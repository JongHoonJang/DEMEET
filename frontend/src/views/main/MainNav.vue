<template>
  <nav>
    <a href="/">
      <img src="@/assets/DEMEET_logo.png" alt="">
    </a>
    <div>
      <form action="">
        <input type="text" placeholder="Search">
      </form>
    </div>
    <div class="list-box">
      <div class="list-box">
        <span class="material-symbols-outlined" id="account">account_box</span>
        <a href="/profile/1">Profile</a>
      </div>
      <div class="list-box" @click="account.logout">
        <span class="material-symbols-outlined" id="logout">logout</span>
        <a href="">Logout</a>
      </div>
    </div>
  </nav>
</template>

<script>
import { defineComponent } from "vue"
import { useAccountStore } from "@/stores/account"
export default defineComponent({
  setup() {
    const account = useAccountStore()
    return {
      account,
    }
  },

})
</script>

<style scoped>
#account {
  font-size: 32px;
  color: #4C4E50;
}

#logout {
  font-size: 32px;
  color: #4C4E50;
}

.list-box {
  margin-top: 12px;
  display: flex;
}

nav {
  height: 70px;
  display: flex;
  justify-content: space-between;
  background: #1A1D1F;
  margin-bottom: 100px;
}

nav img {
  margin-top: 20px;
  margin-left: 20px;
  width: 180px;
  height: 50px;
}

nav input {
  width: 525px;
  height: 50px;
  margin-top: 16px;
  background: #111315;
  border-radius: 10px;
  font-family: 'Poppins';
  font-style: normal;
  font-weight: 600;
  font-size: 32px;
  line-height: 48px;
  text-align: start;
  color: white;
}

nav div a {
  font-weight: bold;
  color: #4C4E50;
  text-decoration-line: none;
  margin-right: 20px;
  font-family: 'Poppins';
  font-style: normal;
  font-weight: 600;
  font-size: 24px;
  color: #4C4E50;
}

nav a.router-link-exact-active {
  color: #2978c2;
}
</style>