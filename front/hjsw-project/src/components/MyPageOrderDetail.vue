<template>
  <div class="payment-details">
    <div><h3>결제 상세</h3></div>
    <table>
      <tr>
        <td class="label"> 상품명:</td>
        <td class="value">{{ store.order.item_name }}</td>
      </tr>
      <tr>
        <td class="label"> 가격:</td>
        <td class="value">{{ store.order.cost }}</td>
      </tr>
      <tr>
        <td class="label"> 결제 날짜:</td>
        <td class="value">{{ store.order.approved_at }}</td>
      </tr>
    </table>
    <div class="buttons">
      <a class="refund-button" :href="refund" @click="alert">환불하기</a>
    </div>
  </div>
</template>

<script setup>
import { useMyPageStore } from "@/stores/mypage";
import { onMounted, ref, computed } from "vue";
import { useRoute, useRouter } from "vue-router";

const route = useRoute();
const router = useRouter();

const id = ref(route.params.id);

const store = useMyPageStore();
const alert = function(){
  window.alert("환불작업이 실행됩니다.")
}
onMounted(() => {
  store.getOrder(id.value);
});

const refund = computed(() => {
  return `http://localhost:8080/api/kakaopay/cancel/${id.value}`;
});
</script>


<style scoped>
.payment-details {
  background-color: #f8f9fa;
  border: 1px solid #ced4da;
  border-radius: 8px;
  padding: 20px;
  max-width: 800px;
  margin: 20px auto;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

table {
  width: 100%;
}

tr {
  border-bottom: 1px solid #ddd;
}

td {
  padding: 10px 10px;

}

.label {
  font-weight: bold;
  color: #333;
  width: 30%;
}

.value {
  color: #666;
  width: 70%;
}

.buttons {
  margin-top: 20px;
}

.refund-button {
  display: inline-block;
  padding: 10px 20px;
  background-color: #dc3545;
  color: #fff;
  border: none;
  border-radius: 5px;
  text-decoration: none;
  transition: background-color 0.3s ease;
}

.refund-button:hover {
  background-color: #c82333;
}
</style>

