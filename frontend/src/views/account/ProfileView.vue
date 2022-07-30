<template>
  <div style='width:80%; margin:auto;' >
    <MainNav/>
    <div class='profile-box'>
      <div class='profile-bg'>
        <img class='bg-image' src="@/assets/profile_bg.jpg" alt="">
      </div>
      <div class='profile-id'>
        <div class='profile-image'>
          <span class="material-symbols-outlined" id='account'>account_circle</span>
        </div>
        <div class='profile-detail'>
          <div class='profile-rough'>
            <div class="name">
              <h1 v-if="!isEdit">{{ account.profile.nickname }} 
                <button class="pw-btn" v-if="!isEdit" @click="isEdit=true">
                  <span class="material-symbols-outlined" id="edit">edit</span>
                </button>
              </h1>
              <input v-if="isEdit" v-model="name" type="text">
              <button class="pw-btn" v-if="isEdit" @click="onUpdate(name)">
                <span class="material-symbols-outlined">done</span>
              </button>
              <button class="pw-btn" v-if="isEdit" @click="isEdit=false">
                <span class="material-symbols-outlined">close</span>
              </button>
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
      <div class='pjt'>PJT1</div>
      <div class='pjt'>PJT2</div>
      <div class='pjt'>PJT3</div>
      <div class='pjt'>PJT4</div>
    </div>
  </div>
</template>

<script>
import { defineComponent } from "vue"
import { useAccountStore } from "@/stores/account"

import MainNav from '@/views/main/MainNav'
import ModalView from '@/views/main/ModalView'
import ChangePassword from '@/views/account/ChangePassword'
export default defineComponent({
  components: {
    MainNav,
    ModalView,
    ChangePassword,
  },
  data() {
    return{
      isModalViewed: false,
      isEdit: false
    }
  },
  setup() {
    const account = useAccountStore()
    const name = ''
    const onUpdate = (data) => {
      account.changeName(data)
    }
    account.fetchProfile()
    return {
      account,
      name,
      onUpdate
    }
  },
})
</script>

<style scoped>
#account {
  font-size:10rem;
}
.name {
  display: flex;
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
  margin-left: 30px;
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

.pw-btn {
  margin-top: 30px;
  margin-left: 10px;
  width: 30px;
  height: 30px;
  color: white;
  background: #2D68FE;
  border-radius: 5px;
}
</style>