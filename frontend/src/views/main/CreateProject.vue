<template>
  <div class="pjt-name">pjt-name</div>
  <input type="text">
  <AddUser />
  <div class="user-box">
      <UserIcon />
  </div>
  <button>start project</button>
</template>

<script>
import AddUser from '@/views/main/AddUser'
export default {
  components: {
    AddUser
  },
}
</script>

<style>

</style>