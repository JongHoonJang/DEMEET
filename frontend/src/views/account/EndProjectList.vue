<template>
  <router-link :to="{ name: 'DetailView', params: {pid: pid}}" class="project-container">
    <img class="project-img" src="https://images.edrawsoft.com/kr/sample.jpg" alt="">
    <div class="project-data">
      <p>PJT name: {{ endPjt.endProject.pjtName }}</p>
      <p>start PJT: {{ endPjt.endProject.pjtStartDate }}</p>
      <p>end PJT: {{ endPjt.endProject.pjtEndDate }}</p>
      <p>member: {{ endPjt.endProject.member.length }}</p>
    </div>
  </router-link>
</template>

<script>
import { defineComponent,ref } from "vue"
// import { useAccountStore } from "@/stores/account"
export default defineComponent({
  props:['endProject'],
  setup(props) {
    const endPjt = ref(props)
    const pid = ref(endPjt.value.endProject.pid)
    return {
      pid,
      endPjt
    }
  }
})
</script>

<style>
.project-container {
  width: 300px;
  height: 440px;
  background: #111315;
  text-decoration: none;
  border-radius: 10px;
  margin: 50px;
}
.project-img {
  width: 300px;
  height: 300px;
  border-radius: 10px;
}
.project-data {
  display: flex;
  flex-direction: column;
  text-align: start;
  color: white;
}
.project-data p {
  margin: 8px;
}
</style>