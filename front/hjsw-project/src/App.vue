<script setup>
import { RouterLink, RouterView } from "vue-router";
import { computed } from "vue";
import { useRoute } from "vue-router";
import { ref, reactive, onMounted, onBeforeUnmount } from "vue";
import TheHadderNav from "./components/hadder/TheHadderNav.vue";
import TheFooterNav from "./components/footer/TheFooterNav.vue";
import GptView from "./components/aside/GptView.vue";

const route = useRoute();

// 로그인 페이지에서는 헤더와 푸터를 숨기는 역할
const showHeader = computed(() => route.path !== "/login");

const chatWidth = ref(400); // 채팅창 너비
const chatHeight = ref(520); // 채팅창 높이

const resizeInfo = reactive({
  startX: 0,
  startY: 0,
  isResizing: false,
  isDragging: false,
  initialLeft: 0,
  initialTop: 0,
  startLeft: 0,
  startTop: 0,
});

const isChatVisible = ref(true); // 채팅창의 가시성 상태를 관리하는 변수

function startResize(event) {
  resizeInfo.startX = event.clientX;
  resizeInfo.startY = event.clientY;
  resizeInfo.isResizing = true;
}

function startDrag(event) {
  resizeInfo.startX = event.clientX;
  resizeInfo.startY = event.clientY;
  resizeInfo.startRight = parseInt(chatStyle.right, 10); // 변경
  resizeInfo.startBottom = parseInt(chatStyle.bottom, 10); // 변경
  resizeInfo.isDragging = true;
}

function resizeChat(event) {
  if (resizeInfo.isResizing) {
    const deltaX = event.clientX - resizeInfo.startX;
    const deltaY = event.clientY - resizeInfo.startY;
    chatWidth.value = Math.max(200, chatWidth.value + deltaX); // Minimum width
    chatHeight.value = Math.max(100, chatHeight.value + deltaY); // Minimum height
    resizeInfo.startX = event.clientX;
    resizeInfo.startY = event.clientY;
  }
  if (resizeInfo.isDragging) {
    const deltaX = resizeInfo.startX - event.clientX; // 변경
    const deltaY = resizeInfo.startY - event.clientY; // 변경
    chatStyle.right = `${resizeInfo.startRight + deltaX}px`; // 변경
    chatStyle.bottom = `${resizeInfo.startBottom + deltaY}px`; // 변경
  }
}

function stopResize() {
  resizeInfo.isResizing = false;
  resizeInfo.isDragging = false;
}

function closeChat() {
  isChatVisible.value = false;
}

const chatStyle = reactive({
  right: "20px", // 변경된 부분
  bottom: "20px", // 변경된 부분
});

onMounted(() => {
  window.addEventListener("mousemove", resizeChat);
  window.addEventListener("mouseup", stopResize);
});

onBeforeUnmount(() => {
  window.removeEventListener("mousemove", resizeChat);
  window.removeEventListener("mouseup", stopResize);
});
</script>

<template>
  <div>
    <div>
      <TheHadderNav v-if="showHeader"></TheHadderNav>
    </div>

    <RouterView />
    <!-- 봇 주석 -->
     <!-- <div  --> 
      <!-- v-if/="showHeader && isChatVisible" -->
      <!-- class="floating-chat" -->
      <!-- :style="{ -->
        <!-- width: chatWidth + 'px', -->
        <!-- height: chatHeight + 'px', -->
        <!-- ...chatStyle, -->
      <!-- }" -->
    <!-- > -->
      <!-- Drag handle -->
      <!-- <div class="drag-handle" @mousedown="startDrag"></div> -->

      <!-- 크기 조절 핸들 -->
      <!-- <div class="resize-handle" @mousedown="startResize"></div> -->

      <!-- 닫기 버튼 -->
      <!-- <button class="close-button" @click="closeChat">닫기</button> -->
      <!-- <GptView :width="chatWidth" :height="chatHeight"></GptView> -->
    <!-- </div> -->

    <!-- <div class="footer"> -->
      <!-- <TheFooterNav v-if="showHeader"></TheFooterNav> -->
    <!-- </div> -->
  </div>
</template>

<style scoped>
.footer {
  margin-bottom: 0%;
}
.floating-chat {
  min-width: 300px;
  min-height: 420px;
  position: fixed;
  bottom: 20px; /* 원하는 위치에 조정하세요 */
  right: 20px; /* 원하는 위치에 조정하세요 */
  z-index: 999; /* 다른 요소 위에 나타나도록 z-index 설정 */
  background-color: #fff; /* 배경색을 원하는 색상으로 설정 */
  border: 1px solid #ccc; /* 필요에 따라 테두리 설정 */
  border-radius: 8px; /* 필요에 따라 테두리 모서리 둥글게 설정 */
  padding: 10px; /* 내용 여백 설정 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* 그림자 효과 추가 */
  cursor: move; /* 드래그를 위한 커서 설정 */
}

.drag-handle {
  width: 100%;
  height: 20px;
  background-color: #f0f0f0;
  cursor: move;
  position: absolute;
  top: 0;
  left: 0;
}

.resize-handle {
  width: 10px;
  height: 10px;
  background-color: #ccc;
  position: absolute;
  top: 0;
  left: 0;
  cursor: nwse-resize;
}

.close-button {
  position: absolute;
  top: 5px;
  right: 5px;
  background-color: transparent;
  border: none;
  cursor: pointer;
}
</style>
