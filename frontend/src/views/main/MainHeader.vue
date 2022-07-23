<template>
  <header>
    <div class="container">
        <div class="create-box">
          <a href="">
            <span class="material-symbols-outlined">add</span>
          </a>
        </div>
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

<style>
header {
  margin-top: 100px;
}

.material-symbols-outlined {
  font-size: 64px;
  color: white;
}

.create-box {
  width: 280px;
  height: 280px;
  left: 196px;
  top: 226px;
  background: #111315;
  border-radius: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.container {
  display: flex;
  justify-content: space-around;
  flex-direction: row;
}
</style>