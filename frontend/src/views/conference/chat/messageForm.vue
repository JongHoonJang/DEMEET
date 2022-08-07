<template>
  <div>
    <input
      class="input-text"
      type="text"
      placeholder="채팅을 입력하세요."
      v-model = "messageForm.message"
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
  height: 60px;
}
.input-text {
    height: 60px;

    flex: 1;
    display: flex;
    justify-content: stretch;
    align-items: stretch;
}
.inputTextArea {

  position : relative;
  transform : translateY(-100%);
  justify-content: space-between;

}
</style>
