<template>
		<div id="join" v-if="!session">
			<div id="img-div"> <img class='bg-image' src="@/assets/DEMEET_logo.png" alt=""></div>
			<div id="join-dialog" class="jumbotron vertical-center">
				<h1>Join a video session</h1>
				<div class="form-group">
					<p>
						<label>Participant</label>
						<input v-model="myUserName" class="form-control" type="text" required>
					</p>
					<p>
						<label>Session</label>
						<input v-model="mySessionId" class="form-control" type="text" required>
					</p>
					<p class="text-center">
						<button class="btn btn-lg btn-success" @click="joinSession()">Join!</button>
					</p>
				</div>
			</div>
		</div>

	<div id="session" v-if="session">
  <nav>
		<div id="session-header">
				<h1 id="session-title">{{ mySessionId }}</h1>
				<input class="btn btn-large btn-danger" type="button" id="buttonLeaveSession" @click="leaveSession" value="Leave session">
		</div>
  </nav>
  <main>
    <section>
      <article>
        <!-- 영상, 드로잉 등 -->
        <ConferenceVideo
				:OV="OV"
				:session = "session"
				:mainStreamManager = "mainStreamManager"
				:publisher ="publisher"
				:subscribers = "subscribers"
				/>
      </article>
      <footer>
        <!-- 동작 버튼 등,  -->
        <ConferenceFooter/>
      </footer>
    </section>
    <aside>
      <!-- 참자가 목록, 채팅 -->
      <ConferenceUsers
      :publisher="publisher"
      :subscribers="subscribers"
      />
      <ConferenceChat/>
    </aside>
  </main>
	</div>
	
  
</template>

<script>
import ConferenceVideo from './ConferenceVideo'
import ConferenceUsers from './ConferenceUsers'
import ConferenceChat from './ConferenceChat'
import ConferenceFooter from './ConferenceFooter'

// 튜토리얼 복붙
import axios from 'axios';
import { OpenVidu } from 'openvidu-browser';
//추가
import api from "@/api/api"

axios.defaults.headers.post['Content-Type'] = 'application/json'

const OPENVIDU_SERVER_URL = "https://" + 'i7b309.p.ssafy.io' + ":4443"
const OPENVIDU_SERVER_SECRET = "wlwhseodnjs123"

export default {
components: { ConferenceVideo, ConferenceUsers, ConferenceChat, ConferenceFooter,},

data () {
  return {
    OV: undefined,
    session: undefined,
    mainStreamManager: undefined,
    publisher: undefined,
    subscribers: [],

    mySessionId: 'SessionA',
    myUserName: 'Participant' + Math.floor(Math.random() * 100),
  }
},

provide(){
	return{
		proMainStreamManager : this.mainStreamManager,
		proPublisher : this.publisher,
		proSubscribers : this.subscribers
	}
},

methods: {
		joinSession () {
			// --- Get an OpenVidu object ---
			this.OV = new OpenVidu();

			// --- Init a session ---
			this.session = this.OV.initSession();

			// --- Specify the actions when events take place in the session ---

			// On every new Stream received...
			this.session.on('streamCreated', ({ stream }) => {
				const subscriber = this.session.subscribe(stream);
				this.subscribers.push(subscriber);
			});

			// On every Stream destroyed...
			this.session.on('streamDestroyed', ({ stream }) => {
				const index = this.subscribers.indexOf(stream.streamManager, 0);
				if (index >= 0) {
					this.subscribers.splice(index, 1);
				}
			});

			// On every asynchronous exception...
			this.session.on('exception', ({ exception }) => {
				console.warn(exception);
			});

			// --- Connect to the session with a valid user token ---

			// 'getToken' method is simulating what your server-side should do.
			// 'token' parameter should be retrieved and returned by your own backend

			// this.getToken(this.mySessionId).then(token => {
			// 	this.session.connect(token, { clientData: this.myUserName })
			// 		.then(() => {

			// 			// --- Get your own camera stream with the desired properties ---

			// 			let publisher = this.OV.initPublisher(undefined, {
			// 				audioSource: undefined, // The source of audio. If undefined default microphone
			// 				videoSource: undefined, // The source of video. If undefined default webcam
			// 				publishAudio: true,  	// Whether you want to start publishing with your audio unmuted or not
			// 				publishVideo: true,  	// Whether you want to start publishing with your video enabled or not
			// 				resolution: '640x480',  // The resolution of your video
			// 				frameRate: 30,			// The frame rate of your video
			// 				insertMode: 'APPEND',	// How the video is inserted in the target element 'video-container'
			// 				mirror: false       	// Whether to mirror your local video or not
			// 			});

			// 			this.mainStreamManager = publisher;
			// 			this.publisher = publisher;

			// 			// --- Publish your stream ---

			// 			this.session.publish(this.publisher);
			// 		})
			// 		.catch(error => {
			// 			console.log('There was an error connecting to the session:', error.code, error.message);
			// 		});
			// });
			this.getToken((token) => {
				this.session.connect(token, {'clientData':this.myUserName})
				.then(() => {
					console.log("Connection Success");
					let publisher = this.OV.initPublisher(undefined, {
						audioSource: undefined, // The source of audio. If undefined default microphone
						videoSource: undefined, // The source of video. If undefined default webcam
						publishAudio: true,  	// Whether you want to start publishing with your audio unmuted or not
						publishVideo: true,  	// Whether you want to start publishing with your video enabled or not
						resolution: '640x480',  // The resolution of your video
						frameRate: 30,			// The frame rate of your video
						insertMode: 'APPEND',	// How the video is inserted in the target element 'video-container'
						mirror: false       	// Whether to mirror your local video or not
					});
					console.log("Publish Success");
					this.mainStreamManager = publisher;
					this.publisher = publisher;

					// publish your stream
					this.session.publish(this.publisher);
				})
				.catch( error => {
					console.log('There was an error connecting to the session:', error.code, error.message)
				})
			})

			window.addEventListener('beforeunload', this.leaveSession)
		},

		leaveSession () {
			// --- Leave the session by calling 'disconnect' method over the Session object ---

			if (this.session) this.session.disconnect();

			this.session = undefined;
			this.mainStreamManager = undefined;
			this.publisher = undefined;
			this.subscribers = [];
			this.OV = undefined;

			window.removeEventListener('beforeunload', this.leaveSession);
		},

		updateMainVideoStreamManager (stream) {
			if (this.mainStreamManager === stream) return;
			this.mainStreamManager = stream;
		},

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

		// getToken (mySessionId) {
		// 	return this.createSession(mySessionId).then(sessionId => this.createToken(sessionId));
		// },
		getToken (callback){
			this.httpPostRequest(
				'get-token',
				{sessionName : this.mySessionId},
				(response) => {
					let token = response; // Get token from response
					console.warn('Request of TOKEN gone WELL (TOKEN:' + token + ')');
					callback(token); // Continue the join operation
				}
			);
		},

		// httpPostRequest(url, body, errorMsg, callback) {
		// 	var http = new XMLHttpRequest();
		// 	http.open('POST', url, true);
		// 	http.setRequestHeader('Content-type', 'application/json');
		// 	http.addEventListener('readystatechange', processRequest, false);
		// 	http.send(JSON.stringify(body));

		// 	function processRequest() {
		// 		if (http.readyState == 4) {
		// 			if (http.status == 200) {
		// 				try {
		// 					callback(JSON.parse(http.responseText));
		// 				} catch (e) {
		// 					callback();Ò
		// 				}
		// 			} else {
		// 				console.warn(errorMsg);
		// 				console.warn(http.responseText);
		// 			}
		// 		}
		// 	}
		// },
		httpPostRequest(url, body, callback){
			axios({
                'url': api.conferences.conference() + url,
                'method': 'post',
                'data': JSON.stringify(body),
				// 추후 연결 필요
				headers: { Authorization: `Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0QG5hdmVyLmNvbSIsImlzcyI6InNzYWZ5LmNvbSIsImV4cCI6MTY2MDg2OTYyNCwiaWF0IjoxNjU5NTczNjI0fQ.KF0FiX4l5kPjusYFeuDCY-WsUhZchVp-WFNb9Zf1riD98E62H7xGPawfUqLMi9HTalihs0cb9-RVSqFVPW9NFQ`}
            })
            .then(res => {
				console.log(res.data[0]);
                const token = res.data[0];
                console.warn('Request of TOKEN gone WELL (TOKEN:' + token + ')');
                callback(token);
            })
            .catch(err => (
                console.error(err.response),
                alert('컨퍼런스 토큰 저장에 실패하였습니다.')
            ))
		},

		// See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-session
		createSession (sessionId) {
			return new Promise((resolve, reject) => {
				axios
					.post(`${OPENVIDU_SERVER_URL}/openvidu/api/sessions`, JSON.stringify({
						customSessionId: sessionId,
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
							resolve(sessionId);
						} else {
							console.warn(`No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}`);
							if (window.confirm(`No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${OPENVIDU_SERVER_URL}"`)) {
								location.assign(`${OPENVIDU_SERVER_URL}/accept-certificate`);
							}
							reject(error.response);
						}
					});
			});
		},

		// See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-connection
		createToken (sessionId) {
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
					.catch(error => reject(error.response));
			});
		},
	}

}
</script>

<style scoped>
main{
  display: flex;
  margin: 0;
  height: 100vh;

}

section {
  width: 80%;
  display: flex;
  flex-direction: column;
  background-color: rgb(13, 19, 30);
}

article {
  height: 90%;
}

footer {
  background-color: rgb(21, 29, 42);
  height: 10%;
}

  aside{
    display: flex;
    flex-direction: column;
    height: 100%;
    width: 20%;
    background-color: #fff;
  }

</style>