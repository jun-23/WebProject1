<template>
  <div>
    <h2>Testing for PortOne API</h2>
    <hr>
    <h3>Button 구현</h3>
    <button @click="requestPay">충전</button>
    <br>
    <hr>
  </div>
</template>

<script setup>
///// FontAwesome 설정
import { library } from "@fortawesome/fontawesome-svg-core";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { fas } from "@fortawesome/free-solid-svg-icons";

library.add(fas);

/////

import { ref, onMounted } from "vue";
import axios from 'axios';
import { useRouter } from 'vue-router';

// Iamport 스크립트를 로드하는 함수

const router = useRouter();

function loadIamportScript() {
  return new Promise((resolve, reject) => {
    const script = document.createElement("script");
    script.src = "https://cdn.iamport.kr/v1/iamport.js";
    script.onload = resolve;
    script.onerror = reject;
    document.head.appendChild(script);
  });
}

onMounted(async () => {
  try {
    await loadIamportScript();
    console.log("Iamport 스크립트가 성공적으로 로드되었습니다.");
    const IMP = window.IMP;
    IMP.init("imp68513146"); // Iamport 가맹점 식별코드로 변경하세요
  } catch (error) {
    console.error("Iamport 스크립트 로드 실패:", error);
  }
});

const requestPay = () => {
  const IMP = window.IMP; // 생략 가능 (근데 일단 문서에서는 포함되어있으니 추가)
  IMP.request_pay({
    pg: "html5_inicis", // 결제 시스템 ID로 변경하세요
    pay_method: "card",
    merchant_uid: "ORD20180131-0000012", // 주문 번호 // 해당 주문번호가 달라져야 새로운 결제를 할 수 있다.
    name: "테스트용 결제정보",
    amount: 100,
    buyer_email: "kkk@naver.com",
    buyer_name: "김결제",
    buyer_tel: "010-8282-9292",
    buyer_addr: "서울특별시 강남구 신사동",
    buyer_postcode: "01181"
  }, rsp => {
    if (rsp.success) {
      // axios로 HTTP 요청 // Back쪽 데이터를 받는 부분을 개선하여서 이를 쏘면 결제 정보를 서버에 저장할 수 있음
      axios({
        url: "{서버의 결제 정보를 받는 endpoint}",
        method: "post",
        headers: { "Content-Type": "application/json" },
        data: {
          imp_uid: rsp.imp_uid,
          merchant_uid: rsp.merchant_uid
        }
      }).then((data) => {
        // 서버 결제 API 성공 시 로직
        console.log("결제가 성공적으로 처리되었습니다.", data);
        router.push({ name: 'WorkOutClassView' }); // 결제 성공 후 홈으로 리디렉션
      }).catch((error) => {
        console.error("서버 결제 API 호출 실패:", error);
      });
    } else {
      alert(`결제에 실패하였습니다. 에러 내용: ${rsp.error_msg}`);
      router.push({ name: 'WorkOutClassView' }); // 결제 실패 후 홈으로 리디렉션
    }
  });
};
</script>



<style scoped>
</style>
