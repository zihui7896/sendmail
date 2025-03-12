<template>
  <div class="email-container">
    <h2>发送邮件</h2>
    <div class="form-group">
      <label for="receiver">接收邮箱：</label>
      <input
        type="email"
        id="receiver"
        v-model="receiver"
        placeholder="请输入接收邮箱"
        class="input-field"
      />
    </div>
    <div class="form-group">
      <label for="subject">邮件标题：</label>
      <input
        type="text"
        id="subject"
        v-model="subject"
        placeholder="请输入邮件标题"
        class="input-field"
      />
    </div>
    <div class="form-group">
      <label for="content">邮件内容：</label>
      <textarea
        id="content"
        v-model="content"
        placeholder="请输入邮件内容"
        class="input-field textarea-field"
      ></textarea>
    </div>
    <button @click="sendEmail" class="send-button">发送邮件</button>
    <div v-if="message" :class="['message', { error: isError }]">
      {{ message }}
    </div>
  </div>
</template>

<script>
import { ref } from "vue";

export default {
  name: "SendEmail",
  setup() {
    const receiver = ref(""); // 接收邮箱
    const subject = ref(""); // 邮件标题
    const content = ref(""); // 邮件内容
    const message = ref(""); // 显示的消息
    const isError = ref(false); // 是否是错误消息

    // 发送邮件的函数
    const sendEmail = async () => {
      if (!receiver.value || !subject.value || !content.value) {
        message.value = "请填写完整信息！";
        isError.value = true;
        return;
      }

      try {
        const response = await fetch("http://localhost:7090/api/send-email", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            receiver: receiver.value,
            subject: subject.value,
            content: content.value,
          }),
        });

        const result = await response.json();
        if (result.success) {
          message.value = "邮件发送成功！";
          isError.value = false;
        } else {
          message.value = "邮件发送失败，请重试。";
          isError.value = true;
        }
      } catch (error) {
        message.value = "网络错误，请重试。";
        isError.value = true;
      }
    };

    return {
      receiver,
      subject,
      content,
      message,
      isError,
      sendEmail,
    };
  },
};
</script>

<style scoped>
.email-container {
  max-width: 500px;
  margin: 50px auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 10px;
  background-color: #f9f9f9;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

h2 {
  text-align: center;
  color: #333;
}

.form-group {
  margin-bottom: 15px;
}

label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
  color: #555;
}

.input-field {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 16px;
}

.textarea-field {
  height: 100px;
  resize: vertical;
}

.send-button {
  width: 100%;
  padding: 10px;
  background-color: #28a745;
  color: white;
  border: none;
  border-radius: 5px;
  font-size: 16px;
  cursor: pointer;
}

.send-button:hover {
  background-color: #218838;
}

.message {
  margin-top: 10px;
  text-align: center;
  font-weight: bold;
}

.message.error {
  color: red;
}
</style>