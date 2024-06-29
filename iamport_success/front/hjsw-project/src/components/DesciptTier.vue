<template>
    <div id="app">
      <div class="container">

        <div class="box">
          <h2>Bronze Tier</h2>
          <p>Basic features included</p>
          <p>Price: Free/month</p>
          <button @click="bronze">Free</button>
        </div>
        <div class="box" >
          <h2>Silver Tier</h2>
          <p>Extra features included</p>
          <p>Price: ￦20,000/month</p>
          <a v-if="loginStore.grade < 1" :href="buyShilver"><button>Subscribe</button></a>
          <button v-if="loginStore.grade >= 1" @click="purchased">Subscribed</button>
        </div>
        <div class="box" >
          <h2>Gold Tier</h2>
          <p>Premium features included</p>
          <p>Price: ￦30,000/month</p>
          <button v-if="loginStore.grade < 1"  @click="prevBuy">Not Auth</button>
          <a v-if="loginStore.grade === 1" :href="buyGold"><button>Subscribe</button></a>
          <button v-if="loginStore.grade >= 2" @click="purchased">Subscribed</button>
        </div>
        <div class="box" >
          <h2>Platinum Tier</h2>
          <p>Premium features included</p>
          <p>Price: ￦40,000/month</p>
          <button  @click="prevBuy" v-if="loginStore.grade < 2">Not Qualified</button>
          <a v-if="loginStore.grade === 2" :href="buyPlatinum"><button>Subscribe</button></a>
          <button v-if="loginStore.grade >= 3" @click="purchased">Subscribed</button>
        </div>
      </div>
      
    </div>
  </template>
  
<script setup>
import { onMounted, ref, computed } from "vue";
import { useRoute, useRouter } from 'vue-router';
import { useLoginStore } from "@/stores/login";

const route = useRoute();
const router = useRouter();

const loginStore = useLoginStore();

// 이메일 정보를 가져옴
onMounted(async () => {
  await loginStore.getJustUserInfo();
  loginStore.getGrade(loginStore.JustUserInfo.email)
}); 
onMounted(() => {});
//등급에 따라 활성화 정도 변경
// 메인으로 백
const bronze = function(){
    router.push("/")
}

const prevBuy = function(){
  window.alert("강해져서 돌아와라");
}

const purchased = function(){
  window.alert("구입 완료하신 상품입니다.")
}

// 각 티어별로 구매링크
const buyGold = computed(() => {
        return `http://localhost:8080/api/kakaopay/buy?partner_user_id=${encodeURIComponent(loginStore.JustUserInfo.email)}&item_name=${encodeURIComponent("Gold Tier")}&quantity=1&total_amount=30000&tier=2`;
});

const buyShilver = computed(() => {
        return `http://localhost:8080/api/kakaopay/buy?partner_user_id=${encodeURIComponent(loginStore.JustUserInfo.email)}&item_name=${encodeURIComponent("Shilver Tier")}&quantity=1&total_amount=20000&tier=1`;
});

const buyPlatinum = computed(() => {
        return `http://localhost:8080/api/kakaopay/buy?partner_user_id=${encodeURIComponent(loginStore.JustUserInfo.email)}&item_name=${encodeURIComponent("Platinum Tier")}&quantity=1&total_amount=40000&tier=3`;
});
</script>
  
  <style scoped>
  .container {
    display: flex;
    justify-content: space-around;
    margin-top: 50px;
  }
  .box {
    width: 200px;
    height: 400px;
    border-radius: 20px;
    background-color: #f0f0f0;
    box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
    padding: 20px;
    cursor: pointer;
    transition: transform 0.3s ease;

  }
  .box:hover {
    transform: translateY(-5px);
  }
  button {
    background-color: #007bff;
    color: #fff;
    border: none;
    padding: 10px 20px;
    border-radius: 5px;
    cursor: pointer;
    transition: background-color 0.3s ease;
  }
  button:hover {
    background-color: #0056b3;
  }
  </style>