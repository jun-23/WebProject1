<template>
  <div class="container">
    <h4>게시글 작성</h4>
    <div class="input-container">
      <input type="text" id="title" placeholder="제목" v-model="post.title" />
      <label for="title" class="floating-label">Title</label>
    </div>
    <div class="input-container">
      <textarea
        id="content"
        placeholder="내용"
        style="height: 200px"
        v-model="post.content"
        :style="{ fontSize: fontSize + 'px', fontFamily: fontFamily }"
      ></textarea>
      <label for="content" class="floating-label">Content</label>
    </div>

    <div class="controls">
      <label for="font-size">Font Size</label>
      <input type="range" id="font-size" min="12" max="30" v-model="fontSize" />
      <label for="font-family">Font Family</label>
      <select id="font-family" v-model="fontFamily">
        <option value="Arial">Arial</option>
        <option value="Georgia">Georgia</option>
        <option value="Times New Roman">Times New Roman</option>
        <option value="Verdana">Verdana</option>
      </select>
    </div>
    <div>
      <button @click="createPostB">등록</button>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from "vue";
import { useComuStore } from "@/stores/comu";
import { useLoginStore } from "@/stores/login";

const Comustore = useComuStore();
const loginStore = useLoginStore();

const post = ref({
  title: "",
  content: "",
  image: "",
});
const nowEmail = ref("");

const fontSize = ref(16);
const fontFamily = ref("Arial");

onMounted(() => {
  loginStore.getOnlyUserInfo(); // LoginCookie를 가져오게 됨
});

onMounted(async () => {
  await loginStore.getOnlyUserInfo(); // 비동기 메서드가 완료될 때까지 기다림
  // console.log("Login state:", loginStore.OnlyUserInfo);
  nowEmail.value = loginStore.OnlyUserInfo.email;
});

const createPostB = async function () {
  console.log(post.value);
  Comustore.createPost(loginStore.OnlyUserInfo.email, post.value);
};
</script>

<style scoped>
.container {
  max-width: 600px;
  margin: 50px auto;
  padding: 20px;
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  font-family: "Arial", sans-serif;
}

h4 {
  text-align: center;
  color: #333;
}

.input-container {
  position: relative;
  margin-bottom: 20px;
}

input,
textarea {
  width: 96%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  background-color: #fff;
  transition: all 0.3s;
}

input:focus,
textarea:focus {
  border-color: #333;
  box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);
}

label {
  display: block;
  margin-bottom: 5px;
  color: #666;
}

button {
  display: block;
  width: 100%;
  padding: 10px;
  border: none;
  border-radius: 5px;
  background-color: #333;
  color: #fff;
  font-size: 16px;
  cursor: pointer;
  transition: background-color 0.3s;
}

button:hover {
  background-color: #555;
}

.floating-label {
  position: absolute;
  top: -20px;
  left: 10px;
  font-size: 14px;
  color: #333;
  pointer-events: none;
  transition: all 0.3s;
}

input:focus + .floating-label,
textarea:focus + .floating-label,
input:not(:placeholder-shown) + .floating-label,
textarea:not(:placeholder-shown) + .floating-label {
  top: -20px;
  left: 10px;
  font-size: 14px;
  color: #333;
}

.controls {
  margin-bottom: 20px;
}

.controls label {
  display: block;
  margin-bottom: 5px;
  color: #666;
}

.controls input[type="range"] {
  width: 96%;
  margin-bottom: 10px;
}

.controls select {
  width: 98%;
  padding: 5px;
  border: 1px solid #ccc;
  border-radius: 5px;
  background-color: #fff;
}
</style>
