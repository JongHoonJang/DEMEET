<template>
  <div class="dump">
		<!-- <div id="session" v-if="session"> -->
		<div id="session">
			<div id="main-video" class="col-md-6">
				<user-video :stream-manager="mainStreamManager"/>
			</div>
			<div id="video-container" class="col-md-6">
				<user-video :stream-manager="publisher" @click="updateMainVideoStreamManager(publisher)"/>
				<user-video v-for="sub in subscribers" :key="sub.stream.connection.connectionId" :stream-manager="sub" @click="updateMainVideoStreamManager(sub)"/>
			</div>
		</div>
  </div>
</template>

<script>
// 튜토리얼 복붙
import UserVideo from './UserVideo'

export default {
  
  name: 'ConferenceVideo',
  components: {    UserVideo},
  
  data () {
		return {
			// OV: undefined,
			// session: undefined,
			// mainStreamManager: undefined,
			// publisher: undefined,
			// subscribers: [],

			mySessionId: 'SessionA',
			myUserName: 'Participant' + Math.floor(Math.random() * 100),
		}
	},
	
	props:{
		OV:Object,
		session:Object,
		mainStreamManager:Object,
		publisher:Object,
		subscribers :Object
	},
}
</script>

<style scoped>
div.dump {
  background-color: 0D131E;
  width: 100vh;
  height: 100vh;
}

</style>