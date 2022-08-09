<template>
  <div class="profile-view">
    <MainNav/>
    <div class='profile-box'>
      <div class='profile-bg'>
        <img class='bg-image' src="@/assets/profile_bg.jpg" alt="">
      </div>
      <div class='profile-id'>
        <div class='profile-image' @click="isInput=true">
          <input v-if="isInput" type="file" accept=".jpg,.png,.jpeg" class="ex_file" name="ex_file" @change="fileUpload">
          <button v-if="isInput" @click="isInput=false">취소</button>
          <img 
          v-if="account.profile.profileImagePath && !isInput" 
          :src="`${ account.profile.profileImagePath }`" 
          >
          <img  v-if="!isInput" src="@/assets/기본프로필.jpg" alt="">
        </div>
        <div class='profile-detail'>
          <div class='profile-rough'>
            <div class="name">
              <h1 v-if="!isEdit">{{ account.profile.nickname }} 
              <span class="material-symbols-outlined" id="edit" v-if="!isEdit" @click="isEdit=true">edit</span>
              </h1>
              <div class="name-edit">
                <input v-if="isEdit" v-model="name" type="text">
                <div class="name-btn">
                  <span class="material-symbols-outlined" id="done" v-if="isEdit" @click="onUpdate(name)">done</span>
                  <span class="material-symbols-outlined" id="close" v-if="isEdit" @click="isEdit=false">close</span>
                </div>
              </div>
            </div>
            <div>
              <h3>{{ account.profile.email }}</h3>
            </div>
          </div>
          <div class='change-password'>
            <button class="pwedit-btn" @click="isModalViewed=true">Change Password</button>
            <ModalView v-if="isModalViewed" @close-modal="isModalViewed=false">
              <ChangePassword />
            </ModalView>
          </div>
        </div>
      </div>

    </div>
    <div class='endproject'>
      <EndprojectList 
      v-for="endProject in account.profile.deActivateProjects"
      :key="endProject.pid"
      :endProject="endProject"
      />
    </div>
    <div class="hidden">
      <p class="signout" @click="signout()">회원탈퇴</p>
    </div>
  </div>
</template>

<script>
import { defineComponent } from "vue"
import { useAccountStore } from "@/stores/account"
import MainNav from '@/views/main/MainNav'
import ModalView from '@/views/main/ModalView'
import ChangePassword from '@/views/account/ChangePassword'
import EndprojectList from '@/views/account/EndProjectList'
export default defineComponent({
  components: {
    MainNav,
    ModalView,
    ChangePassword,
    EndprojectList
  },
  data() {
    return{
      isModalViewed: false,
      isEdit: false,
      isInput: false
    }
  },
  setup() {
    const profileImage = ''
    const account = useAccountStore()
    const name = ''
    const onUpdate = (data) => {
      if (data !== '') {
        account.changeName(data)
      }
      else {
        alert('변경할 닉네임을 입력하세요')
      }
    }
    const signout = () => {
      if (confirm("회원탈퇴 하시겠습니까?")){
        account.signout()
      }
    }
    const fileUpload = (e) =>{
      var fileInput = e.target.files[0]
      console.log(fileInput)
      // let imagePath = (window.URL || window.webtkitURL).createObjectURL(fileInput[0].files[0])
      account.changeImage(e.target.value)

      // for( var i=0; i<fileInput.length; i++ ){
      // 	if( fileInput[i].files.length > 0 ){
      // 		for( var j = 0; j < fileInput[i].files.length; j++ ){
      // 			console.log(fileInput[i].files[j].name) // 파일명 출력
      // 		}
      // 	}
    }
    account.fetchProfile()
    return {
      profileImage,
      account,
      name,
      onUpdate,
      signout,
      fileUpload
    }
  },
})
</script>

<style scoped>
.profile-view {
  width:80%; 
  margin:auto;
}
.profile-image img {
  width: 142px;
  height: 142px;
  border-radius: 50%;
}

h1 {
  color: white;
}

h3 {
  color: white;
}
.name {
  display: flex;
}
.name-edit {
  display: flex;
}
.name-btn {
  margin-top: 30px;
  margin-left: 8px;
}
.bg-image {
  width: 100%;
  height: 40vh;
}
.profile-id {
  display: flex;
  border: solid;
  margin-bottom: 2rem;
}
.profile-detail {
  width: 100%;
  display: flex;
  justify-content: space-between;
}
.pjt {
  width: 20%;
  height: 20rem;
  background-color: white;
}

.endproject {
  padding-top: 10rem;
  padding-bottom: 2rem;
  border: solid;
  display: flex;
  flex-direction: row;
  justify-content: space-evenly;
  flex-wrap: wrap;
}

input {
  margin-top: 30px;
  box-sizing: border-box;
  width: 200px;
  height: 30px;

  border: 1px solid #A9A9A9;
  border-radius: 8px;
}

.pwedit-btn {
  width: 148px;
  height: 40px;
  color: white;
  background: #2D68FE;
  border-radius: 5px;
}

#edit {
  color: white;
}

#edit:hover {
  color: #2D68FE;
  transform: scale(1.4);
}

#done {
  color: white;
}

#done:hover {
  color: green;
  transform: scale(1.4);
}

#close {
  color: white;
}

#close:hover {
  color: red;
  transform: scale(1.4);
}
.change-password {
  display: flex;
  align-items: center;
  margin-right: 30px;
}

.hidden {
  display: flex;
  justify-content: end;
}
.signout {
  color: black;
}

.signout:hover {
  color: red;
}
</style>