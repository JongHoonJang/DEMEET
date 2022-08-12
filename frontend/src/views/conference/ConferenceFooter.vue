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
    <!-- 내 영상 공유 중지 -->
    <button type="button" @click="$emit('videoOnOff'), videoIcon()">
      <span v-if="videoValue" class="material-symbols-outlined">stop_circle</span>
      <span v-if="!videoValue" class="material-symbols-outlined">play_circle</span>
    </button>

    <button v-if="!isSharing" type="button" @click="$emit('shareScreen')">화면공유</button>
    <button v-if="isSharing" type="button" >공유 중</button>
    <button type="button" @click="$emit('shareDrawing')">드로잉</button> 
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

    const audioIcon = () =>{
      audioValue.value = !audioValue.value
    }

    const videoIcon = () =>{
      videoValue.value = !videoValue.value
    }

    const micIcon = () =>{
      micValue.value = !micValue.value
    }
    
    return {
      audioValue,
      videoValue,
      micValue,
      audioIcon,
      videoIcon,
      micIcon,
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