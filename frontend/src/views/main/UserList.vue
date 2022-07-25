<template>
  <div class="user-box">
    <div class="user-data">
      <img src="@/assets/profile.jpg" alt="">
      <div>
        <div class="text-type">장종훈</div>
        <div class="text-type">jangjr98@</div>
      </div>
    </div>
    <button class="plus-btn">
      <span class="material-symbols-outlined" id="mail">mail</span>
      <span class="plus">초대</span>
    </button>
  </div>
</template>

<script>
export default {

}
</script>

<style scoped>
#mail {
  margin: 4px;
}

.user-box {
  margin: 14px;
  width: 300px;
  height: 50px;
  background: linear-gradient(0deg, rgba(0, 0, 0, 0.2), rgba(0, 0, 0, 0.2)), rgba(45, 140, 255, 0.1);
  border-radius: 15px;
  display: flex;
  justify-content: space-between;
}

img {
  margin: 5px;
  width: 40px;
  height: 40px;
  border-radius: 100%;
}

.text-type {
  margin: 2px;
  text-align: start;
}

.plus {
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
</style>