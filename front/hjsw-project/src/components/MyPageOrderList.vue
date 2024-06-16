<template>
  <div class="container">
    <h1>마이페이지</h1>
    <hr>
    <section>
      <h2>My Grade</h2>
      <div class="bronze" v-if="loginStore.grade===0"><img src="@/assets/tier/bronze-removebg-preview.png"><h3>Bronze</h3></div>
      <div class="silver" v-if="loginStore.grade===1"> <img src="@/assets/tier/silver-removebg-preview.png"><h3>Silver</h3></div>
      <div class="gold" v-if="loginStore.grade===2"><img src="@/assets/tier/gold-removebg-preview.png"><h3>Gold</h3></div>
      <div class="platinum" v-if="loginStore.grade===3"> <img src="@/assets/tier/platinum-removebg-preview.png"><h3>Platinum</h3></div>
    </section>
    <hr>
    <section>
      <router-link :to="`/mypage/post`">
                  <h2>내가 쓴 글</h2>
      </router-link>
    </section>
    <hr>
    <section>
      <h2>주문 목록</h2>
      <hr />
      <p>총 주문 수: {{ store.orderList.length  }}</p>
      <hr />
      <table class="orderTable">
        <thead>
          <tr>
            <th>주문번호</th>
            <th>결제상품</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="order in store.orderList.filter((c) => c.flag === 1)" :key="order.id">
            <td>
              <div class="detailLink">
                <router-link :to="`/mypage/${order.id}`">
                  {{ order.id }}번
                </router-link>
              </div>
            </td>
            <td>{{ order.item }}</td>
          </tr>
        </tbody>
      </table>
    </section>

    <hr />

    <section>
      <h2>취소 목록</h2>
      <hr />
      <table class="cancelTable">
        <thead>
          <tr>
            <th>주문번호</th>
            <th>결제상품</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="order in store.orderList.filter((c) => c.flag === 0)" :key="order.id">
            <td>
              <div class="detailLink">
                <router-link :to="`/mypage/cancel/${order.id}`">
                  {{ order.id }}번
                </router-link>
              </div>
            </td>
            <td>{{ order.item }}</td>
          </tr>
        </tbody>
      </table>
    </section>

    <section>
      <h2>티어 상품</h2>
      <hr />
      <table class="cancelTable">
        <thead>
          <tr>
            <th>주문번호</th>
            <th>결제상품</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="order in store.orderList.filter((c) => c.flag === 2)" :key="order.id">
            <td>
              <div class="detailLink">
                  {{ order.id }}번
              </div>
            </td>
            <td>{{ order.item }}</td>
          </tr>
        </tbody>
      </table>
    </section>
    <hr />
  </div>
</template>

<script setup>
import { useMyPageStore } from "@/stores/mypage";
import { ref, onMounted } from "vue";
import { useLoginStore } from "@/stores/login";

const store = useMyPageStore();
const loginStore = useLoginStore();
const mygrade = ref("");
onMounted(async () => {
  await loginStore.getJustUserInfo();
  console.log("이메일");
  console.log(loginStore.JustUserInfo.email);
  await store.getOrderList(loginStore.JustUserInfo.email);
  await loginStore.getGrade(loginStore.JustUserInfo.email);
});

onMounted(() => {});
</script>

<style scoped>

/* 전체 페이지 스타일 */
body {
  font-family: Arial, sans-serif;
  margin: 0;
  padding: 0;
}

.container {
  max-width: 800px;
  margin: 20px auto;
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  
}

h2 {
  margin-top: 0;
}

/* 주문 및 취소 테이블 스타일 */
table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 20px;
}

th, td {
  padding: 10px;
  text-align: left;
}

thead {
  background-color: #333;
  color: black;
}

tr:nth-child(even) {
  background-color: #f2f2f2;
}

/* 링크 스타일 */
a {
  color:black;
  text-decoration: none;
}

a:hover {
  text-decoration: underline;
}

.bronze {
  color : brown;
  font-size: 36px;
  font-weight: bold;
  text-shadow: 2px 2px 2px gray;
  display: flex;
  align-items: center;
}

.silver {
  color : gray;
  font-size: 36px;
  font-weight: bold;
  text-shadow: 2px 2px 2px gray;
  display: flex;
  align-items: center;
}

.gold {
  color : #ffd700;
  font-size: 36px;
  font-weight: bold;
  text-shadow: 2px 2px 2px gray;
  display: flex;
  align-items: center;
}

.platinum {
  color : palegreen;
  font-size: 36px;
  font-weight: bold;
  text-shadow: 2px 2px 2px gray;
  display: flex;
  align-items: center;
}



</style>
