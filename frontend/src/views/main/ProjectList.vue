<template>
  <router-link :to="{ name:'DetailView', params: { project_pk: demeet.project.pid }}" class="project-box">
    <div class="box">
      <span class="material-symbols-outlined" id="terminal">terminal</span>
      <span style="font-size: 20px">{{ demeet.project.pjtName }}</span>
    </div>
    <div class="box">
      <span class="material-symbols-outlined" id="group">group</span>
      <span style="font-size: 20px">Members</span>
      <span class="member">{{ demeet.project.member.length }}</span>
    </div>
    <!-- <div class="box">
      <span class="material-symbols-outlined" id="cancel">cancel_presentation</span>
      <div class="off">OFF</div>
    </div> -->
    <div class="box">
      <span class="material-symbols-outlined" id="video">video_chat</span>
      <div class="on">ON</div>
    </div>
    <div class="host-box">
      <span class="material-symbols-outlined" id="person">person</span>
      <div class="host-data">
        <p>{{ host.nickname }}</p>
        <p>{{ host.email }}</p>
      </div>
    </div>
  </router-link>
</template>

<script>
import { defineComponent } from "vue"
import { useAccountStore } from "@/stores/account"
export default defineComponent({
  props: ['project'],
  setup(props) {
    console.log(props.project)
    const demeet = props
    const account = useAccountStore()
    account.userList()
    const host = account.userList.find(user => user.uid === demeet.project.projectOwner)
    return {
      demeet,
      host
    }
  }

})
</script>

<style scoped>
#terminal {
  font-size: 24px;
  color: white;
  margin-right: 20px;
  margin-bottom: 10px;
}

#group {
  font-size: 24px;
  color: white;
  margin-right: 20px;
  margin-bottom: 10px;
}

#person {
  margin-top: 20px;
  margin-left: 10px;
  margin-right: 20px;
  font-size: 52px;
}

#cancel{
  font-size: 24px;
  margin-right: 20px;
}

#video {
  font-size: 24px;
  margin-right: 20px;
}

.project-box {
  margin-bottom: 50px;
  margin-left: 25px;
  margin-right: 25px;
  text-decoration: none;
  width: 280px;
  height: 280px;
  background: #111315;
  border-radius: 10px;
  display: flex;
  color: white;
  display: flex;
  justify-content: flex-end;
  flex-direction: column;
  float: right;
}

.on {
  width: 28px;
  height: 20px;
  background: #E73939;
  border-radius: 5px;
  font-weight: 600;
  font-size: 16px;
  line-height: 120%;
  color: #FFFFFF;
}

.off {
  width: 36px;
  height: 20px;
  border-radius: 5px;
  background: #9E9E9E;
  font-family: 'Inter';
  font-style: normal;
  font-weight: 600;
  font-size: 16px;
  line-height: 120%;
  color: #FFFFFF;
}

.member {
  width: 24px;
  height: 20px;
  background: #BFB2FF;
  border-radius: 5px;
  font-weight: 600;
  font-size: 16px;
  line-height: 120%;
  color: #FFFFFF;
  margin-left: 28px;
}

.box {
  margin-top: 20px;
  margin-left: 50px;
  display: flex;
}

.host-box {
  background: #9E9E9E;
  border-radius: 5px;
  width: 280px;
  height: 92px;
  margin-top: 28px;
  display: flex;
}

.host-data {
  text-align: start;
}
</style>