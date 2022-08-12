<template>
  <nav>
    <a href="/">
      <img src="@/assets/DEMEET_logo.png" alt="">
    </a>
    <div>
      <input v-model.trim="search" @input="setData" type="text" placeholder="Search">
    </div>
    <div class="list-box">
      <div class="list-box">
        <span class="material-symbols-outlined" id="account">account_box</span>
        <a href="/profile/me">{{ account.profile.nickname }}님</a>
      </div>
      <div class="list-box" @click="account.logout">
        <span class="material-symbols-outlined" id="logout">logout</span>
        <a href="">Logout</a>
      </div>
    </div>
  </nav>
</template>

<script>
import { defineComponent, ref } from "vue"
import { useAccountStore } from "@/stores/account"
export default defineComponent({
  setup() {
    const account = useAccountStore()
    const search = ref('')
    const setData = () => {
      account.search = search.value
      if (account.search){
        account.projects = account.projects.filter(res => res.pjtName.includes(account.search))
        account.endProjects = account.endProjects.filter(res => res.pjtName.includes(account.search))
      }else{
        account.fetchProjects()
        account.fetchProfile()
      }
    }
    return {
      account,
      search,
      setData
    }
  },
  async created() {
    await this.account.fetchProfile()
  },
})
</script>

<style scoped>
@media (max-width: 1024px){
  nav input{
		display: none;
  }
}
@media (max-width: 768px){
  nav div a {
    display: none;
  }
  #account {
    margin-right: 30px;
  }

}

#account {
  font-size: 32px;
  color: white;
}

#logout {
  font-size: 32px;
  color: white;
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
  width: 375px;
  height: 50px;
  margin-top: 16px;
  background: #111315;
  border-radius: 10px;
  font-style: normal;
  font-weight: 600;
  font-size: 32px;
  line-height: 48px;
  text-align: start;
  color: white; 
}

nav input[type=text]::placeholder {
  font-family: 'Material Icons Outlined';
}


nav div a {
  font-weight: bold;
  color: white;
  text-decoration-line: none;
  margin-right: 20px;
  font-family: 'Poppins';
  font-style: normal;
  font-weight: 600;
  font-size: 24px;
}

</style>