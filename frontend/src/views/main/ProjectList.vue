<template>
  <a href="/project/1" class="project-box">
    <div class="box">
      <span class="material-symbols-outlined" id="terminal">terminal</span>
      <span style="font-size: 20px">PJT-name</span>
    </div>
    <div class="box">
      <span class="material-symbols-outlined" id="group">group</span>
      <span style="font-size: 20px">Members</span>
      <span class="member">3</span>
    </div>
    <!-- <div class="box">
      <span class="material-symbols-outlined" id="cancel">cancel_presentation</span>
      <div class="off">OFF</div>
    </div> -->
    <div class="box">
      <span class="material-symbols-outlined" id="video">video_chat</span>
      <div class="on">ON</div>
    </div>
    <div class="host-box">
      <span class="material-symbols-outlined" id="person">person</span>
      <div class="host-data">
        <p>Host</p>
        <p>SSAFY@SSAFY.com</p>
      </div>
    </div>
  </a>
</template>

<script>
export default {
}
</script>

<style>
.project-box {
  text-decoration: none;
  width: 280px;
  height: 280px;
  left: 196px;
  top: 226px;
  background: #111315;
  border-radius: 10px;
  display: flex;
  color: white;
  display: flex;
  justify-content: flex-end;
  flex-direction: column;
}

.on {
  width: 28px;
  height: 20px;
  background: #E73939;
  border-radius: 5px;
  font-weight: 600;
  font-size: 16px;
  line-height: 120%;
  color: #FFFFFF;
}

.off {
  width: 36px;
  height: 20px;
  border-radius: 5px;
  background: #9E9E9E;
  font-family: 'Inter';
  font-style: normal;
  font-weight: 600;
  font-size: 16px;
  line-height: 120%;
  color: #FFFFFF;
}

.member {
  width: 24px;
  height: 20px;
  background: #BFB2FF;
  border-radius: 5px;
  font-weight: 600;
  font-size: 16px;
  line-height: 120%;
  color: #FFFFFF;
  margin-left: 28px;
}

#terminal {
  font-size: 24px;
  color: white;
  margin-right: 20px;
  margin-bottom: 10px;
}

#group {
  font-size: 24px;
  color: white;
  margin-right: 20px;
  margin-bottom: 10px;
}

#person {
  margin-top: 20px;
  margin-left: 10px;
  margin-right: 20px;
  font-size: 52px;
}

#cancel{
  font-size: 24px;
  margin-right: 20px;
}

#video {
  font-size: 24px;
  margin-right: 20px;
}

.box {
  margin-top: 20px;
  margin-left: 50px;
  display: flex;
}

.host-box {
  background: #9E9E9E;
  border-radius: 5px;
  width: 280px;
  height: 92px;
  margin-top: 28px;
  display: flex;
}

.host-data {
  text-align: start;
}
</style>