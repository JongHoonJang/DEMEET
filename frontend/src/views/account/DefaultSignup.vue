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

input::placeholder {color:white;}

.image-box {
  width: 20vw;
  height: 20vh;
  background-size: contain;
}


.account-box {
  position: absolute;
  background-color : rgba(26, 15, 31, 0.3);
  background-clip: content-box;
  display: flex;
  padding: 10vw 15vw;
  width: 50vw;
  height: 60vh;
  border: solid;
  flex-grow: 1;
  justify-content: space-evenly;
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

.account-left {
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.sign-head {
  /* position: absolute; */
  /* width: 692px;  */
  /* height: 120px; */
  color:rgba(255, 1, 214, 0);

  -webkit-text-stroke: 0.125rem rgb(255, 255, 255);
  letter-spacing: -0.25rem;
  word-spacing: -0.75rem;
  margin: 2rem;
  
  font-family: 'Poppins';
  font-style: italic;
  /* transform:rotate(5deg); */
  font-weight: 900;
  font-size: 4rem;
  line-height: 5rem;
  display: flex;
}

.logo {
    /* width: 5rem;
    height: 5rem; */
    color: rgba(26, 15, 31, 0.3);
    font-size: 4rem;
    background: linear-gradient(to right, rgba(255, 0, 214, 0.9), rgba(0, 224, 255, 0.9));
    -webkit-text-stroke: transparent;
    /* stroke-width: 0.25rem; */
    -webkit-background-clip: text;
    letter-spacing: -0.25rem;
    }


</style>