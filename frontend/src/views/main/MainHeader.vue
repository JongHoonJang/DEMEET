<template>
  <header>
    <div class="container">
      <div class="create">
        <div class="create-box" @click="isModalViewed=true">
            <span class="material-symbols-outlined" id="add">add</span>
        </div>
        <ModalView v-if="isModalViewed" @close-modal="isModalViewed=false">
          <CreateProject />
        </ModalView>
      </div>
        <ProjectList 
        v-for="project in projects.projects"
        :key="project.pid"
        :project="project"
        />
      </div>
  </header>
</template>

<script>
import { defineComponent,ref } from "vue"
import { useAccountStore } from "@/stores/account"
import ModalView from '@/views/main/ModalView'
import ProjectList from '@/views/main/ProjectList'
import CreateProject from '@/views/main/CreateProject'
export default defineComponent({
  components: {
    ProjectList,
    ModalView,
    CreateProject
  },
  data() {
    return {
      isModalViewed: false,
    }
  },
  setup() {
    const projects = ref(useAccountStore())
    return {
      projects,

    }
  },
  async created() {
    await this.projects.fetchProjects()
    await this.projects.fetchUserList()
    await this.projects.projects
  },
  async updated() {
    await this.projects.fetchProjects()
  },
})
</script>

<style scoped>
#add {
  font-size: 64px;
  color: white;
}

.create {
  text-decoration: none;
}

.create-box {
  margin-bottom: 50px;
  margin-left: 25px;
  margin-right: 25px;
  width: 280px;
  height: 280px;
  background: #111315;
  border-radius: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.container {
  margin-left: 100px;
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  flex-wrap: wrap;
}
</style>