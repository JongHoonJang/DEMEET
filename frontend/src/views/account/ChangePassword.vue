<template>
  <div class="container">
    <div>
      <div>
        <p>Password</p>
        <input v-model="credentials.currPassword" type="password" placeholder="*********">
      </div>

      <div>
        <p>New Password</p>
        <input v-model="credentials.newPassword" type="password" placeholder="*********">
      </div>

      <div>
        <p>Confirm Password</p>
        <input v-model="newPassword2" type="password" placeholder="*********">
      </div>
    </div>

    <div class='pass-cg-btn'>
      <button @click="pwupdata(credentials)">Change Password</button>
    </div>
  </div>
</template>

<script>
import { defineComponent } from "vue"
import { useAccountStore } from "@/stores/account"

export default defineComponent({

  setup() {
    const account = useAccountStore()
    const newPassword2 = ""
    const credentials = {
        currPassword : "",
        newPassword : "",
    }
    const pwupdata = (credentials) => {
      // if (credentials.newPassword === newPassword2){
      //   account.changePassword(credentials)
      // }
      account.changePassword(credentials)
    }  
    return {
      account,
      newPassword2,
      credentials,
      pwupdata
    }
  },
})
</script>

<style scoped>
.container {
  margin: 30px;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}
</style>