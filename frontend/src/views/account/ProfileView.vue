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
              <h1>{{ account.profile.nickname }}
                <button>변경</button>
              </h1>
              <!-- <button>변경</button> -->
            </div>
            <div>
              <p>Job</p>
            </div>
          </div>
          <div class='change-password'>
            <button @click="isModalViewed=true">Change Password</button>
            <ModalView v-if="isModalViewed" @close-modal="isModalViewed=false">
              <ChangePassword />
            </ModalView>
          </div>
        </div>
      </div>

    </div>
    <div class='container'>
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
    return {
      isModalViewed: false,
    }
  },

  setup() {
    const account = useAccountStore()
    return {
      account,
    }
  },
})
</script>

<style scoped>
#account {
  font-size:10rem;
}

/* .name {
  display: flex;
} */

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
/* .profile-projects {
  display: grid; */
/* } */
.container {
  padding-top: 10rem;
  padding-bottom: 2rem;
  border: solid;
  display: flex;
  flex-direction: row;
  justify-content: space-evenly;
  flex-wrap: wrap;
}
</style>