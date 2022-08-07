<template>
  <div>
    <!-- 스피커 끄기 내가 다른 사람 소리 안 듣게 -->
    <button type="button" @click="$emit('audioOnOff'), audioIcon()">
      <span v-if="audioValue" class="material-symbols-outlined">volume_up</span>
      <span v-if="!audioValue" class="material-symbols-outlined">volume_off</span>
    </button>  
    <!-- 내 영상 공유 중지 -->
    <button type="button" @click="$emit('videoOnOff'), videoIcon()">
      <span v-if="videoValue" class="material-symbols-outlined">stop_circle</span>
      <span v-if="!videoValue" class="material-symbols-outlined">play_circle</span>
    </button>

    <!-- <button type="button" @click="$emit('shareScreen')">화면공유??</button> 추후 개발
    <button type="button" @click="$emit('shareDrawing')">드로잉</button> --> 

    <!-- 나가기 -->
    <button type="button" class="leave-meeting" @click="$emit('sessionExit')"><span class="material-symbols-outlined">
logout
</span></button>
  <div id="sideBar">
    <button type="button" @click="$emit('userListOnOff')">유저목록</button>  
    <button type="button" @click="$emit('chattingOnOff')">채팅창</button>  
  </div>
  </div>
</template>

<script>
import {ref} from 'vue'

export default {
  name: 'ConferenceFooter',
  components: {},
  setup() {
    const audioValue = ref(true)
    const videoValue = ref(true)

    const audioIcon = () =>{
      audioValue.value = !audioValue.value
    }

    const videoIcon = () =>{
      videoValue.value = !videoValue.value
    }
    
    return {
      audioValue,
      videoValue,
      audioIcon,
      videoIcon,
    }
  },
  create() {},
  mounted() {},
  unmounted() {},
  methods: {},
}
</script>

<style scoped>
div #sideBar {
  float: right;
}

button{
  font-size: 10px;
  padding: 4px;
}

button.leave-meeting{
    font-size: 10px;
  padding: 4px;
  color: rgb(242, 235, 235);
  background-color: rgb(234, 71, 71);
}

</style>
