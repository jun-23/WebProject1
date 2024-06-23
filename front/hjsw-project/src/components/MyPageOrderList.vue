<template>
  <div class="container">
    <h1>마이페이지</h1>
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
.container {
  max-width: 800px;
  margin: 20px auto;
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

h1 {
  margin-top: 0;
  text-align: center;
}

hr {
  border: none;
  border-top: 1px solid #ccc;
  margin: 20px 0;
}

table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 20px;
}

th, td {
  padding: 12px; /* Increased padding for better spacing */
  text-align: center;
}

thead {
  background-color: #333;
  color: white;
}

tbody tr:nth-child(even) {
  background-color: #f2f2f2;
}

.detailLink {
  display: flex;
  justify-content: center;
}

.detailLink a {
  color: #007bff; /* Link color */
  text-decoration: none;
  transition: color 0.3s ease;
}

.detailLink a:hover {
  color: #0056b3; /* Hover color */
}
</style>
