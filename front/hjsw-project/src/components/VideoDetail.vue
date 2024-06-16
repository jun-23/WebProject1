<template>
  <div class="video-detail">
    <div class="video-container">
      <h2 class="video-title">{{ store.video.title }}</h2>
      <!-- 유튜브 영상 임베드 코드 -->
      <div class="video-player">
        <iframe
          width="100%"
          height="500px"
          src="https://www.youtube.com/embed/es2HgZBFvIM?si=YzKOI2sY-aRYaMaj"
          frameborder="0"
          allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
          allowfullscreen
        ></iframe>
      </div>
    </div>
    <div class="video-info">
      <p><strong>제목:</strong> {{ store.video.title }}</p>
      <p><strong>업로드 날짜:</strong> {{ formatDate(store.video.regDate) }}</p>
    </div>
  </div>
</template>

<script setup>
import { useVideoStore } from "@/stores/video";
import { onMounted, ref } from "vue";
import { useRoute } from "vue-router";

const route = useRoute();
const store = useVideoStore();

const embedUrl = ref("");

onMounted(() => {
  store.getVideo(route.params.id);
  // embedUrl 설정
  embedUrl.value = `https://www.youtube.com/embed/${store.video.id}?autoplay=1`;
});

// 업로드 날짜 형식 변환 함수
function formatDate(dateString) {
  const date = new Date(dateString);
  return `${date.getFullYear()}년 ${date.getMonth() + 1}월 ${date.getDate()}일`;
}
</script>

<style scoped>
.video-detail {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.video-container {
  width: 100%;
  max-width: 800px;
}

.video-title {
  text-align: center;
}

.video-player {
  margin-bottom: 20px;
}

.video-info {
  max-width: 800px;
  background-color: #f9f9f9;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.video-info h3 {
  margin-bottom: 10px;
}

.video-info p {
  margin-bottom: 5px;
}
</style>
