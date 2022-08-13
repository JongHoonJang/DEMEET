<template>
  <div>
    <!-- 소리 -->
    <!-- <button type="button" @click="$emit('micOnOff'), micIcon()">
      <span v-if="micValue" class="material-symbols-outlined">volume_up</span>
      <span v-if="!micValue" class="material-symbols-outlined">volume_off</span>
    </button>   -->
    <!-- 마이크 음소거 -->
    <button type="button" @click="$emit('audioOnOff'), audioIcon()">
      <span v-if="audioValue" class="material-symbols-outlined">mic</span>
      <span v-if="!audioValue" class="material-symbols-outlined">mic_off</span>
    </button>  
    <!-- 내 얼굴 영상 공유 중지 -->
    <button type="button" @click="$emit('videoOnOff'), videoIcon()">
      <span v-if="videoValue" class="material-symbols-outlined">stop_circle</span>
      <span v-if="!videoValue" class="material-symbols-outlined">play_circle</span>
    </button>
    <!-- 컴퓨터 화면 공유 -->
    <button v-if="!isSharing" type="button" @click="$emit('shareScreen')"><span class="material-symbols-outlined">screen_share</span></button>
    <button v-if="isSharing" type="button" ><span class="material-symbols-outlined">stop_screen_share</span></button>
    {{ isSharing }}
    <!-- 드로잉 버튼 -->
    <button type="button" @click="$emit('shareDrawing'), drawIcon()">
      <span v-if="drawValue" class="material-symbols-outlined">draw</span>
      <span v-if="!drawValue" class="material-symbols-outlined">edit_off</span>
    </button> 
    <!-- <button type="button" @click="$emit('shareDrawing')"><a href="http://127.0.0.1:8081/drawing" target="_blank">drawing</a></button>  -->

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

  props: {
		isSharing:{
			type:Boolean,
		},
    isDrawing:{
      type:Boolean,
    }
	},


  setup() {
    const audioValue = ref(true)
    const videoValue = ref(true)
    const micValue = ref(true)
    const drawValue = ref(true)

    const audioIcon = () =>{
      audioValue.value = !audioValue.value
    }

    const videoIcon = () =>{
      videoValue.value = !videoValue.value
    }

    const micIcon = () =>{
      micValue.value = !micValue.value
    }

    const drawIcon = () => {
      drawValue.value = !drawValue.value
    }
  
  return {
    audioValue,
    videoValue,
    micValue,
    drawValue,
    audioIcon,
    videoIcon,
    micIcon,
    drawIcon,
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