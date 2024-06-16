<template>
  <div class="shop-container">
    <h2>상점</h2>
    
    <h4>상품 목록</h4>
    <hr />
    <p>현재 상품의 수: {{ store.shopList.length }}</p>
    <hr />
    <table class="shopTable">
      <thead>
        <tr>
          <th>id</th>
          <th>이름</th>
          <th>상품 이미지</th>
          <th>가격</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="product in paginatedPosts" :key="product.id" @click="goDeatil(product.productId)">
          <td>{{product.productId}}</td>        
          <td>{{ product.item_name}}</td>
          <td><img :src="product.img" class="img-box"></td>
          <td>{{ Number(product.cost).toLocaleString() }} 원</td>       
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

import { useShopStore } from '@/stores/shop';
import { ref, onMounted, computed} from 'vue';
import { useRoute, useRouter } from "vue-router";

const store = useShopStore();
const router = useRouter();
const route = useRoute();

const currentPage = ref(1);
const shopPerPage = 10;

onMounted(() =>{
  store.getShopList();

})

const goDeatil = function(id){
  router.push(`/shop/${id}`);
}

// 전체 게시글과 현재 페이지에 따른 게시글 목록 계산
const paginatedPosts = computed(() => {
const startIndex = (currentPage.value - 1) * shopPerPage;
const endIndex = startIndex + shopPerPage;
return store.shopList.slice(startIndex, endIndex);
});

// 전체 페이지 수 계산
const totalPages = computed(() =>
Math.ceil(store.shopList.length / shopPerPage)
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



</script>

<style scoped>
.shop-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.shopTable {
  width: 100%;
  border-collapse: collapse;
}

.shopTable th,
.shopTable td {
  text-align: center; /* 셀 텍스트 가운데 정렬 설정 */
}

.shopTable th {
  background-color: #f2f2f2; /* 헤더 배경색 설정 */
}
.shopTable tbody tr {
  transition: background-color 0.3s ease; /* 배경색 변화를 부드럽게 적용 */
}
.shopTable tbody tr:hover {
  background-color: skyblue; /* 마우스 오버 시 행 배경색 설정 */
  cursor: pointer; /* 마우스 오버 시 커서 모양 변경 */
}

.shopTable td img {
max-width: 100px;
min-width: 100px;
max-height: 100px;
}

.img-box{
  max-width: 100%;
}

.img-box:hover{
      content : url("https://talkimg.imbc.com/TVianUpload/tvian/TViews/image/2023/10/13/a0def03f-631c-4cfb-b1f7-ac725f8e5ef7.jpg");
      
}

.detailLink {
display: block;
text-decoration: none;
color: #333;
}

.detailLink:hover {
color: red;
transition-duration: 10s;
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
/* 페이지 버튼 */
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
/* Content Style */
/* ------------- */

.btn-section {
display: flex;
}

.btn-section button {
position: relative;
width: 50px;
height: 50px;
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