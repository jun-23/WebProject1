<template>
  <header>
    <div class="header">
      <div class="header-left">
        <RouterLink to="/" class="logo-letter">
          <img src="@/assets/ReadyAndRun_white.png" class="logo" />
          <span>ReadyAndRun</span>
        </RouterLink>
      </div>
      <div class="header-middle">
        <RouterLink to="/" class="nav-link">WORKOUT</RouterLink>
        <RouterLink to="/community" class="nav-link">COMMUNITY</RouterLink>
        <RouterLink to="/shop" class="nav-link">SHOP</RouterLink>
      </div>
      <div class="header-right">
        <div class="upper-content">
          <span v-if="userExist">{{ loginStore.JustUserInfo.email }}님</span>
          <span v-if="!userExist">로그인이 필요합니다.</span>
        </div>
        <div class="lower-content">
          <RouterLink v-if="!userExist" to="/login" class="nav-button"
            >LOGIN</RouterLink
          >
          <a v-if="userExist" @click="logout" class="nav-button">LOGOUT</a>
          <RouterLink v-if="userExist" to="/mypage" class="nav-link">MYPAGE</RouterLink>
        </div>
      </div>
    </div>
  </header>
</template>

<script setup>
import { onMounted, ref, computed } from "vue";
import { useLoginStore } from "@/stores/login";

const loginStore = useLoginStore();

const userExist = computed(
  () => loginStore.JustUserInfo && loginStore.JustUserInfo.email
);

const logout = function () {
  loginStore.logout();
};

onMounted(() => {
  loginStore.getJustUserInfo(); // LoginCookie를 가져오게 됨
});

onMounted(async () => {
  await loginStore.getJustUserInfo(); // 비동기 메서드가 완료될 때까지 기다림
  console.log("Login state:", loginStore.JustUserInfo);
});
</script>
<style scoped>
header {
  margin: 0;
  font-family: Arial, Helvetica, sans-serif;
  background-color: #24282b;
  color: aliceblue;
  min-height: 6vh;
  padding: 0px;
}

.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  overflow: hidden;
  background-color: #0b1622;
  padding: 10px 20px;
}
/* 이미지 스타일 */
.logo {
  float: left;
  padding: 0 !important;
  max-width: 100px; /* 최대 너비 설정 */
  max-height: 50px; /* 최대 높이 설정 */
}

.header .header-left {
  float: left;
  color: rgb(255, 255, 255);
  text-align: center;
  padding: 12px;
  text-decoration: none;
  font-size: 22px;
  line-height: 50px;
  border-radius: 4px;
}

.header .header-middle {
  float: left;
  color: rgb(255, 255, 255);
  text-align: center;
  padding: 12px;
  text-decoration: none;
  font-size: 22px;
  line-height: 25px;
  border-radius: 4px;
}

.header .header-right {
  color: rgb(255, 255, 255);
  float: left;
  text-align: center;
  padding: 12px;
  text-decoration: none;
  line-height: 25px;
  border-radius: 4px;
}

/* 링크 스타일 */
.nav-link {
  font-weight: bold; /* 굵은 글씨체 */
  text-decoration: none; /* 밑줄 제거 */
  color: inherit; /* 부모 요소의 색상 상속 */
  margin: 0 10px; /* 링크 간 간격 */
}
.nav-link:hover {
  color: #ffcc00; /* 호버 시 색상 변경 (선택 사항) */
}

/* 링크 스타일 */
.nav-button {
  font-weight: bold; /* 굵은 글씨체 */
  text-decoration: none; /* 밑줄 제거 */
  color: inherit; /* 부모 요소의 색상 상속 */
  margin: 0 10px; /* 링크 간 간격 */
}
.nav-button:hover {
  color: #ffcc00; /* 호버 시 색상 변경 (선택 사항) */
}

.logo-letter {
  font-weight: bold; /* 굵은 글씨체 */
  text-decoration: none; /* 밑줄 제거 */
  color: inherit; /* 부모 요소의 색상 상속 */
  margin: 0 10px; /* 링크 간 간격 */
}
.logo-letter:hover {
  color: pink;
}

.upper-content {
  display: flex;
  flex-direction: column;
  font-size: 14px;
}

.lower-content {
  display: flex;
  font-size: 18px;
}
</style>
