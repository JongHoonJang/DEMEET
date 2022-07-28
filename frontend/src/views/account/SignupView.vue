<template>
  <div>
  <div class='backdrop'>
    <div class='account-box'>
      <div class='account-left'>
        <div style='display:flex; justify-content:center;'>
        <h1 class='sign-head'>Sign Up</h1>
    </div>
    <form @submit.prevent="account.signup(signdata)" class='account-info'>
      <p><input v-model="signdata.nickname" type="text" placeholder="NickName" class="input-prop"></p>
      <p><input v-model="signdata.email" type="email" placeholder="Email" class="input-prop"></p>
      <p><input v-model="signdata.password" type="password" placeholder="Password" class="input-prop"></p>
      <p><input v-model="password2" type="password" placeholder="Confirm password" class="input-prop"></p>
      <button>Sign Up</button>
    </form>
      </div>
      <div class='account-right'>
        <CheckCamera></CheckCamera>
        <!-- <SocialSignup></SocialSignup> -->
      </div>
    </div>
  </div>
  </div>
</template>

<script>

import { defineComponent } from "vue"
import { useAccountStore } from "@/stores/account"

export default defineComponent({
  setup() {
    const signdata = {
      nickname: '',
      email: '',
      password: '',
    }
    const password2 = ''
    const sign = () => {
      if (signdata.password === password2){
        account.signup(signdata)
      }
    }
    const account = useAccountStore()
    return {
      account,
      signdata,
      password2,
      sign
    }
  },
})
</script>

<style scoped>

.backdrop {
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
  background:
  linear-gradient( to bottom,  rgba(255, 1, 214, 0.3), rgba(7, 219, 254, 0.3));
  width: 100%;
  height: 100vh;
}

.account-box {
  position: absolute;
  background-color : rgba(26, 15, 31, 0.3);
  background-clip: content-box;
  display: flex;
  width: 50vw;
  height: 60vh;
  flex-grow: 1;
  justify-content: space-evenly;
}
.account-left {
  display: flex;
  flex-direction: column;
  justify-content: center;
}

</style>