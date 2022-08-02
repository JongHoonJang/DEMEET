<template>

  <div class="ConferenceChat">
    <!-- 나중에 삭제하기(닉네임 설정부분) -->
    <div class="user-container">
      <label for="nickname">대화명</label>
      <input type="text" id="nickname">
    </div>
    <div class="display-container">
      <ul class="chatting-list">
        <li class="sent">
        </li>

      </ul>
    </div>
    <!-- 나중에 추가할 부분 메세지 보낼 사람 선택하기 -->
    <div class="input-container">
      <span>
          <input type="text" class="chatting-input">
          <button class="send-button">전송</button>
      </span>
    </div>
  </div>

</template>

<script>

export default {
  name: 'ConferenceChat',
  components: {},
  data() {
    return {
      sampleData: ''
    };
  },
  setup() {},
  create() {},
  mounted() {},
  unmounted() {},
  methods: {},
}
</script>

<style scoped>

.ConferenceChat {
    height: 100%;
    width: 100%;
    display: flex;
    flex-direction: column;
    overflow: hidden;
}

.user-container {
    background: #a9bdce;
    flex:1;
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 0.5rem;
    
}

.user-container .user {
    font-size: 10px;
}

.user-container label {
    font-size: 14px;
    margin-right:  1rem;
}

.user-container input {
    border-radius: 3px;
    border: none;
    height: 75%

}

.display-container {
    background: #b2c7d9;
    flex: 12;
    overflow-y: scroll;
}

.input-container {
    flex: 1;
    display: flex;
    justify-content: stretch;
    align-items: stretch;
}

.input-container span{
    display: flex;
    justify-content: flex-start;    
    align-items: center;
    padding: 0.3rem;
    width: 100%;  
    
}

.chatting-input {
    font-size: 12px;
    border: none;
    height: 100%;
    flex:8
    
    
}

.send-button {
    flex: 1;
    background: #ffeb33;
    border: none;
    height: 100%;
    border-radius: 3px;
}

.chatting-list li {
    width: 90%;
    padding: 0.3rem;
    display: flex;
    justify-content: flex-start;
    align-items: flex-end;
    margin-top: 0.5rem;
}

.profile{
    display: flex;
    flex-direction: column;
    align-items: center;
    flex: 1;

}

.profile .image{
    border-radius: 50%;
    object-fit: cover;
    /* 크기에 맞게 넣게 한다 */
    width: 50px;
    height: 50px;    
}

.message {
    border-radius: 5px;
    padding: 0.5rem;
    font-size: 12px;
    margin: 0 5px;
    flex: 7;
}


.time {
    font-size: 10px;
    margin: 0  5px;

}
.sent {
    flex-direction: row-reverse;
    float: right;
}

.sent .message {
    background: #ffeb33;
}
.received .message {
    background: #fff;

}
</style>
