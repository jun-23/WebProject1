<template>
<body>
    <div>
        <div class="exp-container">
            <h1>아임포트 테스트</h1>
            <h3>아임포트 뷰 테스트 화면입니다.</h3>
            <h3>아래 버튼을 눌러 결제 또는 본인인증 테스트를 진행해주세요.</h3>
        </div>
        <div class="imp-home">
            <div class="nav-container">
                <router-link :to="{ path: '/iamportpayment' }">
                    <a-icon type="credit-card" />
                    결제
                </router-link>
                <router-link :to="{ path: '/iamportcertification' }">
                    <a-icon type="user" />
                    본인인증
                </router-link>
            </div>
        </div>
    </div>
</body>
</template>

<script setup>

///// for FontAwesome
import { library } from "@fortawesome/fontawesome-svg-core";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { fas } from "@fortawesome/free-solid-svg-icons";

library.add(fas);
/////
import { useVideoStore } from "@/stores/video";
import { useLoginStore } from "@/stores/login";
import { onMounted, ref } from "vue";
import { useRoute, useRouter } from "vue-router";

const route = useRoute();
const router = useRouter();

const PresentTier = ref(route.params.tier);
const PresentPartNum = ref(Number(route.params.part)); // Convert to number
const PresentPart = ref("");
const Tiergrade = ref(0);

const store = useVideoStore();
const loginStore = useLoginStore();



</script>

<style scoped>
.body{
}
.exp-container {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 50%;
  display: flex;
  align-items: center;
  flex-direction: column;
  justify-content: center;

  * {
    color: #fff;
  }
}

.imp-home {
  position: absolute;
  top: 50%;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: #fff;

  .nav-container {
    display: flex;
    align-items: center;
    justify-content: center;
    position: absolute;
    top: -75px;
    left: 0;
    right: 0;
    a {
      color: #344e81;
      box-shadow: 0 0 1rem 0 rgba(0, 0, 0, 0.13);
      display: flex;
      align-items: center;
      justify-content: center;
      flex-direction: column;
      background-color: #fff;
      margin: 0 10px;
      width: 200px;
      height: 150px;
      border-radius: 3px;
      font-size: 18px;
      .anticon {
        margin-bottom: 20px;
        font-size: 40px;
      }
    }
  }
}
</style>