<template>
  <div class="container">
    <h4>게시글 수정</h4>
    <div class="input-container">
      <input
        type="text"
        id="title"
        placeholder="제목"
        v-model="store.post.title"
      />
      <label for="title" class="floating-label">Title</label>
    </div>
    <div class="input-container">
      <textarea
        id="content"
        placeholder="내용"
        style="height: 200px"
        v-model="store.post.content"
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
      <button @click="updatePost">수정</button>
      <button @click="backButton">뒤로</button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useComuStore } from "@/stores/comu";
import { useLoginStore } from "@/stores/login";
import { useRoute, useRouter } from "vue-router";

const store = useComuStore();
const loginStore = useLoginStore();
const router = useRouter();
const route = useRoute();

const fontSize = ref(16);
const fontFamily = ref("Arial");

const updatePost = function () {
  const id = route.params.id;
  const email = loginStore.JustUserInfo.email; // 적절한 이메일 값으로 변경
  const postDetails = store.post; // store.post에 업데이트할 게시글 정보가 있다고 가정
  console.log(postDetails);
  store.updatePost(id, email, postDetails);
};

const backButton = function () {
  router.go(-1); // 돌아간다.
  // router.back();
};

onMounted(async () => {
  try {
    await Promise.all([
      store.getPost(PresentId.value),
      loginStore.getJustUserInfo(),
    ]);
  } catch (error) {
    console.error("Error loading data:", error);
  }
});
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
  margin-top: 10px;
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
