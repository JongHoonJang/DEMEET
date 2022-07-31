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
      <button class="signup-btn">Sign Up</button>
      <router-link class="back" :to="{ name: 'LoginView' }">back</router-link>
    </form>
      </div>
      <div class='account-right'>
        <h1 class="main-logo">DEMEET</h1>
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
.logo {
    color: rgba(26, 15, 31, 0.3);
    font-size: 4rem;
    background: linear-gradient(to right, rgba(255, 0, 214, 0.9), rgba(0, 224, 255, 0.9));
    -webkit-text-stroke: transparent;
    /* -webkit-background-clip: text; */
    letter-spacing: -0.25rem;
    }
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

input::placeholder {color:white;}

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

.signup-btn {
  width: 80px;
  height: 30px;
  background: linear-gradient(90deg, #FF00D6 8.81%, #00E0FF 94.11%);
  border-radius: 5px;
}

.back {
  color: white;
  text-decoration: none;
  margin-left: 100px;
}

.back:hover {
  color: rgba(255, 1, 214, 1);
}
</style>