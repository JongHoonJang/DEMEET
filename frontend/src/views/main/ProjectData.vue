<template>
  <div class="container">
    <div class="pjt-list">
      <p>PJT-Name</p>
      <span class="material-symbols-outlined" id="edit">edit</span>
    </div>
    <div class="box">
      <p class="text-style">Project1</p>
    </div>
    <!-- <input id="edit-box" type="text" placeholder="Project1"> -->
    <div class="pjt-list">
      <p>PJT-Detail</p>
      <span class="material-symbols-outlined" id="edit">edit</span>
    </div>
    <div class="box">
      <p class="text-style">Project Detail</p>
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
        <AddUser />
      </ModalView>
    </div>
    <div class="user-box">
      <UserIcon />
      <UserIcon />
      <UserIcon />
      <UserIcon />
      <UserIcon />
    </div>
    <div class="time">
      <p>프로젝트 시작일</p>
    </div>
    <div class="box">
      <p class="text-style">2022/07/02/ 10:30</p>
    </div>
    <div class="time">
      <p>프로젝트 종료일</p>
    </div>
    <div class="box">
      <p class="text-style">2022/08/12/ 10:30</p>
    </div>
  </div>
  <div class="btn-box">
    <!-- v-if로 호스트 맴버 endproject구분 -->
    <button class="blue-btn">시작하기</button>
    <button class="red-btn">종료하기</button>
    <button class="blue-btn">입장하기</button>
    <button class="red-btn">팀나가기</button>
    <button class="blue-btn">뒤로가기</button>
  </div>
</template>

<script>
import UserIcon from '@/views/main/UserIcon'
import ModalView from '@/views/main/ModalView'
import AddUser from '@/views/main/AddUser'
export default {
  components: {
    UserIcon,
    ModalView,
    AddUser
  },
  data() {
    return {
      isModalViewed: false,
    }
  }
}
</script>

<style scoped>
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

#add {
  margin-top: 16px;
  color: #9E9E9E;
}

#content {
  margin-top: 16px;
  color: #9E9E9E;
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

#edit {
  margin-top: 16px;
  color: #9E9E9E;
}

.data-box {
  width: 600px;
  height: 600px;
}
</style>