<template>
  <div>
    <input
      class="input-text"
      type="text"
      placeholder="채팅을 입력하세요."
      v-model = "messageForm.message"
      v-on:keyup.enter="submitForm"
    >
    <button
      class="my-btn"
      type="button"
      v-on:click="submitForm"
      >입력
    </button>

  </div>
</template>

<script>
export default {
  name: 'MessageForm',
  data() {
    return {
      messageForm: {
        message: ""
      }
    };
  },

  props: {
    userName: String,
    default: ()=> {
      return ""
    }
  },

  methods: {
    submitForm(event) {

      const msg = this.messageForm.message.trim();

      if (msg != "") {

        event.preventDefault();
        // this.$emit("sendMsg", "[" + this.userName + "] : " + msg);
        var string = JSON.stringify({
          userName: this.userName,
          msg: msg
        });
        this.$emit("sendMsg", string);
      }
      this.messageForm.message = "";
    }
  }
};
</script>

<style scoped>
.my-btn {
  width:60px;
  height: 64px;
  padding:0px;

}
.input-text {
  height: 60px;
  padding:0;
  justify-content: stretch;
  align-items: stretch;

}
#inputTextArea {
  display: flex;
  position : relative;
  height: 60px;
  justify-content: stretch;
  align-items: stretch;

}
</style>

