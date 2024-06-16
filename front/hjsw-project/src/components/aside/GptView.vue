<template>
  <div class="chat-container">
    <div class="bot">
      <p>&emsp;ハ____ハ ｡ﾟﾟ･｡･ﾟﾟ｡</p>
      <p>꒰&emsp;⬩ ω ⬩ ꒱ ˚｡ ｡˚</p>
      <p>&emsp;| つ ~ okay ･｡･</p>
    </div>
    <select v-model="req1">
      <option value="" disabled selected>운동 부위 선택</option>
      <option value="상체">상체</option>
      <option value="하체">하체</option>
      <option value="코어">코어</option>
    </select>
    <select v-model="req2">
      <option value="" disabled selected>운동 목적 선택</option>
      <option value="체중 증량">체중 증량</option>
      <option value="체중 감량">체중 감량</option>
    </select>
    <select v-model="req3">
      <option value="" disabled selected>운동 시간 선택</option>
      <option value="30분">30분</option>
      <option value="1시간">1시간</option>
      <option value="1시간 이상">1시간 이상</option>
    </select>
    <button @click="chat">질문하기</button>
    <div class="response">{{ store.response }}</div>
  </div>
</template>
<script setup>
import { ref } from "vue";
import { useGptStore } from "@/stores/gpt";

const req1 = ref("");
const req2 = ref("");
const req3 = ref("");
const store = useGptStore();

const chat = function () {
  console.log(req1.value);
  store.sendMessage(req1, req2, req3);
};
</script>

<style scoped>
.chat-container {
  max-width: 600px;
  margin: 50px auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  text-align: center;
}

h1 {
  margin-bottom: 20px;
}

select,
button {
  margin: 10px 0;
  padding: 10px;
  width: 90%;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 12px;
}

select:focus,
button:focus {
  outline: none;
}

.response {
  margin-top: 20px;
}

.bot {
  font-size: 10px;
}

.bot:hover {
  color: red;
}
</style>
