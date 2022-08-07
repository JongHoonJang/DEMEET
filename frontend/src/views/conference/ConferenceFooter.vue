<template>
  <div>
    <!-- 내가 다른 사람 소리 안 듣게 -->
    <button type="button" @click="$emit('audioOnOff')">음소거</button>  
    <!-- 내 영상 공유 중지 -->
    <button type="button" @click="$emit('videoOnOff')">영상On</button>
    <button type="button" @click="$emit('shareScreen')">화면공유??</button>
    <button type="button" @click="$emit('shareDrawing')">드로잉</button>
    <button type="button" @click="$emit('sessionExit')">퇴장</button>
  </div>
</template>

<script>
export default {
  name: 'ConferenceFooter',
  components: {},
  data() {
    return {
      sampleData: ''
    };
  },
  setup() {

  },
  create() {},
  mounted() {},
  unmounted() {},
  methods: {},
}
</script>

<style scoped>

</style>
