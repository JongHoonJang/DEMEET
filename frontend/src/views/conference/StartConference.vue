<template>
  <div id="join">
			<a href="/">
				<div id="img-div"> <img class='bg-image' src="@/assets/DEMEET_logo.png" alt="" ></div>
			</a>
			<div id="join-dialog" class="jumbotron vertical-center">
				<h1>Join a video session</h1>
				<div class="form-group">
					<p>Participant : {{demeet.profile.nickname}}</p>	
					<p>Session : {{demeet.project.pjtName}}</p>
					
					<p class="text-center">
						<button class="btn btn-lg btn-success" @click="$emit('joinSession')">Join!</button>
					</p>
				</div>
			</div>
		</div>
</template>

<script>
import { defineComponent,ref } from 'vue'
import { useRoute } from 'vue-router'
import { useAccountStore } from "@/stores/account"
import axios from 'axios'
export default defineComponent({
  async setup() {
    const demeet = useAccountStore()
    const route = useRoute()  
    const project_pk = ref(route.params.pid)
    await axios(process.env.VUE_APP_API_URL + "projects/" + project_pk.value, {
      method: "get",
      headers: demeet.authHeader
    }).then(res => demeet.project = res.data.project)
    demeet.fetchProfile()
    return {
      demeet
    }
  }
})
</script>

<style>

</style>