<template>
  <div class='backdrop'>
    <div class='account-box'>
      <div class='account-left'>
        <div style='display:flex; justify-content:center;'>
      <h1 class='sign-head'>Sign In</h1>
    </div>
    <form @submit.prevent="account.login(credentials)" class='account-info'>
      <p><input v-model="credentials.email" type="text" placeholder="Email" class="input-prop"></p>
      <p><input v-model="credentials.password" type="password" placeholder="password" class="input-prop"></p>
      <button>Sign In</button>
    </form>

    <!-- <div style='color: white;'>Signin as a Guest</div>
    <div>
      <div>
        <img src="@/assets/btn_google_signin_dark_focus_web.png" alt="">
      </div>
      <div style="height:46px; width:190px; display:flex; background-color: black; margin:auto; border-radius: 0.5rem;">
        <img src="@/assets/GitHub-Mark-Light-32px.png" style="height: 36px; margin: 2px;" alt="">
        <p>Sign In with GitHub</p>                
      </div>
      <div style="margin:4px">
        <img src="@/assets/kakao_login_medium_narrow.png" alt="">
      </div>
    </div> -->
      </div>
      <div class='account-right'>
        <h1 class="main-logo">DEMEET</h1>
      </div>
    </div>
  </div>
</template>

<script>
import { defineComponent } from "vue"
import { useAccountStore } from "@/stores/account"

export default defineComponent({

  setup() {
    const credentials = {
      email: '',
      password: ''
    }
    const account = useAccountStore()
    return {
      account,
      credentials
    }
  },
})
</script>

<style scoped>
.main-logo {
      /* width: 5rem;
      height: 5rem; */
  color: rgba(26, 15, 31, 0.3);
  font-size: 4rem;
  background: linear-gradient(to right, rgba(255, 0, 214, 0.9), rgba(0, 224, 255, 0.9));
  -webkit-text-stroke: transparent;

  -webkit-background-clip: text;
  letter-spacing: -0.25rem;
}

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
  width: 60vw;
  height: 60vh;
  flex-grow: 1;
  justify-content: space-evenly;
}

.account-left {
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.account-right {
  display: flex;
  justify-content: center;
  align-items: center;
}

input::placeholder {color:white;}

.logo {
    color: rgba(26, 15, 31, 0.3);
    font-size: 4rem;
    background: linear-gradient(to right, rgba(255, 0, 214, 0.9), rgba(0, 224, 255, 0.9));
    -webkit-text-stroke: transparent;
    /* -webkit-background-clip: text; */
    letter-spacing: -0.25rem;
    }

.account-info {
  width: 20vw;
  height: 20vh;
  }
  
.input-prop {
  color: white;
  background: transparent;
  border-top: none;
  border-bottom: solid;
  border-left: none;
  border-right: none;
  border-color: rgba(255, 1, 214, 1);
  font-size : 1.2rem;
  line-height: 2;
}

.sign-head {
  color:rgba(255, 1, 214, 0);

  -webkit-text-stroke: 0.125rem rgb(255, 255, 255);
  letter-spacing: -0.25rem;
  word-spacing: -0.75rem;
  margin: 2rem;
  
  font-family: 'Poppins';
  font-style: italic;
  font-weight: 900;
  font-size: 4rem;
  line-height: 5rem;
  display: flex;
}

</style>