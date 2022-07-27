<template>
  <div>
    <div style='display:flex; justify-content:center;'>
        <h1 class='sign-head'>Sign Up</h1>
    </div>
    <div class='account-info'>
      <p><input v-model="credentials.nickname" type="text" placeholder="NickName" class="input-prop"></p>
      <p><input v-model="credentials.email" type="email" placeholder="Email" class="input-prop"></p>
      <p><input v-model="credentials.password1" type="password" placeholder="Password" class="input-prop"></p>
      <p><input v-model="credentials.password2" type="password" placeholder="Confirm password" class="input-prop"></p>
    </div>
    <div>
      <button @click="account.signup(credentials)">Sign Up</button>
    </div>
  </div>
</template>

<script>

import { defineComponent } from "vue"
import { useAccountStore } from "@/stores/account"

export default defineComponent({
  data() {
    return {
      credentials: {
        nickname: "",
        email: "",
        password1: "",
        password2: "",
      }
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

/* input::placeholder {color:white;} */


</style>