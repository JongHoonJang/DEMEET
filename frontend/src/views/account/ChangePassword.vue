<template>
  <div class="container">
    <div class="changepw-box">
      <form @submit.prevent="pwupdata(credentials, newPassword2)">
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
    const pwupdata = (credentials, newPassword2) => {
      if (credentials.newPassword === newPassword2){
        if(confirm('비밀번호를 변경하시겠습니까?')) {
            alert('비밀번호가 변경되었습니다. \n 다시 로그인 해주세요.')
            account.changePassword(credentials)
          }
      }
      else {
        alert('비밀번호 확인에 실패하였습니다')
      }
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
  width: 260px;
  height: 40px;
  background: radial-gradient(95% 60% at 50% 75%, #005FD6 0%, #209BFF 100%);
  border: 1px solid #54A1FD;
  box-shadow: 0px 8px 20px -8px #1187FF, inset 0px 1px 8px -4px #FFFFFF;
  border-radius: 12px;
  color: white;
  font-size: 16px;
  line-height: 22px;
  font-weight: 600;
  letter-spacing: .02em;
  transition: all .2s ease;
  -webkit-tap-highlight-color: rgba(255,255,255,0);
}
.pass-cg-btn:hover {
  transform: scale(1.1);
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