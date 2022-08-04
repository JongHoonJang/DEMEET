<template>
  <div class="host-box">
    <span class="material-symbols-outlined" id="person">person</span>
    <div class="host-data">
      <h3>{{ host.nickname }}</h3>
      <p>{{ host.email }}</p>
    </div>
  </div>
  <div class="container">
    <div class="pjt-list" v-if="!isEditName" @click="isEditName=true">
      <p>PJT-Name</p>
      <span @click="switchName" class="material-symbols-outlined" id="edit">edit</span>
    </div>
    <div class="box">
      <p class="text-style">{{ demeet.project.pjtName }}</p>
    </div>
    <form v-if="isEditName" @submit.prevent="">
      <input id="edit-box" type="text" placeholder="Project1">
    </form>
    <div class="pjt-list" v-if="!isEditDetail">
      <p>PJT-Detail</p>
      <span @click="switchDetail" class="material-symbols-outlined" id="edit">edit</span>
    </div>
    <div class="box">
      <p class="text-style">{{ demeet.project.pjtDesc || 'pjt-detail' }}</p>
    </div>
    <!-- <textarea name="" id="" cols="30" rows="10"></textarea> -->
    <div class="pjt-list">
      <p>초대코드</p>
      <span class="material-symbols-outlined" id="content">content_copy</span>
    </div>
    <div class="box">
      <p class="text-style">https://www.demeet.com/meet/1</p>
    </div>
    <div class="time">
      <p>누적 미팅시간</p>
    </div>
    <div class="box">
      <p class="text-style">10:30</p>
    </div>
    <div class="pjt-list">
      <p>Member</p>
      <span class="material-symbols-outlined" id="add" @click="isModalViewed=true">add</span>
      <ModalView v-if="isModalViewed" @close-modal="isModalViewed=false">
        <AddUser 
        :userList="account.userList"
        />
      </ModalView>
    </div>
    <div class="user-box">
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
    <div class="time" v-if="demeet.project.pjtEndDate">
      <p>프로젝트 종료일</p>
    </div>
    <div class="box" v-if="demeet.project.pjtEndDate">
      <p class="text-style">{{ demeet.project.pjtEndDate }}</p>
    </div>
  </div>
  <div class="btn-box">
    <!-- v-if로 호스트 맴버 endproject구분 -->
    <button v-if="demeet.project.projectOwner === account.profile.uid" class="blue-btn">시작하기</button>
    <button v-if="demeet.project.projectOwner === account.profile.uid" class="red-btn">종료하기</button>
    <button v-if="demeet.project.projectOwner !== account.profile.uid" class="blue-btn">입장하기</button>
    <button v-if="demeet.project.projectOwner !== account.profile.uid" class="red-btn">팀나가기</button>
    <button class="blue-btn">뒤로가기</button>
  </div>
</template>

<script>
import { defineComponent } from "vue"
import { useAccountStore } from "@/stores/account"

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
  props: ['project'],
  setup(props) {
    const account = useAccountStore()
    const demeet = props
    account.fetchProfile()
    account.fetchUserList()
    let host = {}
    for (const user of demeet.project.member) {
      if (user.uid === demeet.project.projectOwner) {
        host = user
      }
    }
    console.log(host)
    return {
      demeet,
      account,
      host
    }
  },
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

.user-box{
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
</style>