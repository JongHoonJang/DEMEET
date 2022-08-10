<template>
<div class="bar"></div>
<div class="name-bar">
  <input class="name-input" v-model="projectData.pjt_name" type="text" placeholder="pjt-name">
</div>
  <div class="create-container">
    <div>
      <input 
      type="text" 
      class="search" 
      placeholder="Search User"
      v-model.trim="searchUser" 
      @input="findData()"
      >
      <div class="user-list">
        <div 
        v-for="user in searchList"
        :key="user.uid"
        :user="user"
        >
          <div class="user-box" v-if="user.uid !== account.profile.uid">
            <div class="user-data">
              <img class="user-img" v-if="user.profileImagePath===null" src="@/assets/기본프로필.jpg" alt="">
              <img class="user-img" v-else-if="user.profileImagePath!==null" :src="`${user.profileImagePath}`" alt="">
              <div>
                <div class="text-type">{{ user.nickname }}</div>
                <div class="text-type">{{ user.email }}</div>
              </div>
            </div>
            <button
            v-if="projectData.memberList.some(res => res === user.uid)"  
            @click="remove(user)"  
            class="cancle-btn"
            >
              <span class="material-symbols-outlined" id="mail">mail</span>
              <span class="cancel">취소</span>
            </button>
            <button
            v-else
            @click="add(user)" 
            class="plus-btn"
            >
              <span class="material-symbols-outlined" id="mail">mail</span>
              <span class="plus">초대</span>
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
  <button class="create-btn" @click="account.createProject(projectData)">start project</button>
</template>

<script scoped>
import { ref,defineComponent } from "vue"
import { useAccountStore } from "@/stores/account"

export default defineComponent({
  setup() {
    const projectData = ref({
      pjt_name: '',
      memberList: [],
    })
    const add = (addUser) => {
      projectData.value.memberList.push(addUser.uid)
    }
    const remove = (addUser) => {
      projectData.value.memberList.pop(addUser.uid)
    }
    const account = useAccountStore()
    const userData = ref(account.userList)
    const searchUser = ref('')
    const searchList = ref([]) 
    const findData = () => {
      if (searchUser.value.length != 0){
        const result = account.userList.filter(user => user.nickname.includes(searchUser.value))
        result.push(...account.userList.filter(user => user.email.includes(searchUser.value)))
        const res = new Set(result)
        const resData = [...res]
        searchList.value = resData.slice(0,3)
      }
    }
    return {
      account,
      userData,
      searchUser,
      searchList,
      projectData,
      findData,
      add,
      remove
    }
  },
  async created () {
    await this.account.fetchUserList()
  }
})
</script>

<style>
.bar {
  width: 300px;
  height: 34px;
}


.name-input {
  margin-top: 10px;
  width: 320px;
  height: 24px;
  font-family: 'Inter';
  font-style: normal;
  font-weight: 600;
  font-size: 24px;
  line-height: 120%;
  border-style: solid;
  border-color: linear-gradient(90deg, #FF00D6 8.81%, #00E0FF 94.11%);
  background: #333333;
  color: white;
  border-radius: 5px;
}

.name-bar{
  display: flex;
  justify-content: center;
}

.user-list {
  margin-top: 50px;
  width: 328px;
  height: 250px;
  background: #333333;
  display: flex;
  flex-direction: column;
  border-style: solid;
  border-color: linear-gradient(90deg, #FF00D6 8.81%, #00E0FF 94.11%);
  border-radius: 10px;
}
.create-container {
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
  border-style: solid;
  border-color: linear-gradient(90deg, #FF00D6 8.81%, #00E0FF 94.11%);
  background: #333333;
  color: white;
  border-radius: 5px;
}

#mail {
  margin: 4px;
}

.user-box {
  margin: 14px;
  width: 300px;
  height: 50px;
  background: linear-gradient(0deg, rgba(0, 0, 0, 0.2), rgba(0, 0, 0, 0.2)), rgba(45, 140, 255, 0.1);
  border-style: solid;
  border-color: linear-gradient(90deg, #FF00D6 8.81%, #00E0FF 94.11%);
  border-radius: 15px;
  display: flex;
  justify-content: space-between;
}

.user-img {
  margin: 5px;
  width: 40px;
  height: 40px;
  border-radius: 100%;
}

.text-type {
  margin: 2px;
  text-align: start;
  color: white;
}

.plus {
  font-size: 16px;
  margin: 4px;
}

.cancel {
  font-size: 16px;
  margin: 4px;
}
.user-data {
  font-size: 16px;
  display: flex;
}

.plus-btn{
  display: flex;
  align-items: flex-start;
  padding: 0px;
  width: 80px;
  height: 36px;
  border-radius: 10px;
  margin-top: 6px;
  background: #4C4E50;
  color: white;
  margin: 5px;
}
.plus-btn:hover {
  color: green;
}

.cancle-btn {
  display: flex;
  align-items: flex-start;
  padding: 0px;
  width: 80px;
  height: 36px;
  border-radius: 10px;
  margin-top: 6px;
  background: #4C4E50;
  color: white;
  margin: 5px;
}

.cancle-btn:hover {
  color: red;
}
.create-btn {
  margin: 20px;
  width: 120px;
  height: 30px;
  background: #2097F7;
  border-radius: 5px;
  color: white;
}
.create-btn:hover{
  transform: scale(1.1);
  color: blue;
}
</style>