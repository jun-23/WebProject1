<template>
  <div class="product-details">
      <div class="product-info">
          <h4>상품명: {{ store.product.item_name}}</h4>
          <div class="product-image">
              <img :src="store.product.img" alt="">
          </div>
          <p>가격: {{ Number(store.product.cost * (count)).toLocaleString()}} 원</p>
      </div>
      
      <div class="buttons">
          <p>{{ count }} 개</p>

      </div>
      <div class="button-container">
       <button>
          <span class="button_top" @click="countUp"> Up </span>
      </button>

      <button>
        <span class="button_top" @click="countDown"> Down </span>
      </button>

      <div>
        <a v-if="!userExist" class="button" @click="login">결제</a>
        <a v-if="userExist" class="button" :href="buyUrl">결제</a>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useShopStore } from "@/stores/shop";
import { onMounted, ref, computed } from "vue";
import { useRoute, useRouter } from 'vue-router';
import { useLoginStore } from "@/stores/login";

const route = useRoute();
const router = useRouter();

const product_id = ref(route.params.id);

const store = useShopStore();
const loginStore = useLoginStore();
const userExist = computed(
  () => loginStore.JustUserInfo && loginStore.JustUserInfo.email
);

onMounted(() => {
  store.getShopDetail(product_id.value);
})

const count = ref(1);
const countUp = () => {
  count.value++;
};



const countDown = () => {
  if(count.value > 1)
      count.value--;
};

onMounted(async () => {
await loginStore.getJustUserInfo();
});



// a 링크 형태로 발사 
const login = function(){
    window.alert("로그인이 필요한 서비스입니다.") ;
    router.push("/login");
};


const buyUrl = computed(() => {
  if (count.value > 0) {
      return `http://localhost:8080/api/kakaopay/buy?partner_user_id=${encodeURIComponent(loginStore.JustUserInfo.email)}&item_name=${encodeURIComponent(store.product.item_name)}&quantity=${count.value}&total_amount=${store.product.cost}&tier=0`;
  }
  return '#';
});
</script>

<style scoped>
.product-details {
max-width: 600px;
margin: 0 auto;
padding: 20px;
border: 1px solid #ccc;
border-radius: 5px;
background-color: #f9f9f9;
}

.product-details h4 {
margin: 10px 0;
font-size: 18px;
color: #333;
}

.product-details img {
max-width: 100%;
height: auto;
margin-top: 10px;
}

.product-details p {
margin: 10px 0;
}

.product-details .buttons {
margin-top: 20px;
}

.product-details .buttons button {
padding: 10px 20px;
background-color: #000000;
color: #fff;
border: none;
border-radius: 5px;
cursor: pointer;
margin-right: 10px;
}

.product-details .buttons a {
display: inline-block;
padding: 10px 20px;
background-color: #000000;
color: #fff;
text-decoration: none;
border-radius: 5px;
transition: background-color 0.3s;
}

.product-details .buttons a:hover {
background-color: #dd968b;
}

button {
  /* Variables */
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
  /* Pull the button upwards when hovered */
  transform: translateY(-0.33em);
}

button:active .button_top {
  /* Push the button downwards when pressed */
  transform: translateY(0);
}

.button-container {
  display: flex;
  gap: 10px; /* 버튼과 링크 사이의 간격을 조정할 수 있습니다 */
  align-items: center; /* 세로 중앙 정렬 */
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