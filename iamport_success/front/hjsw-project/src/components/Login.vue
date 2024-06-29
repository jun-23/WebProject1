<template>
  <main>
    <div class="login-container">
      <h2 class="login-title">원하는 로그인 세션을 선택하여 주십시오.</h2>
      <div class="form-group d-flex justify-content-center">
        <a
          href="http://localhost:8080/oauth2/authorization/google?redirect_uri=http://localhost:5173/redirect"
        >
          <img class="social-icon" src="../assets/google.png" alt="Google" />
        </a>
        <a
          href="http://localhost:8080/oauth2/authorization/naver?redirect_uri=http://localhost:5173/redirect"
        >
          <img class="social-icon" src="../assets/naver.png" alt="Naver" />
        </a>
        <a href="http://localhost:8080/oauth2/authorization/kakao">
          <img class="social-icon" src="../assets/kakao.png" alt="Kakao" />
        </a>
      </div>
    </div>
  </main>
</template>

<style scoped>
.login-container {
  text-align: center;
  padding: 50px;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(255, 255, 255, 0.1);
}

.login-title {
  font-size: 24px;
  font-weight: bold;
  color: #333;
  margin-bottom: 30px;
}

.social-icon {
  width: 55px;
  height: 55px;
  margin-right: 20px;
  border-radius: 50%;
  transition: transform 0.3s ease;
}

.social-icon:hover {
  transform: scale(1.1);
}
</style>
