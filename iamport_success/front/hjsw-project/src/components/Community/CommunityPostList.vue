<template>
  <div class="container">
    <h4>커뮤니티</h4>

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
        <!-- 20개씩 게시글 표시 -->
        <tr v-for="post in paginatedPosts" :key="post.postId">
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

    <!-- 페이지네이션 -->
    <div class="pagination">
      <!-- 하단 버튼 구현 -->
      <section class="btn-section">
        <!-- 왼쪽으로 넘기기 -->
        <button class="btn-cp" @click="prevPage" :disabled="currentPage === 1">
          <i class="fab fa-lg first-logo fa-codepen"
            ><font-awesome-icon icon="arrow-left"
          /></i>
          <i class="fab fa-lg second-logo fa-codepen"
            ><font-awesome-icon icon="arrow-left"
          /></i>
        </button>
        <!-- 각 페이지 넘버링 표시 -->
        <span v-for="page in totalPages" :key="page">
          <button
            class="btn-cp"
            @click="gotoPage(page)"
            :class="{ active: currentPage === page }"
          >
            <i class="fab fa-lg first-logo fa-codepen">{{ page }}</i>
            <i class="fab fa-lg second-logo fa-codepen">{{ page }}</i>
          </button>
        </span>
        <!-- 다음 페이지로 넘어가기-->
        <button
          class="btn-cp"
          @click="nextPage"
          :disabled="currentPage === totalPages"
        >
          <i class="fab fa-lg first-logo fa-codepen"
            ><font-awesome-icon icon="arrow-right"
          /></i>
          <i class="fab fa-lg second-logo fa-codepen"
            ><font-awesome-icon icon="arrow-right"
          /></i>
        </button>
        <!-- 글쓰기 -->
        <button class="btn-cp" @click="moveCreatePost">
          <i class="fab fa-lg first-logo fa-codepen"
            ><font-awesome-icon icon="pencil"
          /></i>
          <i class="fab fa-lg second-logo fa-codepen"
            ><font-awesome-icon icon="pencil"
          /></i>
        </button>
      </section>
    </div>
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
import { ref, onMounted, computed } from "vue";
import { useRoute, useRouter } from "vue-router";

const store = useComuStore();
const router = useRouter();
const route = useRoute();

// 현재 페이지와 페이지당 게시글 수 설정
const currentPage = ref(1);
const postsPerPage = 20;

// 전체 게시글과 현재 페이지에 따른 게시글 목록 계산
const paginatedPosts = computed(() => {
  const startIndex = (currentPage.value - 1) * postsPerPage;
  const endIndex = startIndex + postsPerPage;
  return store.postList.slice(startIndex, endIndex);
});

// 전체 페이지 수 계산
const totalPages = computed(() =>
  Math.ceil(store.postList.length / postsPerPage)
);

// 다음 페이지로 이동하는 함수
const nextPage = () => {
  if (currentPage.value < totalPages.value) {
    currentPage.value++;
  }
};

// 이전 페이지로 이동하는 함수
const prevPage = () => {
  if (currentPage.value > 1) {
    currentPage.value--;
  }
};

// 특정 페이지로 이동하는 함수
const gotoPage = (page) => {
  currentPage.value = page;
};

// 게시글 생성 페이지로 이동하는 함수
const moveCreatePost = () => {
  router.push({ name: "postCreate" });
};

// 페이지가 변경될 때마다 게시글 목록 갱신
onMounted(() => {
  store.getPostList();
  currentPage.value = parseInt(route.query.page) || 1;
});
</script>

<style>
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
