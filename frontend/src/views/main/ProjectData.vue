<template>
  <div class="host-box">
    <span class="material-symbols-outlined" id="person">person</span>
    <div class="host-data">
      <h3>{{ host.nickname }}</h3>
      <p>{{ host.email }}</p>
    </div>
  </div>
  <div class="container">
    <div class="pjt-list">
      <p>PJT-Name</p>
      <span 
      v-if="!isEditName && host.uid===account.profile.uid" 
      @click="isEditName=true" 
      class="material-symbols-outlined" 
      id="edit"
      >
      edit</span>
      <div v-if="host.uid!==account.profile.uid" class="flex-box"></div>
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
      <p class="text-style">{{ demeet.project.pjtName }}</p>
    </div>
    <div v-if="isEditName">
      <input class="name-input-box" v-model="projectData.name" id="edit-box" type="text" placeholder="Project1">
    </div>
    <div class="pjt-list">
      <p>PJT-Detail</p>
      <span 
      v-if="!isEditDetail&& host.uid===account.profile.uid" 
      @click="isEditDetail=true" 
      class="material-symbols-outlined" 
      id="edit"
      >
      edit</span>
      <div v-if="host.uid!==account.profile.uid" class="flex-box"></div>
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
    <div v-if="!isEditDetail" class="box">
      <p class="text-style">{{ demeet.project.pjtDesc || 'pjt-detail' }}</p>
    </div>
    <div v-if="isEditDetail">
      <textarea class="desc-input-box" v-model="projectData.desc" name="" id="" cols="30" rows="10"></textarea>
    </div>
    <div v-if="pjt.activation" class="pjt-list">
      <p>초대코드</p>
      <span class="material-symbols-outlined" id="content">content_copy</span>
    </div>
    <div v-if="pjt.activation" class="box">
      <p class="text-style">https://www.demeet.com/meet/1</p>
    </div>
    <div class="time">
      <p>누적 미팅시간</p>
    </div>
    <div class="box">
      <p class="text-style">{{ demeet.project.totalMeetTime }}</p>
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
      v-for="member in demeet.project.member"
      :key="member.uid"
      :member="member"
      />
    </div>
    <div class="time">
      <p>프로젝트 시작일</p>
    </div>
    <div class="box">
      <p class="text-style">{{ demeet.project.pjtStartDate }}</p>
    </div>
    <div class="time" v-if="!pjt.activation">
      <p>프로젝트 종료일</p>
    </div>
    <div class="box" v-if="!pjt.activation">
      <p class="text-style">{{ demeet.project.pjtEndDate }}</p>
    </div>
  </div>
  <div class="btn-box">
    <button 
    v-if="demeet.project.projectOwner === account.profile.uid && pjt.activation" 
    class="blue-btn"
    @click="goConference"
    >
    시작하기</button>
    <button 
    v-if="demeet.project.projectOwner === account.profile.uid && pjt.activation" 
    class="red-btn"
    @click="endProject"
    >
    종료하기</button>
    <button 
    v-if="demeet.project.projectOwner !== account.profile.uid && pjt.activation" 
    class="blue-btn"
    @click="goConference"
    >
    입장하기</button>
    <button 
    v-if="demeet.project.projectOwner !== account.profile.uid && pjt.activation" 
    class="red-btn"
    @click="leave"
    >
    팀나가기</button>
    <button 
    @click="back"
    class="blue-btn"
    >
    뒤로가기</button>
  </div>
</template>

<script>
import { defineComponent,ref } from "vue"
import { useAccountStore } from "@/stores/account"
import router from "@/router"
import UserIcon from '@/views/main/UserIcon'
import ModalView from '@/views/main/ModalView'
import AddUser from '@/views/main/AddUser'

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
  props: {
    project: Object
  },
  setup(props) {
    const account = useAccountStore()
    const demeet = ref(props)
    const pjt = ref(demeet.value.project)
    const member = ref(demeet.value.project.member)
    const projectData = ref({
      pid: demeet.value.project.pid,
      name: demeet.value.project.pjtName,
      desc: demeet.value.project.pjtDesc || 'pjt-detail',
      deactivate: null
    })
    const goConference = () => {
      router.push({name:'ConferenceView', params: {sessionId: demeet.value.project.sessionId}})
    }
    const endProject = () => {
      projectData.value.deactivate = true
      account.updateProject(projectData.value)
    }
    const host = ref(member.value.find(res => res.uid===pjt.value.projectOwner))
    
    const leave = () => {
      if (confirm('정말 팀을 떠나시겠습니까?')){
        account.removeUser({pid:pjt.value.pid,uid:account.profile.uid})
        router.push({name:'MainView'})
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
      pjt,
      host,
      demeet,
      account,
      projectData,
      back,
      endProject,
      goConference,
      leave
    }
  }
})
</script>

<style scoped>
.host-box {
  background: #9E9E9E;
  border-radius: 10px;
  width: 816px;
  height: 92px;
  display: flex;
  align-items: flex-start;
  text-align: start;
}
.flex-box {
  width: 16px;
  height: 16px;
}
#person {
  font-size: 60px;
  margin-left: 16px;
  margin-top: 16px;
  margin-right: 200px;
}
#add {
  margin-top: 16px;
  color: #9E9E9E;
}

#content {
  margin-top: 16px;
  color: #9E9E9E;
}

#edit {
  margin-top: 16px;
  color: #9E9E9E;
}

.blue-btn {
  width: 92px;
  height: 36px;
  background: blue;
  border-radius: 5px;
  color: white;
  font: bold;
  margin: 8px;
}

.red-btn {
  width: 92px;
  height: 36px;
  background: red;
  border-radius: 5px;
  color: white;
  font: bold;
  margin: 8px;
}

.btn-box {
  margin-top: 30px;
  margin-right: 20px;
  text-align: end;
}

.time {
  text-align: start;
  margin-left: 180px;
  color: #9E9E9E;
}

.text-style {
  text-align: start;
  margin: 12px;
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

.box {
  width: 500px;
  height: 44px;
  background: #333333;
  margin-left: 160px;
  color: #9E9E9E;
}
.name-input-box {
  width: 492px;
  height: 40px;
  background: #333333;
  color: white;
}
.desc-input-box{
  width: 492px;
  background: #333333;
  color: white;
}
.member-box{
  display: flex;
  width: 500px;
  height: 44px;
  background: #333333;
  margin-left: 160px;
  color: #9E9E9E;
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
</style>