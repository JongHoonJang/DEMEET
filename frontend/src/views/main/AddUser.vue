<template>
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
        v-for="user in userList"
        :key="user.uid"
        :user="user"
        :project="users.project"
        />
      </div>
    </div>
  </div>
</template>

<script>
import { defineComponent, ref } from "vue"
// import { useAccountStore } from "@/stores/account"

import UserList from '@/views/main/UserList'
export default defineComponent({
  components: {
    UserList
  },
  props: ['userList','project'],
  setup(props) {
    const users = props
    const searchUser = ''
    const searchList = ref([]) 
    const findData = (inputData) => {
      if (inputData.length != 0){
        searchList.value = users.userList.filter(user => user.nickname.includes(inputData))
        console.log(searchList)
      }
    }
    
    return {
      users,
      searchUser,
      searchList,
      findData
    }
  }
})
</script>

<style scoped>
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