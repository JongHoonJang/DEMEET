<template>
  <div class="dump">
		<!-- <div id="session" v-if="session"> -->
		<div id="session">
			<!-- <div id="main-video" class="col-md-6">
				<user-video :stream-manager="mainStreamManager"/>
			</div> -->
			<div id="video-container" class="col-md-6">
				<!-- 내얼굴 -->
				<user-video v-if="publisher==secondPublisher" :streamManager="publisher" :isDrawing="isDrawing" @click="$emit('mainVideoChange', publisher)"/>   
				<user-video v-for="sub in subscribers" :key="sub.stream.connection.connectionId" :streamManager="sub"  @click="$emit('mainVideoChange', sub)"/>
			</div>
		</div>
  </div>
</template>

<script>
// 튜토리얼 복붙
import UserVideo from './UserVideo'


export default {
  
  name: 'ConferenceVideo',
  components: {UserVideo},
	
	props:{ 
		session:{
			type:Object,
			default: () => {
				return {}
			}
		},
		publisher:{
			type:Object,
			default: () => {
				return {}
			}
		},
		subscribers:{
			type:Array,
			default: () => {
				return []
			}
		},
		secondPublisher:{
			type:Array,
			default: () => {
				return []
			}
		},
		isDrawing:{
			type:Boolean
		}
	},

	setup(){
		const mySessionId = 'SessionA'
		const myUserName = 'Participant'

	return {
		mySessionId,
		myUserName,
		}
	},
}
</script>

<style scoped>

@media all and (max-width: 1024px){
		#video-container {
		display: flex;
		flex-direction: row;
		justify-content: space-between;
		align-items: stretch;
	}

}

/* div.dump {
  background-color: 0D131E;
  width: 20vw;
  height: 100vh;
} */

</style>