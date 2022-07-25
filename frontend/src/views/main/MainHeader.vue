<template>
  <header>
    <div class="container">
      <a href="/project/1" class="create">
        <div class="create-box">
            <span class="material-symbols-outlined" id="add">add</span>
        </div>
      </a>
        <ProjectList 

        />
        <ProjectList 

        />
        <ProjectList 

        />
        <ProjectList 

        />
        <ProjectList 

        />
        <ProjectList 

        />
        <ProjectList 

        />
        <ProjectList 

        />
        <ProjectList 

        />
      </div>
  </header>
</template>

<script>
import ProjectList from '@/views/main/ProjectList'
export default {
  components: {
    ProjectList
  }
}
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
  margin-right: 100px;
  display: flex;
  justify-content: space-between;
  flex-wrap: wrap;
}
</style>