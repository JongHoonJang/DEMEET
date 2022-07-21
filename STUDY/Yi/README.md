1. 호스트만 회의내용을 녹화할 수 있는가? 녹음만 가능할 수는 없는가?, 녹화한다면 서버에 저장하지 않고 호스트가 바로 저장할 수는 없는가?

```html
## 화면만 녹화 - https://medium.com/watcha/%EC%9B%B9%EB%B8%8C%EB%9D%BC%EC%9A%B0%EC%A0%80%EC%97%90%EC%84%9C-%EB%8F%99%EC%9E%91%ED%95%98%EB%8A%94-%EB%85%B9%ED%99%94%EC%95%B1-%EB%A7%8C%EB%93%A4%EA%B8%B0-70142ce28994
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Document</title>
  <!-- <script src="app.js" defer></script> -->
</head>
<body>
  <video id="recorded-video" controls></video>
  <video id="video-output"></video> 
  <button id="start-btn">녹화 시작</button>
  <button id="finish-btn">녹화 종료</button>
  <button id="download-btn">다운로드</button>
  <script>

    const videoOutput = document.getElementById('video-output');
    const startBtn = document.getElementById('start-btn');
    const downloadBtn = document.getElementById('download-btn');
    const finishBtn = document.getElementById('finish-btn');
    const recordedVideo = document.getElementById('recorded-video');

    let mediaStream = null;
    let mediaRecorder = null;
    let recordedMediaURL = null;

    // 유저의 카메라로 부터 입력을 사용할 수 있도록 요청
    navigator.mediaDevices
    .getUserMedia({ video: true })
    .then(function (newMediaStream) {
      mediaStream = newMediaStream;

      // 카메라의 입력을 실시간으로 비디오 태그에서 확인
      videoOutput.srcObject = mediaStream;
      videoOutput.onloadedmetadata = function (e) {
        videoOutput.play();
      };
    });

    // 녹화 시작 버튼 클릭 시 빌생하는 이벤트 핸들러 등록
    startBtn.addEventListener('click', function () {
      let recordedChunks = [];
      // 1.MediaStream을 매개변수로 MediaRecorder 생성자를 호출
      mediaRecorder = new MediaRecorder(mediaStream, {
            mimeType: 'video/webm;',
          });

          // 2. 전달받는 데이터를 처리하는 이벤트 핸들러 등록
          mediaRecorder.ondataavailable = function (event) {
            if (event.data && event.data.size > 0) {
              console.log('ondataavailable');
              recordedChunks.push(event.data);
            }
          };

          // 3. 녹화 중지 이벤트 핸들러 등록
          mediaRecorder.onstop = function () {
            // createObjectURL로 생성한 url을 사용하지 않으면 revokeObjectURL 함수로 지워줘야합니다.
            // 그렇지 않으면 메모리 누수 문제가 발생합니다.
            if (recordedMediaURL) {
              URL.revokeObjectURL(recordedMediaURL);
            }

            const blob = new Blob(recordedChunks, { type: 'video/webm;' });
            recordedMediaURL = URL.createObjectURL(blob);
            recordedVideo.src = recordedMediaURL;
          };

          mediaRecorder.start();
        });

        // 녹화 종료 버튼 클릭 시 빌생하는 이벤트 핸들러 등록
        finishBtn.addEventListener('click', function () {
          if (mediaRecorder) {
            // 5. 녹화 중지
            mediaRecorder.stop();
          }
        });

        // 다운로드 버튼 클릭 시 발생하는 이벤트 핸들러 등록
        downloadBtn.addEventListener('click', function () {
          console.log('recordedMediaURL : ', recordedMediaURL);
          if (recordedMediaURL) {
            const link = document.createElement('a');
            document.body.appendChild(link);
            link.href = recordedMediaURL;
            link.download = 'video.webm';
            link.click();
            document.body.removeChild(link);
          }
        });
      </script>
  </body>
</html>
```

```html
## 소리만 녹음 - https://curryyou.tistory.com/446
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>마이크 녹음</title>
</head>
<body>
    <button>시작/종료</button>
    <br><br>
    <audio controls>녹음된 소리를 재생할 audio 엘리먼트</audio>

</body>
<script>

    // 엘리먼트 취득
    const $audioEl = document.querySelector("audio");
    const $btn = document.querySelector("button");

    // 녹음중 상태 변수
    let isRecording = false;

    // MediaRecorder 변수 생성
    let mediaRecorder = null;

    // 녹음 데이터 저장 배열
    const audioArray = [];

    $btn.onclick = async function (event) {
        if(!isRecording){

            // 마이크 mediaStream 생성: Promise를 반환하므로 async/await 사용
            const mediaStream = await navigator.mediaDevices.getUserMedia({audio: true});

            // MediaRecorder 생성
            mediaRecorder = new MediaRecorder(mediaStream);

            // 이벤트핸들러: 녹음 데이터 취득 처리
            mediaRecorder.ondataavailable = (event)=>{
                audioArray.push(event.data); // 오디오 데이터가 취득될 때마다 배열에 담아둔다.
            }

            // 이벤트핸들러: 녹음 종료 처리 & 재생하기
            mediaRecorder.onstop = (event)=>{

                // 녹음이 종료되면, 배열에 담긴 오디오 데이터(Blob)들을 합친다: 코덱도 설정해준다.
                const blob = new Blob(audioArray, {"type": "audio/ogg codecs=opus"});
                audioArray.splice(0); // 기존 오디오 데이터들은 모두 비워 초기화한다.

                // Blob 데이터에 접근할 수 있는 주소를 생성한다.
                const blobURL = window.URL.createObjectURL(blob);

                // audio엘리먼트로 재생한다.
                $audioEl.src = blobURL;
                $audioEl.play();

            }

            // 녹음 시작
            mediaRecorder.start();
            isRecording = true;

        }else{
            // 녹음 종료
            mediaRecorder.stop();
            isRecording = false;
        }
    }


</script>

</html>
```

1. ~~회의 대화와 채팅을 text 문서로 받을 수 없는가?~~
2. ~~투표 기능?~~

<hr/>

0714

코드 컨벤션 구상

<hr/>

0715

컴포넌트 구조 구상

피그마 강의 시청

-----------------

0718

피그마를 통한 목업 만들기

--------------------

0719

목업 마무리 작업

스토리보드 작성

역할 분담(이정건 - 배포 및 FE의 Acoounts 부문)

---------------------------------------

0720

api 문서 설계 보완

명세서 익히기

------------------------------------------

0721

git 새로 clone

docker 설치 및 설정