<template>
  <div class="user-icon">
    <span class="material-symbols-outlined" id="close">close</span>
    <p class="username">장종훈</p>
  </div>
</template>

<script>
export default {

}
</script>

<style scoped>
#close {
  font-size: 16px;
  color: black;
}

.user-icon {
  margin-left: 8px;
  margin-top: 2px;
  background-image: url(@/assets/profile.jpg);
  background-size: cover;
  width: 40px;
  height: 40px;
  border-radius: 100%;
  display: flex;
  flex-direction: column;
  text-align: end;
}

.username {
  width: 34px;
  height: 14px;
  font-size: 8px;
  color: black;
}
</style>