<template>
  <div class="host-box">
    <img class="host-img" v-if="host.profileImagePath!==null" :src="`${host.profileImagePath}`" alt="">
    <img class="host-img" v-else src="@/assets/기본프로필.jpg" alt="">
    <div class="host-data">
      <h3>{{ host.nickname }}</h3>
      <p>{{ host.email }}</p>
    </div>
  </div>
  <div class="container">
    <div class="pjt-list">
      <p>PJT-Name</p>
      <span 
      v-if="!isEditName && host.uid===account.profile.uid && pjt.activation" 
      @click="isEditName=true" 
      class="material-symbols-outlined" 
      id="edit"
      >
      edit</span>
      <div v-if="host.uid!==account.profile.uid || !pjt.activation" class="flex-box"></div>
      <div v-if="isEditName">  
        <span 
        class="material-symbols-outlined" 
        id="done" 
        @click="account.updateProject(projectData)"
        >
        done</span>
        <span 
        class="material-symbols-outlined" 
        id="close" 
        @click="isEditName=false"
        >
        close</span>
      </div>
    </div>
    <div class="box" v-if="!isEditName">
      <p class="text-style">{{ pjt.pjtName }}</p>
    </div>
    <div v-if="isEditName">
      <input class="name-input-box" v-model="projectData.name" id="edit-box" type="text" placeholder="Project1">
    </div>
    <div class="pjt-list">
      <p>PJT-Detail</p>
      <span 
      v-if="!isEditDetail&& host.uid===account.profile.uid && pjt.activation" 
      @click="isEditDetail=true" 
      class="material-symbols-outlined" 
      id="edit"
      >
      edit</span>
      <div v-if="host.uid!==account.profile.uid || !pjt.activation" class="flex-box"></div>
      <div v-if="isEditDetail">  
        <span 
        class="material-symbols-outlined" 
        id="done" 
        @click="account.updateProject(projectData)"
        >
        done</span>
        <span 
        class="material-symbols-outlined" 
        id="close" 
        @click="isEditDetail=false"
        >
        close</span>
      </div>
    </div>
    <div v-if="!isEditDetail" class="pjt-detail-box">
      <p class="text-style">{{ pjt.pjtDesc || 'pjt-detail' }}</p>
    </div>
    <div v-if="isEditDetail">
      <textarea class="desc-input-box" v-model="projectData.desc" maxlength="200" name="" id="" cols="30" rows="10"></textarea>
    </div>
    <div class="time">
      <p>누적 미팅시간</p>
    </div>
    <div class="box">
      <p class="text-style">{{ pjt.totalMeetTime }}</p>
    </div>
    <div class="pjt-list">
      <p>Member</p>
      <span 
      v-if="host.uid===account.profile.uid && pjt.activation" 
      @click="isModalViewed=true"
      class="material-symbols-outlined" 
      id="add" 
      >
      add</span>
      <ModalView v-if="isModalViewed" @close-modal="isModalViewed=false">
        <AddUser 
        :project="pjt"
        />
      </ModalView>
      <div v-if="host.uid!==account.profile.uid || !pjt.activation" class="flex-box"></div>
    </div>
    <div class="member-box">
      <UserIcon 
      v-for="member in pjt.member"
      :key="member.uid"
      :member="member"
      />
    </div>
    <div class="time">
      <p>프로젝트 시작일</p>
    </div>
    <div class="box">
      <p class="text-style">{{ pjt.pjtStartDate }}</p>
    </div>
    <div class="time" v-if="!pjt.activation">
      <p>프로젝트 종료일</p>
    </div>
    <div class="box" v-if="!pjt.activation">
      <p class="text-style">{{ pjt.pjtEndDate }}</p>
    </div>
  </div>
  <div class="btn-box">
    <button @click="back" class="back-btn">뒤로가기</button>
    <div >
      <button 
      v-if="pjt.projectOwner === account.profile.uid && pjt.activation" 
      class="blue-btn"
      @click="goConference"
      >
      시작하기</button>
      <button 
      v-if="pjt.projectOwner === account.profile.uid && pjt.activation" 
      class="red-btn"
      @click="endProject"
      >
      종료하기</button>
      <button 
      v-if="pjt.projectOwner !== account.profile.uid && pjt.activation" 
      class="blue-btn"
      @click="goConference"
      >
      입장하기</button>
      <button 
      v-if="pjt.projectOwner !== account.profile.uid && pjt.activation" 
      class="red-btn"
      @click="leave"
      >
      팀나가기</button>
    </div>
  </div>
</template>

<script>
import { defineComponent,ref } from "vue"
import { useAccountStore } from "@/stores/account"
import router from "@/router"
import { useRoute } from 'vue-router'
import UserIcon from '@/views/main/UserIcon'
import ModalView from '@/views/main/ModalView'
import AddUser from '@/views/main/AddUser'
import axios from "axios"
export default defineComponent({
  components: {
    UserIcon,
    ModalView,
    AddUser
  },
  data() {
    return {
      isModalViewed: false,
      isEditName: false,
      isEditDetail: false,
    }
  },
  async setup() {
    const account = useAccountStore()
    const route = useRoute()  
    const project_pk = ref(route.params.pid)
    await axios(process.env.VUE_APP_API_URL + "projects/" + project_pk.value, {
      method: "get",
      headers: account.authHeader
    }).then(res => account.project = res.data.project)
    const pjt = ref(account.project)
    const projectData = ref({
      pid: pjt.value.pid,
      name: pjt.value.pjtName,
      desc: pjt.value.pjtDesc || 'pjt-detail',
      deactivate: null
    })
    const host = ref(pjt.value.member.find(res => res.uid===pjt.value.projectOwner))
    const goConference = () => {
      router.push({name:'ConferenceView', params: {pid: project_pk.value ,sessionId: pjt.value.sessionId}})
    }
    const endProject = () => {
      projectData.value.deactivate = true
      account.updateProject(projectData.value)
    }

    const leave = () => {
      if (confirm('정말 팀을 떠나시겠습니까?')){
        router.push({name:'MainView'})
        account.removeUser({ pid:pjt.value.pid, uid:account.profile.uid })
      }
    }
    const back = () => {
      if (pjt.value.activation) {
        router.push({name:'MainView'})
      }else {
        router.push({name:'ProfileView'})
      }
    }
    
    return {
      host,
      pjt,
      account,
      projectData,
      back,
      endProject,
      goConference,
      leave,
    }
  }
})
</script>

<style scoped>

.host-box {
  background: #9E9E9E;
  border-radius: 10px;
  width: 650px;
  height: 92px;
  display: flex;
  align-items: flex-start;
  text-align: start;
}
.host-img {
  height: 80px;
  width: 80px;
  margin: 6px;
  border-radius: 50%;
}
.flex-box {
  width: 16px;
  height: 16px;
}

#add {
  margin-top: 16px;
  color: #9E9E9E;
}
#add:hover {
  transform: scale(1.4);
  color: #2097F7;
}

#content {
  margin-top: 16px;
  color: #9E9E9E;
}
#content:hover {
  transform: scale(1.4);
  color: #2097F7;
}
#edit {
  margin-top: 16px;
  color: #9E9E9E;
}
#edit:hover {
  transform: scale(1.4);
  color: #2097F7;
}
.blue-btn {
  width: 92px;
  height: 36px;
  margin-left: 110px; 
  background: radial-gradient(95% 60% at 50% 75%, #005FD6 0%, #209BFF 100%);
  border: 1px solid #54A1FD;
  box-shadow: 0px 8px 20px -8px #1187FF, inset 0px 1px 8px -4px #FFFFFF;
  border-radius: 12px;
  color: white;
  font-size: 16px;
  line-height: 22px;
  font-weight: 600;
  letter-spacing: .02em;
  transition: all .2s ease;
  -webkit-tap-highlight-color: rgba(255,255,255,0);
}
.blue-btn:hover {
  transform: scale(1.2);
}


.back-btn {
  width: 92px;
  height: 36px;
  background: radial-gradient(95% 60% at 50% 75%, #0060d6 0%, #000000 100%);
  border: 1px solid #54A1FD;
  box-shadow: 0px 8px 20px -8px #1187FF, inset 0px 1px 8px -4px #FFFFFF;
  border-radius: 12px;
  color: white;
  font-size: 16px;
  line-height: 22px;
  font-weight: 600;
  letter-spacing: .02em;
  transition: all .2s ease;
  -webkit-tap-highlight-color: rgba(255,255,255,0);
}
.back-btn:hover{
  transform: scale(1.2);
  color: #9E9E9E;
}

.red-btn {
  width: 92px;
  height: 36px;
  margin-left: 16px;
  background: radial-gradient(95% 60% at 50% 75%, #d60000 0%, #ff2020 100%);
  border: 1px solid #fd5454;
  box-shadow: 0px 8px 20px -8px #ff1111, inset 0px 1px 8px -4px #FFFFFF;
  border-radius: 12px;
  color: white;
  font-size: 16px;
  line-height: 22px;
  font-weight: 600;
  letter-spacing: .02em;
  transition: all .2s ease;
  -webkit-tap-highlight-color: rgba(255,255,255,0);
}

.red-btn:hover {
  transform: scale(1.2);
}

.btn-box {
  margin-top: 30px;
  margin-right: 20px;
  display: flex;
  justify-content: flex-start;
  margin-left: 20%;
}

.time {
  text-align: start;
  color: #9E9E9E;
  margin-left: 21%;
}

.text-style {
  text-align: start;
  margin: 12px;
  overflow: hidden;
  word-break:break-all;
}

.pjt-list {
  display: flex;
  justify-content: space-around;
  color: #9E9E9E;
}

.container {
  display: flex;
  flex-direction: column;
}

.container .box {
  margin-left: 20%;
}

.box {
  width: 400px;
  height: 44px;
  background: #333333;
  color: #9E9E9E;
}

.pjt-detail-box {
  margin-left: 20%;
  width: 400px;
  height: 120px;
  background: #333333;
  color: #9E9E9E;
}
.name-input-box {
  width: 388px;
  height: 40px;
  background: #333333;
  color: white;
}
.desc-input-box{
  width: 388px;
  background: #333333;
  color: white;
}
.member-box{
  display: flex;
  width: 400px;
  height: 44px;
  background: #333333;
  color: #9E9E9E;
  margin-left: 20%;
}

.data-box {
  width: 600px;
  height: 600px;
}

#done {
  margin-top: 16px;
  color: white;
}

#done:hover {
  color: green;
  transform: scale(1.4);
}

#close {
  margin-top: 16px;
  color: white;
}

#close:hover {
  color: red;
  transform: scale(1.4);
}

.host-data{
  margin-left: 200px;
}
</style>