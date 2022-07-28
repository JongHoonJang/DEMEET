<template>
  <div class="container">
    <div>
      <div>
        <p>Password</p>
        <input v-model="credentials.currPassword" type="password" placeholder="*********">
      </div>

      <div>
        <p>New Password</p>
        <input v-model="credentials.newPassword1" type="password" placeholder="*********">
      </div>

      <div>
        <p>Confirm Password</p>
        <input v-model="credentials.newPassword2" type="password" placeholder="*********">
      </div>
    </div>

    <div class='pass-cg-btn'>
      <button @click="checkPassword">Change Password</button>
    </div>
  </div>
</template>

<script>
import { defineComponent } from "vue"
import { useAccountStore } from "@/stores/account"

export default defineComponent({
  data() {
    return{
      newPassword1 : "",
      newPassword2 : "",
      
      credentials: {
        currPassword : "",
        newPassword : "",
      }
    }
  },
  setup() {
    const account = useAccountStore()
    return {
      account,
    }
  },
  methods : {
    checkPassword() {
      if (this.newPassword1===this.newPassword2) {
        this.account.changePassword(this.credentials)
      }
    }
  }
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