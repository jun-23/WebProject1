<template>
  <div class="shop-container">
    <h2>상점</h2>

    <!-- 카테고리 선택 -->

    <div class="category-buttons">
      <button v-for="category in categories" 
              :key="category" 
              @click="selectCategory(category)"
              :class="{ active: selectedCategory === category }">
        {{ category }}
      </button>
    </div>

    <h4>상품 목록</h4>
    <hr />
    <p>현재 상품의 수: {{ store.shopList.length }}</p>
    <hr />
    <div class="card-container">
      <div class="card" v-for="product in store.shopList" :key="product.id" @click="goDetail(product.productId)">
        <img :src="product.img" class="card-img" />
        <div class="card-content">
          <h5>{{ product.item_name }}</h5>
          <p>가격: {{ Number(product.cost).toLocaleString() }} 원</p>
        </div>
      </div>
    </div>

    <!-- 페이지네이션 -->
    <div class="pagination">
      <section class="btn-section">
        <button class="btn-cp" @click="prevPage" :disabled="currentPage === 1">
          <i class="fab fa-lg first-logo fa-codepen"><font-awesome-icon icon="arrow-left" /></i>
          <i class="fab fa-lg second-logo fa-codepen"><font-awesome-icon icon="arrow-left" /></i>
        </button>
        <span v-for="page in totalPages" :key="page">
          <button class="btn-cp" @click="gotoPage(page)" :class="{ active: currentPage === page }">
            <i class="fab fa-lg first-logo fa-codepen">{{ page }}</i>
            <i class="fab fa-lg second-logo fa-codepen">{{ page }}</i>
          </button>
        </span>
        <button class="btn-cp" @click="nextPage" :disabled="currentPage === totalPages">
          <i class="fab fa-lg first-logo fa-codepen"><font-awesome-icon icon="arrow-right" /></i>
          <i class="fab fa-lg second-logo fa-codepen"><font-awesome-icon icon="arrow-right" /></i>
        </button>
      </section>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted,computed,onUpdated } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useShopStore } from '@/stores/shop';
import { library } from "@fortawesome/fontawesome-svg-core";
import { fas } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";


library.add(fas);

const store = useShopStore();
const router = useRouter();
const route = useRoute();

const currentPage = ref(1);
const shopPerPage = 10;
const selectedCategory = ref('all');
const categories = ref(['all', '컵', '디지털', '패션', '데스크', '인형', '키링']);

// 페이지 로드 시 카테고리에 따라 상품 목록 가져오기
onMounted(() => {
  console.log('통과');
  selectedCategory.value = route.params.category || 'all'; // route.params.category가 없을 경우 'all'로 설정

  if (selectedCategory.value === 'all') {
    console.log(selectedCategory.value);
    store.getShopList();
  } else {
    store.getShopListByCategory(selectedCategory.value);
  }
});

onUpdated(() => {

})


// 카테고리 변경 시 상품 목록 필터링
const selectCategory = (category) => {
  selectedCategory.value = category;
  currentPage.value = 1;
  if (selectedCategory.value === 'all') {
    console.log(selectedCategory.value);
    store.getShopList();
  } else {
    store.getShopListByCategory(selectedCategory.value);
  }
  router.push(`/shop/${category}`);


  // computed 속성을 이용하여 filteredProducts를 다시 계산하도록 함
};

// 상세 페이지 이동
const goDetail = (id) => {
  router.push(`/shop/detail/${id}`);

};

// 필터된 상품 목록
const filteredProducts = computed(() => {
  if (selectedCategory.value === 'all') {
    return store.shopList;
  }
  return store.shopList.filter(product => product.category === selectedCategory.value);
});

// 현재 페이지의 상품 목록 계산
const paginatedPosts = computed(() => {
  const startIndex = (currentPage.value - 1) * shopPerPage;
  const endIndex = startIndex + shopPerPage;
  return filteredProducts.value.slice(startIndex, endIndex);
});

// 전체 페이지 수 계산
const totalPages = computed(() => Math.ceil(filteredProducts.value.length / shopPerPage));

// 다음 페이지 이동
const nextPage = () => {
  if (currentPage.value < totalPages.value) {
    currentPage.value++;
  }
};

// 이전 페이지 이동
const prevPage = () => {
  if (currentPage.value > 1) {
    currentPage.value--;
  }
};

// 특정 페이지로 이동
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

.category-buttons {
  margin-bottom: 20px;
}

.category-buttons button {
  margin-right: 10px;
  padding: 10px 20px;
  border: none;
  background-color: #f2f2f2;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.category-buttons button.active {
  background-color: #4caf50;
  color: white;
}

.category-buttons button:hover {
  background-color: #007bff;
  color: white;
}

.card-container {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
}

.card {
  border: 1px solid #f2f2f2;
  border-radius: 10px;
  padding: 20px;
  width: calc(25% - 20px);
  box-sizing: border-box;
  cursor: pointer;
  transition: background-color 0.3s ease, transform 0.3s ease;
}

.card:hover {
  background-color: #f2f2f2;
  transform: translateY(-5px);
}

.card-img {
  width: 100%;
  height: auto;
  margin-bottom: 10px;
}

.card-content {
  text-align: center;
}

.card-content h5 {
  margin-bottom: 10px;
  font-size: 18px;
}

.card-content p {
  margin: 5px 0;
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
