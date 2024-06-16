<template>
  <footer id="footer" class="footer">
    <div class="footer-inner">
      <ul class="part-list">
        <li><a href="#" onclick="fnPolicyPopup();">이용약관</a></li>
        <li>
          <a href="#" class="fc-blue" onclick="fnPrivacyPopup();"
            >개인정보처리방침</a
          >
        </li>
      </ul>
      <address>본 사이트는 수완과 해준의 Final Project입니다.</address>

      <div class="footer-desc">
        Copyright © Soowan & Haejun All Rights Reserved.
      </div>
    </div>
  </footer>
</template>

<script setup></script>

<style scoped>
.footer {
  padding: 40px 0;
  background: #24282b;
}

.footer .footer-inner {
  position: relative;
  width: 100%;
  max-width: 1200px;
  margin: 0 auto;
  padding-left: 159px;
}

.footer .footer-inner::before {
  position: absolute;
  top: 0;
  left: 0;
  width: 100px;
  height: 70px;
  background: url("@/assets/ReadyAndRun_white.png") center center no-repeat;
  background-size: contain;
  content: "";
}

.footer .part-list {
  font-size: 0;
}
li,
ol,
ul {
  list-style: none;
}
a {
  color: inherit;
  text-decoration: none;
}
.footer .part-list li::before {
  position: absolute;
  top: 50%;
  -webkit-transform: translateY(-50%);
  transform: translateY(-50%);
  left: 0;
  width: 1px;
  height: 12px;
  background: #4a5159;
  content: "";
}
.footer .part-list li a {
  font-size: 14px;
  color: #fff;
  font-weight: 200;
}
.fc-blue {
  color: #3c90e2 !important;
}
.footer address {
  padding: 20px 0 10px;
  font-size: 12px;
  color: #8c99a8;
  font-style: normal;
}
.footer-desc {
  font-size: 12px;
  color: #fff;
  font-weight: 200;
}
</style>
