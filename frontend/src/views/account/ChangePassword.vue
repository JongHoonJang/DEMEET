<template>
  <div class="container">
    <AlertView v-if="isError" @close-modal="isError=false">
      <h3>{{ alertText }}</h3>
    </AlertView>
    <ConfirmView v-if="isBool" @close-modal="result">
      <h3>{{ confirmText }}</h3>
    </ConfirmView>
    <div class="changepw-box">
      <form @submit.prevent="pwupdata(credentials, newPassword2)">
        <div>
          <p>Password</p>
          <input v-model="credentials.currPassword" type="password" placeholder="*********">
        </div>

        <div>
          <p>New Password</p>
          <input @input="limitPassword()" v-model="credentials.newPassword" type="password" placeholder="*********">
        </div>
        <p class="pw-error" v-if="isPasswordError">최소 8자리이상 입력해주세요</p>
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
import { defineComponent, ref } from "vue"
import { useAccountStore } from "@/stores/account"
import AlertView from "@/views/main/AlertView"
import ConfirmView from "@/views/main/ConfirmView"
export default defineComponent({
  components: {
    AlertView,
    ConfirmView
  },
  setup() {
    const alertText = ref('')
    const isError = ref(false)
    const confirmText = ref('')
    const isBool = ref(false)
    let result 
    const account = useAccountStore()
    const isPasswordError = ref(false)
    const newPassword2 = ""
    const credentials = ref({
        currPassword : "",
        newPassword : "",
    })
    const limitPassword = () => {
      const newPassword = []
      if(credentials.value.newPassword.length >= 8) {
        credentials.value.newPassword.split(' ').reduce((acc, cur) => {
          if (1 < acc + cur.length < 8) {
            newPassword.push(cur)
          }  
          isPasswordError.value = false
        }, 0)
      }else if (credentials.value.newPassword.length === 0){
        isPasswordError.value = false
      }else{
        isPasswordError.value = true
      }

    }
    const pwupdata = (credentials, newPassword2) => {
      if (isPasswordError.value){
        alertText.value = '비밀번호를 8자리 이상 입력해주세요.'
        isError.value = true
      }else {
        if (credentials.newPassword !== newPassword2){
          alertText.value = '비밀번호 확인에 실패하였습니다'
          isError.value = true
        }else {
          confirmText.value = '비밀번호를 변경하시겠습니까?'
          isBool.value = true
          console.log(result)
          if(result[0] === 1) {
              isBool.value = false
              account.changePassword(credentials)
          }
          // }else {
          //   isBool.value = false
          // }
        }
      }
    }  
    return {
      account,
      newPassword2,
      credentials,
      isPasswordError,
      alertText,
      isError,
      confirmText,
      isBool,
      result,
      pwupdata,
      limitPassword,
    }
  },
})
</script>

<style scoped>
h3 {
  margin: 25px;
  color: white;
}
.pw-error {
  color: red;
  font-size: 8px;
  margin: 0%;
  text-align: start;
  margin-left:12px;
}

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