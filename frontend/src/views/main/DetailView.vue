<template>
    <MainNav />
    <div class="container">
      <div>
        <h1>저장된 이미지</h1>
        <img src="https://images.edrawsoft.com/kr/sample.jpg" alt="">
      </div>
      <div class="detail-box">
        <div class="detail-host-box">
          <span class="material-symbols-outlined" id="person">person</span>
          <div class="host-data">
            <p>Host</p>
            <p>SSAFY@SSAFY.com</p>
          </div>
        </div>
        <div>
          <div class="pjt-name">
            <p>PJT-Name</p>
            <span class="material-symbols-outlined" id="edit">edit</span>
          </div>
          <div class="edit-box">Project1</div>
          <!-- <input id="edit-box" type="text" placeholder="Project1"> -->
          <div class="pjt-name">
            <p>PJT-Detail</p>
            <span class="material-symbols-outlined" id="edit">edit</span>
          </div>
          <div class="edit-box">Project Detail</div>
          <!-- <textarea name="" id="" cols="30" rows="10"></textarea> -->
        </div>
      </div>
    </div>
</template>

<script>
import MainNav from '@/views/main/MainNav'
export default {
  components: {
    MainNav,
  }
}
</script>

<style>
#edit {
  color:#9E9E9E;
  margin: 16px;
}

.edit-box {
  width: 600;
  height: 56;
  background: #333333;
}

.data-box {
  width: 600;
  height: 600;
}

h1 {
  color: white;
}

img {
  width: 500px;
  height: 300px;
}

.container {
  margin-top: 100px;
  display: flex;
}

.detail-box{
  width: 816px;
  height: 852px;
  background: #111315;
  border-radius: 10px;
  display: flex;
  flex-direction: column;
}

.detail-host-box {
  background: #9E9E9E;
  border-radius: 10px;
  width: 816px;
  height: 92px;
  display: flex;
  align-items: flex-start;
}

.pjt-name {
  display: flex;
  justify-content: space-around;
}
</style>