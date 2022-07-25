<template>
  <div>
    <MainNav/>
    <div class='profile-box'>
      <div class='profile-bg'>
        <img class='bg-image' src="@/assets/profile_bg.jpg" alt="">
      </div>
      <div class='profile-id'>
        <div class='profile-image'>
          <span class="material-symbols-outlined" id='account'>account_circle</span>
        </div>
        <div class='profile-detail'>
          <div class='profile-rough'>
            <div class="name">
              <h1>Name
                <button>변경</button>
              </h1>
              <!-- <button>변경</button> -->
            </div>
            <div>
              <p>Job</p>
            </div>
          </div>
          <div class='change-password'>
            <button>Change Password</button>
          </div>
        </div>
      </div>

    </div>
    <div class='container'>
      <div class='PJT'>PJT1</div>
      <div class='PJT'>PJT2</div>
      <div class='PJT'>PJT3</div>
      <div class='PJT'>PJT4</div>
    </div>
  </div>
</template>

<script>
import MainNav from '@/views/main/MainNav'
export default {
  components: {
    MainNav
  }

}
</script>

<style scoped>
#account {
  font-size:300px;
}

/* .name {
  display: flex;
} */

.bg-image {
  width: 100%;
}
.profile-id {
  display: flex;
}
.profile-detail {
  width: 100%;
  display: flex;
  justify-content: space-between;
}
.PJT {
  width: 20%;
  height: 10vh;
}
/* .profile-projects {
  display: grid; */
/* } */
.container {
  margin-left: 100px;
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  flex-wrap: wrap;
}
</style>