<!-- 임시로 옵션 API 에서 컴포지션 API 로 수정 중 -->

<template>
<div class="conferenve-container">
	<Suspense v-if="!conferenceAction" >
		<template #default>
			<StartConference 
			@joinSession="joinSession"
			/>
		</template>
		<template #fallback>
			<h1>Loading...</h1>
		</template>
	</Suspense>
</div>
	<div id="session" v-if="conferenceAction">

  <main>
		<div id="conference-main">
        <!-- 영상, 드로잉 등 -->
			<ConferenceVideo
				:session = "session"
				:mainStreamManager = "mainStreamManager"
				:publisher ="publisher"
				:subscribers = "subscribers"
				:isDrawing="isDrawing"
			/>
			<DrawingView 
				v-if="isDrawing"
				:openviduSessionId="openviduSessionId"
			/>

      <!-- 참자가 목록, 채팅 -->
			<ConferenceUsers
				v-if="userListStatus"
				:publisher="publisher"
				:subscribers="subscribers"
				:users="users"
			/>
			<div
				id="chat-box"
				v-if="chattingStatus"
				style="box: 5px 5px 5px"
			>
			<messageList
				:msgs="msgs"
				:myId="myId"
				:fromId="fromId"
			/>
			<messageForm
				v-if="chatting"
				style="width:100%"
				v-on:sendMsg="sendMsg"
				:user-name="myUserName"
			/>
			</div>
		</div>	
	
  </main>
  <footer>
		<!-- 동작 버튼 등,  -->
		<ConferenceFooter
		:isSharing="isSharing"
		@audio-on-off="audioOnOff"  
		@video-on-off="videoOnOff"
		@mic-on-off="micOnOff"
		@share-screen="startShareScreen"
		@share-drawing="shareDrawing"
		@session-exit="leaveSession"
		@user-list-on-off="userListOnOff"
		@chatting-on-off="chattingOnOff"
		/>
	</footer>
    
</div>


</template>

<script>
// import { fabric } from 'fabric'
import ConferenceVideo from './ConferenceVideo'
import ConferenceUsers from './ConferenceUsers'
import ConferenceFooter from './ConferenceFooter'
import DrawingView from './DrawingView'
import StartConference from './StartConference'
import messageForm from './chat/messageForm.vue'
import messageList from './chat/messageList.vue'
import { useRoute } from 'vue-router'
// 튜토리얼 복붙
import axios from 'axios'
import { OpenVidu } from 'openvidu-browser'
import { ref } from 'vue'
//추가
import api from "@/api/api"
// pinia
import { useAccountStore } from "@/stores/account"

axios.defaults.headers.post['Content-Type'] = 'application/json'

const OPENVIDU_SERVER_URL = "https://" + 'i7b309.p.ssafy.io' + ":4443"
const OPENVIDU_SERVER_SECRET = "wlwhseodnjs123"

export default {


components: {
	ConferenceVideo,
	ConferenceUsers,
	ConferenceFooter,
	StartConference,
	DrawingView,
	messageForm,
	messageList
},

setup() {
	const account = useAccountStore()
	const route = useRoute()  
	account.fetchProfile()
	const userData = ref(account.profile)
	let OV = ref(undefined)
	let session = ref(undefined)
	let mainStreamManager= ref(undefined)
	let publisher = ref(undefined)
	let secondPublisher = ref(undefined)
	let subscribers = ref([])
	let conferenceAction = ref(false)
	let users = ref([])
	var openviduSessionId = ref(null)
	const mySessionId = route.params.sessionId
	// pinai 
	const myUserName = userData.value.nickname
	const msgs = ref([])
	const chatting = true
	const fromId = ref("")
	const myId = ref("")

	// foooter 작동을 위한 변수
	const micStatus = ref(100)
	const audioStatus = ref(true)
	const videoStatus = ref(true)
	const userListStatus = ref(true)
	const chattingStatus = ref(true)
	const isSharing = ref(false)
	const isDrawing = ref(false)
	
	const joinSession = () => {
		// --- Get an OpenVidu object ---
			OV.value = new OpenVidu()

			// --- Init a session ---
			session.value = OV.value.initSession()

			// --- Specify the actions when events take place in the session ---
			// On every new Stream received...
			session.value.on('streamCreated', ({ stream }) => {
				var subscriber = session.value.subscribe(stream)
				subscribers.value.push(subscriber)
				users.value.push(subscriber)
			})



			// On every Stream destroyed...
			session.value.on('streamDestroyed', ({ stream }) => {
				const index = subscribers.value.indexOf(stream.streamManager, 0)
				if (index >= 0) {
					subscribers.value.splice(index, 1)
				}
			})

			// On every asynchronous exception...
			session.value.on('exception', ({ exception }) => {
				console.warn(exception)
			})
			// Receiver of the message (usually before calling 'session.connect')
			session.value.on("signal:my-chat", event => {
				fromId.value = event.from.connectionId
				const tmp = msgs.value.slice()
				tmp.push(event.data)
				msgs.value = tmp
			})
			// --- Connect to the session with a valid user token ---

			// 'getToken' method is simulating what your server-side should do.
			// 'token' parameter should be retrieved and returned by your own backend

			getToken((token) => {
				session.value.connect(token, {'clientData':myUserName})
				.then(() => {
					
					let ppublisher = OV.value.initPublisher(undefined, {
						audioSource: undefined, // The source of audio. If undefined default microphone
						videoSource: undefined, // The source of video. If undefined default webcam
						publishAudio: true,  	// Whether you want to start publishing with your audio unmuted or not
						publishVideo: true,  	// Whether you want to start publishing with your video enabled or not
						resolution: '320x240',  // The resolution of your video
						frameRate: 30,			// The frame rate of your video
						insertMode: 'APPEND',	// How the video is inserted in the target element 'video-container'
						mirror: false       	// Whether to mirror your local video or not
					})

					mainStreamManager  = ppublisher
					publisher.value = ppublisher

					// publish your stream
					session.value.publish(publisher.value)
					conferenceAction.value = true
				})
				.catch( error => {
					console.log('There was an error connecting to the session:', error.code, error.message)
				})
			})
			window.addEventListener('beforeunload', leaveSession)
		}

		const leaveSession = () => {
			// --- Leave the session by calling 'disconnect' method over the Session object ---

			if (session.value ) session.value.disconnect()

			session.value  = undefined
			mainStreamManager.value  = undefined
			publisher.value = undefined
			subscribers.value = []
			OV.value = undefined
			conferenceAction.value = false
			secondPublisher.value = undefined

			window.removeEventListener('beforeunload', leaveSession)
		}

		const updateMainVideoStreamManager = (stream) => {
			if (mainStreamManager.value  === stream) return
			mainStreamManager.value  = stream
		}

			/**
			 * --------------------------
			 * SERVER-SIDE RESPONSIBILITY
			 * --------------------------
			 * These methods retrieve the mandatory user token from OpenVidu Server.
			 * This behavior MUST BE IN YOUR SERVER-SIDE IN PRODUCTION (by using
			 * the API REST, openvidu-java-client or openvidu-node-client):
			 *   1) Initialize a Session in OpenVidu Server	(POST /openvidu/api/sessions)
			 *   2) Create a Connection in OpenVidu Server (POST /openvidu/api/sessions/<SESSION_ID>/connection)
			 *   3) The Connection.token must be consumed in Session.connect() method
	 */
	
	const getToken = (callback) => {
		
		httpPostRequest(
			'get-token',
				{sessionName : mySessionId},
				(response) => {
					let token = response // Get token from response
					let tempToken = token.substring(
					token.indexOf("ses_"),
					token.indexOf("&token")
					);
					openviduSessionId.value = tempToken;					
					
					callback(token) // Continue the join operation
				}
			)
		}

	const httpPostRequest = (url, body, callback) => {
		axios({
			'url': api.conferences.conference() + url,
						'method': 'post',
						'data': JSON.stringify(body),
		// 추후 연결 필요
		headers: account.authHeader
				
				})
				.then(res => {
						const token = res.data[0]
						callback(token)
				})
				.catch(err => (
						console.error(err.response),
						alert('컨퍼런스 토큰 저장에 실패하였습니다.')
				))
	}

	// See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-session
	const createSession = (sessionId) => {
		return new Promise((resolve, reject) => {
			axios
				.post(`${OPENVIDU_SERVER_URL}/openvidu/api/sessions`, JSON.stringify({
					mySessionId: sessionId,
				}), {
					auth: {
						username: 'OPENVIDUAPP',
						password: OPENVIDU_SERVER_SECRET,
					},
				})
				.then(response => response.data)
				.then(data => resolve(data.id))
				.catch(error => {
					if (error.response.status === 409) {
						resolve(sessionId)
					} else {
						// console.warn(`No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}`)
						if (window.confirm(`No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${OPENVIDU_SERVER_URL}"`)) {
							location.assign(`${OPENVIDU_SERVER_URL}/accept-certificate`)
						}
						reject(error.response)
					}
				})
		})
	}

	const getConnections = (sessionId) => {
		return new Promise((resolve, reject) => {

			axios
			.get(`${OPENVIDU_SERVER_URL}/openvidu/api/sessions/`, {
				params: {'SESSION_ID': sessionId},
				headers: {
					'Content-Type': 'application/json',
					'Authorization': `Basic ${btoa('OPENVIDUAPP:wlwhseodnjs123')}`,
				}
			},
			)
			.then(response => response.data)
			.then(data =>  resolve(data.id))
			.catch(error => reject(error.response))
		
		});
	};

	// See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-connection
	const createToken = (sessionId) => {
		return new Promise((resolve, reject) => {
			axios
				.post(`${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${sessionId}/connection`, {}, {
					auth: {
						username: 'OPENVIDUAPP',
						password: OPENVIDU_SERVER_SECRET,
					},
				})
				.then(response => response.data)
				.then(data => resolve(data.token))
				.catch(error => reject(error.response))
		})
	}

	const sendMsg =(msg) => {
	// Sender of the message (after 'session.connect')
		console.log(msg)
		session.value
			.signal({
				data: msg, // Any string (optional)
				to: [], // Array of Connection objects (optional. Broadcast to everyone if empty)
				type: "my-chat" // The type of message (optional)
			})
			.then(() => {
			})
			.catch(error => {
				console.error(error)
			})
	}

	const audioOnOff = () => {
		publisher.value.publishAudio(!audioStatus.value)
		audioStatus.value = !audioStatus.value
	}

	const videoOnOff = () => {
		publisher.value.publishVideo(!videoStatus.value)
		videoStatus.value = !videoStatus.value
	}

	const micOnOff = () => {
		if (micStatus.value){
			OV.value.setAdvancedConfiguration({
				publisherSpeakingEventsOptions: {interval: 100,threshold: -50}

			})
		}else{
			OV.value.setAdvancedConfiguration({
				publisherSpeakingEventsOptions: {interval: 100,   threshold: -50 }
			})
		}
		micStatus.value = !micStatus.value
	}

	const userListOnOff = () =>{
		userListStatus.value = !userListStatus.value

	}

	const chattingOnOff = () =>{
		chattingStatus.value = !chattingStatus.value
	}

	const shareDrawing = () => {
		if (isDrawing.value === false){
			startShareDrawing()
		}else{
			closeShareDrawing()
		}
	}

	const dumpMethod = () => {  // 작동 확인을 위한 함수
		// alert('dumpMethod 작동 확인')
	}


	// share screen 화면 공유 uservideo, users 전부 다 비동기 처리
	const startShareScreen = () => {
		isSharing.value = !isSharing.value
		secondPublisher.value = publisher.value
		var newPublisher = OV.value.initPublisher('ConferenceVideo', 
		{ 
			videoSource: "screen", 
			resolution: "1280x720",
			insertMode: "APPEND",
			publishAudio: true,
			publishVideo: true,
			frameRate: 30,
			mirror: false
		})

		newPublisher.once('accessAllowed', () => {
			newPublisher.stream.getMediaStream().getVideoTracks()[0].addEventListener('ended', () => {
				// 정지 누르면 다시 원상 복귀
				session.value.unpublish(publisher.value).then(() => {
				mainStreamManager.value = secondPublisher.value
				publisher.value = secondPublisher.value
				}).then(()=> {
					// publish your stream
					session.value.publish(publisher.value).then(()=>{
						secondPublisher.value = undefined
						isSharing.value = !isSharing.value
					})
				})
			})

		// Unpublishing the old publisher
		session.value.unpublish(publisher.value).then(() => {
			// Assigning the new publisher to our global variable 'publisher'
			mainStreamManager.value = newPublisher
			publisher.value = newPublisher
			
			}).then(() => {
				// Publishing the new publisher
				session.value.publish(publisher.value).then(() => {
				})
			})
		})
	}

	const startShareDrawing = () => {
		isDrawing.value = !isDrawing.value
		secondPublisher.value = publisher.value
	


		OV.value.getUserMedia({
			audioSource: false,
			videoSource: undefined, 
			resolution: '1280x720',
			frameRate: 30,
		})
		.then(() => {
			var canvas = document.getElementById('canvas')  // canvas 잡은 거 확인
			var stream = canvas.captureStream()

			var video = document.getElementById('videoID')
			video.srcObject = canvas.captureStream()

			// var grayVideoTrack = canvas.captureStream(FRAME_RATE)
			var againPublisher = OV.value.initPublisher('ConferenceVideo',
				{
					audioSource: true,
					videoSource: stream.getVideoTracks()[0],
					resolution: '1280x720',
				})
				againPublisher.once('accessAllowed', () => {
					session.value.unpublish(publisher.value).then(() => {
					// mainStreamManager.value = againPublisher
					publisher.value = againPublisher
				}).then(() => {
					session.value.publish(publisher.value).then(() => {})
				})
			})
		})
	}

	const closeShareDrawing = () => {
	
	session.value.unpublish(publisher.value).then(() => {
		publisher.value = secondPublisher.value
		mainStreamManager.value = secondPublisher.value
	}).then(()=>{
		session.value.publish(publisher.value)
		isDrawing.value = !isDrawing.value
	})
}
	
	

	return {
		OV,
		session,
		account,
		mainStreamManager,
		publisher,
		subscribers,
		mySessionId,
		myUserName,
		conferenceAction,
		users,
		secondPublisher,
		openviduSessionId,
		// 채팅 변수
		msgs,
		chatting,
		fromId,
		myId,
		// footer 함수 작동 변수
		audioStatus,
		videoStatus,
		userListStatus,
		chattingStatus,
		micStatus,
		isSharing,
		isDrawing,
		// 기본 함수
		dumpMethod,
		joinSession,
		leaveSession,
		updateMainVideoStreamManager,
		getToken,
		startShareScreen,
		// httpPostRequest,
		getConnections,
		createSession,
		createToken,
		// 채팅 함수
		sendMsg,
		// footer 함수
		audioOnOff,
		videoOnOff,
		userListOnOff,
		chattingOnOff,
		micOnOff,
		shareDrawing,
		startShareDrawing,
		closeShareDrawing,
	}
},

}
</script>

<style scoped>
main {
	height: auto;
}
#conference-main{
  display: flex;
  margin: 0;
  width: 100%;
	justify-content: space-between;

}

footer {
  background-color: rgb(21, 29, 42);

	position: fixed;

	bottom: 0px;
	width: 100%;
}

.chat-box {
  height: fit-content;
}

.users-box {
	height: fit-content;
}

#session {
	height: 100%;
}

.dump{
	display: flex;
	justify-content:flex-start;
	background-color: 0D131E;
  width: auto;
  height: auto;
  overflow: auto;
}

.conferenve-container {
	bottom:50%;
	right:50%;

	justify-content: center;
	align-items: center;
}
</style>