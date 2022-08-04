<template>
<div class="search-bar">
  <div class="pjt-name">pjt-name</div>
  <input type="text">
</div>
  <div class="container">
    <div>
      <input 
      type="text" 
      class="search" 
      placeholder="Search User"
      v-model="searchUser" 
      @keyup.enter="findData(searchUser)"
      >
      <div class="user-list">
        <UserList 
        v-for="user in userData"
        :key="user.uid"
        :user="user"
        />
      </div>
    </div>
  </div>
  <button>start project</button>
</template>

<script>
import { ref,defineComponent } from "vue"
import { useAccountStore } from "@/stores/account"

import UserList from '@/views/main/UserList'
export default defineComponent({
  components: {
    UserList
  },

  setup() {
    const account = useAccountStore()
    account.fetchUserList()
    const userData = ref(account.userList)
    const searchUser = ''
    let searchList 
    const findData = (inputData) => {
      if (inputData.length != 0){
        searchList = userData.value.filter(user => user.nickname.includes(inputData))
        console.log(searchList)
      }
    }
    return {
      account,
      userData,
      searchUser,
      findData
    }
  }
})
</script>

<style>
.search-bar{
  display: flex;
  justify-content: center;
}

.user-list {
  margin-top: 50px;
  width: 328px;
  height: 234px;
  background: #333333;
  display: flex;
  flex-direction: column;
  border-radius: 10px;
}
.container {
  margin: 30px;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}
.search {
  margin-top: 50px;
  width: 320px;
  height: 24px;
  font-family: 'Inter';
  font-style: normal;
  font-weight: 600;
  font-size: 24px;
  line-height: 120%;
  background: #333333;
  color: white;
  border-radius: 5px;
}
</style>