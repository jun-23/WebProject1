<template>
  <div>
    <h2>로그인이 잘 되었다!</h2>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useRoute, useRouter } from "vue-router";
import { useCookies } from "vue3-cookies";
import { useLoginStore } from "@/stores/login";

const loginStore = useLoginStore();

const store = useLoginStore();
const route = useRoute();
const router = useRouter();

const { cookies } = useCookies();

// cookies.set("test", "lulluby");

if (true) {
  loginStore.loginByCookie(); // 쿠키를 수집하고
  // console.log(cookies.get("test")); // 여기 쿠키랑 - 서버 쿠키랑 대조
  // console.log("잘 가는지 확인용");
  window.alert("로그인하였습니다.");
  // router.push({ path: "/" }); // 이걸 쓰면 한번더 보내는꼴이 됨.
} else {
  window.alert("로그인에 실패하였스무니다");
  router.push({ path: "/login" });
}
</script>

<style scoped></style>
