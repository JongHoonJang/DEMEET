<template>
  <div class="container">
    <div>
      <input type="text" class="search" placeholder="Search User">
      <div class="user-list">
        <UserList />
        <UserList />
        <UserList />
        <UserList />
      </div>
    </div>
  </div>

</template>

<script>
import UserList from '@/views/main/UserList'
export default {
  components: {
    UserList
  }
}
</script>

<style scoped>
.user-list {
  margin-top: 50px;
  width: 328px;
  height: 320px;
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