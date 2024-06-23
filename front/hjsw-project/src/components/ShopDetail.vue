<template>
  <div class="product-details-container">
    <div class="category-selection">
      <div class="category-buttons">
        <button v-for="category in categories" 
                :key="category" 
                @click="goToCategory(category)"
                :class="{ active: selectedCategory === category }">
          {{ category }}
        </button>
      </div>
    </div>

    <div class="product-details">
      <div class="product-image">
        <img :src="store.product.img" alt="">
      </div>
      <div class="product-info">
        <h4>상품명: {{ store.product.item_name }}</h4>
        <p>판매가: {{ Number(store.product.cost).toLocaleString() }} 원</p>
        <p>수량: {{ count }} 개</p>
        <div class="button-container">
          <button @click="countUp">
            <span class="button_top">Up</span>
          </button>
          <button @click="countDown">
            <span class="button_top">Down</span>
          </button>
        </div>
        <p>Total: {{ Number(store.product.cost * count).toLocaleString() }} 원</p>
        <div class="button-container">
          <a v-if="!userExist" class="button" @click="login">구매</a>
          <a v-if="userExist" class="button" :href="buyUrl">구매</a>
          <button class="button" @click="addToCart">장바구니</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useShopStore } from "@/stores/shop";
import { ref, onMounted, computed } from "vue";
import { useRoute, useRouter } from 'vue-router';
import { useLoginStore } from "@/stores/login";

const route = useRoute();
const router = useRouter();

const product_id = ref(route.params.id);

const store = useShopStore();
const loginStore = useLoginStore();
const userExist = computed(() => loginStore.JustUserInfo && loginStore.JustUserInfo.email);

const count = ref(1);
const countUp = () => {
  count.value++;
};

const countDown = () => {
  if (count.value > 1) count.value--;
};

onMounted(() => {
  store.getShopDetail(product_id.value);
});

onMounted(async () => {
  await loginStore.getJustUserInfo();
});

const login = function() {
  window.alert("로그인이 필요한 서비스입니다.");
  router.push("/login");
};

const buyUrl = computed(() => {
  if (count.value > 0) {
    return `http://localhost:8080/api/kakaopay/buy?partner_user_id=${encodeURIComponent(loginStore.JustUserInfo.email)}&item_name=${encodeURIComponent(store.product.item_name)}&quantity=${count.value}&total_amount=${store.product.cost}&tier=0`;
  }
  return '#';
});

const addToCart = () => { //장바구니 기능
  window.alert("장바구니에 추가되었습니다.");
};

</script>

<style scoped>
.product-details-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.category-selection {
  margin-bottom: 20px;
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

.product-details {
  display: flex;
  gap: 20px;
}

.product-image {
  flex: 2;
  padding-right: 20px;
}

.product-image img {
  max-width: 100%;
  height: auto;
  border-radius: 5px;
}

.product-info {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.product-info h4 {
  margin: 10px 0;
  font-size: 18px;
  color: #333;
}

.product-info p {
  margin: 10px 0;
}

.button-container {
  display: flex;
  gap: 10px;
  margin-top: 10px;
}

button {
  --button_radius: 0.75em;
  --button_color: #e8e8e8;
  --button_outline_color: #000000;
  font-size: 17px;
  font-weight: bold;
  border: none;
  border-radius: var(--button_radius);
  background: var(--button_outline_color);
}

.button_top {
  display: block;
  box-sizing: border-box;
  border: 2px solid var(--button_outline_color);
  border-radius: var(--button_radius);
  padding: 0.75em 1.5em;
  background: var(--button_color);
  color: var(--button_outline_color);
  transform: translateY(-0.2em);
  transition: transform 0.1s ease;
}

button:hover .button_top {
  transform: translateY(-0.33em);
}

button:active .button_top {
  transform: translateY(0);
}

.button {
  font-size: 16px;
  font-weight: bold;
  padding: 10px 20px;
  background-color: #007bff;
  color: white;
  text-decoration: none;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.button:hover {
  background-color: #0056b3;
}

.button_top {
  display: block;
  box-sizing: border-box;
  border: 2px solid #007bff;
  border-radius: 4px;
  padding: 10px 20px;
  background-color: #e8e8e8;
  color: #007bff;
  text-align: center;
  transition: transform 0.1s ease;
}

.button:hover .button_top {
  transform: translateY(-2px);
}
</style>
