<template>
<div>
  <MainNav class="nav"/>
    <div class="container">
      <div>
        <h1>저장된 이미지</h1>
        <img src="https://images.edrawsoft.com/kr/sample.jpg" alt="">
      </div>
      <div class="detail-box">
        <ProjectData 
        :project="project.project"
        :key="project.pid"
        />
      </div>
    </div>
</div>
</template>

<script>
import { defineComponent } from "vue"
import { useAccountStore } from "@/stores/account"
import { useRoute } from 'vue-router'
import MainNav from '@/views/main/MainNav'
import ProjectData from '@/views/main/ProjectData'
export default defineComponent({
  components: {
    MainNav,
    ProjectData
  },
  setup() {
    const route = useRoute()  
    const project_pk = route.params.project_pk
    const project = useAccountStore()
    project.fetchProject(project_pk)
    return {
      project,
    }
  },
})
</script>

<style scoped>
.nav {
  width:80%; 
}
#person {
  font-size: 60px;
  margin-left: 16px;
  margin-top: 16px;
  margin-right: 200px;
}

#edit {
  color:#9E9E9E;
  margin: 16px;
}

.container {
  display: flex;
  justify-content: space-around;
}

.detail-box{
  width: 816px;
  height: 852px;
  background: #111315;
  border-radius: 10px;
  display: flex;
  flex-direction: column;
}


.host-box {
  background: #9E9E9E;
  border-radius: 10px;
  width: 816px;
  height: 92px;
  display: flex;
  align-items: flex-start;
  text-align: start;
}

h1 {
  color: white;
}

img {
  width: 500px;
  height: 300px;
}
</style>