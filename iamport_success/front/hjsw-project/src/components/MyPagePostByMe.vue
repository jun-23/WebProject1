<template>
  <div class="container">
    <h4>내가 쓴 글</h4>

    <hr />
    <!-- 게시글 테이블 -->
    <table>
      <thead>
        <tr>
          <th>제목</th>
          <th style="width: 15%">글쓴이</th>
          <th style="width: 10%">등록일</th>
        </tr>
      </thead>
      <tbody>
        <tr
          v-for="post in store.postList.filter(
            (c) => c.user.email === loginStore.JustUserInfo.email
          )"
          :key="post.postId"
        >
          <td style="font-size: 0.9em">
            <RouterLink :to="`/community/${post.postId}`">{{
              post.title
            }}</RouterLink>
          </td>
          <td style="font-size: 0.8em">{{ post.user.email }}</td>
          <td style="font-size: 0.5em">{{ post.createdAt }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
///// for FontAwesome
import { library } from "@fortawesome/fontawesome-svg-core";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { fas } from "@fortawesome/free-solid-svg-icons";

library.add(fas);
/////
import { useComuStore } from "@/stores/comu";
import { useLoginStore } from "@/stores/login";
import { ref, onMounted, computed } from "vue";
import { useRoute, useRouter } from "vue-router";

const loginStore = useLoginStore();
const store = useComuStore();
const router = useRouter();
const route = useRoute();

// 페이지가 변경될 때마다 게시글 목록 갱신
onMounted(() => {
  store.getPostList();
});

onMounted(async () => {
  try {
    await Promise.all([loginStore.getJustUserInfo()]);
  } catch (error) {
    console.error("Error loading data:", error);
  }
});
</script>

<style scoped>
/* 여기에 전역 CSS 스타일을 추가합니다 */

/* -------------- */
/* Table and Pagination */
/* -------------- */
table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 20px;
}

th,
td {
  border-top: 1px dotted #ddd;
  padding: 8px;
}

th {
  background-color: #f2f2f2;
}

.pagination {
  margin-top: 20px;
}

.pagination button {
  background-color: #4caf50;
  color: white;
  padding: 8px 16px;
  border: none;
  cursor: pointer;
}

.pagination button.active {
  background-color: #007bff;
}

.pagination button:disabled {
  background-color: #cccccc;
  cursor: not-allowed;
}

/* ------------- */
/* Basic Setting */
/* ------------- */

*,
*::before,
*::after {
  box-sizing: inherit;
}

::selection {
  color: #fff;
  background-color: #f3feff;
}

/* Setting for Button*/
:root {
  /* Font Color */
  --light-theme: #f3feff;

  /* Font Family */
  --fonts-style-x: "neuzeit-grotesk", sans-serif;
  --fonts-style-y: "neuzeit-grotesk", sans-serif;

  /* Font Weight */
  --light: 300;
  --regular: 400;
  --bold: 700;
  --black: 900;
}

/* ------------- */
/* Content Style */
/* ------------- */

.btn-section {
  display: flex;
}

.btn-section button {
  position: relative;
  width: 15px;
  height: 15px;
  margin: 0 10px;
  border-radius: 10px;
  border: none;
  outline: none;
  overflow: hidden;
  cursor: pointer;
  transition: 400ms;

  animation: zoom 500ms ease;
}

.btn-section button i {
  position: absolute;
  font-size: 1.5em;
  transition: 400ms;
}

.btn-section .btn-cp {
  background-color: #ebfaf0bb;
  color: #1e4d94;
}

.btn-section .first-logo {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  opacity: 1;
}

.btn-section .second-logo {
  position: absolute;
  top: calc(50% + 30px);
  left: 50%;
  transform: translate(-50%, -50%);
  opacity: 0;
}

.btn-section button:hover .first-logo {
  position: absolute;
  top: -30px;
  opacity: 0;
}

.btn-section button:hover .second-logo {
  top: 50%;
  opacity: 1;
}

.btn-section button:active {
  background-color: #ffffff00;
}

@keyframes zoom {
}
</style>
