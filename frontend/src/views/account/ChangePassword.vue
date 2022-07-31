<template>
  <div class="container">
    <div class="changepw-box">
      <form @submit.prevent="pwupdata(credentials)">
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
        <button class='pass-cg-btn'>Change Password</button>

      </form>
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
.changepw-box {
  margin-top: 60px;
}

.pass-cg-btn {
  margin: 40px;
  width: 283px;
  height: 40px;

  background: #2097F7;
  border-radius: 10px;
}

input {
  box-sizing: border-box;
  width: 318px;
  height: 40px;

  border: 1px solid #A9A9A9;
  border-radius: 8px;
}

p {
  font-family: 'Poppins';
  font-style: normal;
  font-weight: 500;
  font-size: 14px;
  line-height: 21px;
  /* identical to box height */


  color: #FFFFFF;
}
</style>