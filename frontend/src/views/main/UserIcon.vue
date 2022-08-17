<template>
  <AlertView AlertView v-if="isError" @close-modal="isError=false">
    <h3>{{ alertText }}</h3>
  </AlertView>
  <div 
  v-if="user.member.profileImagePath!==null" 
  class="user-icon" 
  :style="`background-image: url(${user.member.profileImagePath});`">
    <span 
    v-if="project.project.projectOwner===project.profile.uid && project.project.activation" 
    @click="remove(user)" 
    class="material-symbols-outlined" 
    id="close"
    >
    close</span>
    <div 
    v-if="project.project.projectOwner!==project.profile.uid || !project.project.activation" 
    class="none-box"
    >
    </div>
    <p class="username">{{ user.member.nickname }}</p>
  </div>
  <div 
  v-if="user.member.profileImagePath===null" 
  class="user-icon"
  >
    <span 
    v-if="project.project.projectOwner===project.profile.uid && project.project.activation" 
    @click="remove(user)" 
    class="material-symbols-outlined" 
    id="close"
    >
    close</span>
    <div 
    v-if="project.project.projectOwner!==project.profile.uid || !project.project.activation" 
    class="none-box"
    >
    </div>
    <p class="username">{{ user.member.nickname }}</p>
  </div>
</template>

<script>
import { defineComponent, ref } from "vue"
import router from "@/router"
import { useAccountStore } from "@/stores/account"
import AlertView from "@/views/main/AlertView"
export default defineComponent({
  components: {
    AlertView
  },
  props: ['member','host'],
  setup(props) {
    const alertText = ref('')
    const isError = ref(false)
    const user = props
    const hostdata = user.host
    const project = useAccountStore()
    const pjt = ref(project.project)
    const remove = (user) => {
      if (user.member.uid===project.profile.uid) {
        alertText.value = '호스트를 추방할 수 없습니다.'
        isError.value = true
      }else {
        if(confirm('추방시키겠습니까?')){
          project.removeUser({pid:pjt.value.pid,uid:user.member.uid})
          router.go({name:'DetailView'})
        }
      }
    }
    return {
      user,
      project,
      hostdata,
      alertText,
      isError,
      remove
    }
  }
})
</script>

<style scoped>
h3 {
  margin: 25px;
  color: white;
}
#close {
  font-size: 16px;
  color: black;
}
.none-box{
  width: 40px;
  height: 16px;
  margin-bottom: 18px;
}
.user-icon {
  margin-left: 8px;
  margin-top: 2px;
  background-image: url(@/assets/기본프로필.jpg);
  background-size: cover;
  width: 40px;
  height: 40px;
  border-radius: 100%;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  text-align: end;
}
.username {
  width: 44px;
  height: 14px;
  font-size: 8px;
  color: black;
  word-break:break-all;
  text-align: center;
}
</style>