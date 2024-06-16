<template>
  <main>
    <h1>원하는 운동을 레벨에 맞게 선택하세요</h1>
    <div class="upside-buttons">
      <div class="btn-holder">
        <button
          class="btn btn-5 hover-border-11"
          @click="(event) => updateState(1, event)"
          :class="{ active: partInfo.value === 1 }"
        >
          <span
            ><font-awesome-icon icon="running" />
            <p>전신</p></span
          >
        </button>
      </div>
      <div class="btn-holder">
        <button
          class="btn btn-5 hover-border-11"
          @click="(event) => updateState(2, event)"
          :class="{ active: partInfo.value === 2 }"
        >
          <span
            ><font-awesome-icon icon="dumbbell" />
            <p>상체</p></span
          >
        </button>
      </div>

      <div class="btn-holder">
        <button
          class="btn btn-5 hover-border-11"
          @click="(event) => updateState(3, event)"
          :class="{ active: partInfo.value === 3 }"
        >
          <span
            ><font-awesome-icon :icon="['fas', 'person-biking']" />
            <p>하체</p></span
          >
        </button>
      </div>
      <div class="btn-holder">
        <button
          class="btn btn-5 hover-border-11"
          @click="(event) => updateState(4, event)"
          :class="{ active: partInfo.value === 4 }"
        >
          <span
            ><font-awesome-icon :icon="['fas', 'weight-hanging']" />
            <p>특수</p></span
          >
        </button>
      </div>
    </div>
    <!-- 위에까지가 버튼 아래부터는 티어별 영상 정리 -->
    <hr />

    <!-- Carousel 삽입 PLATINUM -->
    <div class="carousel-container">
      <div class="navigation-buttons">
        <button @click="prevP" class="carousel-buttons">
          <font-awesome-icon :icon="['fas', 'chevron-left']" />
        </button>
        &emsp;&emsp;&emsp;&emsp;
        <span>PLATINUM</span>&emsp;&emsp;&emsp;&emsp;
        <button @click="nextP" class="carousel-buttons">
          <font-awesome-icon :icon="['fas', 'chevron-right']" />
        </button>
      </div>
      <Flicking
        ref="flickingPlatinum"
        :options="{ align: 'prev', circular: true, panelsPerView: 3 }"
        @move-end="onMoveEndPlatinum"
      >
        <div
          class="panel"
          v-for="(image, index) in imagesPlatinum"
          :key="index"
        >
          <router-link
            :to="{
              name: 'videoList',
              params: { tier: 'platinum', part: partInfo },
            }"
          >
            <img :src="image.src" class="carousel-image" />
          </router-link>
        </div>
      </Flicking>
    </div>
    <br />

    <hr />
    <!-- Carousel 반복 - GOLD-->
    <div class="carousel-container">
      <div class="navigation-buttons">
        <button @click="prevG" class="carousel-buttons">
          <font-awesome-icon :icon="['fas', 'chevron-left']" />
        </button>
        &emsp;&emsp;&emsp;&emsp;
        <span>GOLD</span>&emsp;&emsp;&emsp;&emsp;
        <button @click="nextG" class="carousel-buttons">
          <font-awesome-icon :icon="['fas', 'chevron-right']" />
        </button>
      </div>
      <Flicking
        ref="flickingGold"
        :options="{ align: 'prev', circular: true, panelsPerView: 3 }"
        @move-end="onMoveEndGold"
      >
        <div class="panel" v-for="(image, index) in imagesGold" :key="index">
          <router-link
            :to="{
              name: 'videoList',
              params: { tier: 'gold', part: partInfo },
            }"
          >
            <img :src="image.src" class="carousel-image" />
          </router-link>
        </div>
      </Flicking>
    </div>
    <br />

    <hr />
    <!-- Carousel 반복 Silver-->
    <div class="carousel-container">
      <div class="navigation-buttons">
        <button @click="prevS" class="carousel-buttons">
          <font-awesome-icon :icon="['fas', 'chevron-left']" />
        </button>
        &emsp;&emsp;&emsp;&emsp;
        <span>SILVER</span>&emsp;&emsp;&emsp;&emsp;
        <button @click="nextS" class="carousel-buttons">
          <font-awesome-icon :icon="['fas', 'chevron-right']" />
        </button>
      </div>
      <Flicking
        ref="flickingSilver"
        :options="{ align: 'prev', circular: true, panelsPerView: 3 }"
        @move-end="onMoveEndSilver"
      >
        <div class="panel" v-for="(image, index) in imagesSilver" :key="index">
          <router-link
            :to="{
              name: 'videoList',
              params: { tier: 'shilver', part: partInfo },
            }"
          >
            <img :src="image.src" class="carousel-image" />
          </router-link>
        </div>
      </Flicking>
    </div>
    <br />

    <hr />
    <!-- Carousel 반복 Bronze-->
    <div class="carousel-container">
      <div class="navigation-buttons">
        <button @click="prevB" class="carousel-buttons">
          <font-awesome-icon :icon="['fas', 'chevron-left']" />
        </button>
        &emsp;&emsp;&emsp;&emsp;
        <span>BRONZE</span>&emsp;&emsp;&emsp;&emsp;
        <button @click="nextB" class="carousel-buttons">
          <font-awesome-icon :icon="['fas', 'chevron-right']" />
        </button>
      </div>
      <Flicking
        ref="flickingBronze"
        :options="{ align: 'prev', circular: true, panelsPerView: 3 }"
        @move-end="onMoveEndBronze"
      >
        <div class="panel" v-for="(image, index) in imagesBronze" :key="index">
          <router-link
            :to="{
              name: 'videoList',
              params: { tier: 'bronze', part: partInfo },
            }"
          >
            <img :src="image.src" class="carousel-image" />
          </router-link>
        </div>
      </Flicking>
    </div>
    <!-- Carousel 반복-->
  </main>
</template>

<script setup>
import { onMounted, ref } from "vue";
// Carousel Needed it
const flickingPlatinum = ref(null);
const flickingGold = ref(null);
const flickingSilver = ref(null);
const flickingBronze = ref(null);

//PLATINUM
const prevP = () => {
  flickingPlatinum.value.prev().catch(console.error);
};
const nextP = () => {
  flickingPlatinum.value.next().catch(console.error);
};
const onMoveEndPlatinum = (e) => {
  console.log("Move end", e);
};

//GOLD
const prevG = () => {
  flickingGold.value.prev().catch(console.error);
};
const nextG = () => {
  flickingGold.value.next().catch(console.error);
};
const onMoveEndGold = (e) => {
  console.log("Move end", e);
};
//SILVER
const prevS = () => {
  flickingSilver.value.prev().catch(console.error);
};
const nextS = () => {
  flickingSilver.value.next().catch(console.error);
};
const onMoveEndSilver = (e) => {
  console.log("Move end", e);
};
//BRONZE
const prevB = () => {
  flickingBronze.value.prev().catch(console.error);
};
const nextB = () => {
  flickingBronze.value.next().catch(console.error);
};
const onMoveEndBronze = (e) => {
  console.log("Move end", e);
};
// Platinum Photos
import Pla_1 from "@/assets/main/Pla_1.jpg";
import Pla_2 from "@/assets/main/Pla_2.jpg";
import Pla_3 from "@/assets/main/Pla_3.jpg";
import Pla_4 from "@/assets/main/Pla_4.jpg";
import Pla_5 from "@/assets/main/Pla_5.jpg";
import Pla_6 from "@/assets/main/Pla_6.jpg";

import Gold_1 from "@/assets/main/Gold_1.jpg";
import Gold_2 from "@/assets/main/Gold_2.jpg";
import Gold_3 from "@/assets/main/Gold_3.jpg";
import Gold_4 from "@/assets/main/Gold_4.jpg";
import Gold_5 from "@/assets/main/Gold_5.jpg";
import Gold_6 from "@/assets/main/Gold_6.jpg";

import Sil_1 from "@/assets/main/Sil_1.jpg";
import Sil_2 from "@/assets/main/Sil_2.jpg";
import Sil_3 from "@/assets/main/Sil_3.jpg";
import Sil_4 from "@/assets/main/Sil_4.jpg";
import Sil_5 from "@/assets/main/Sil_5.jpg";
import Sil_6 from "@/assets/main/Sil_6.jpg";

import Bro_1 from "@/assets/main/Bro_1.jpg";
import Bro_2 from "@/assets/main/Bro_2.jpg";
import Bro_3 from "@/assets/main/Bro_3.jpg";
import Bro_4 from "@/assets/main/Bro_4.jpg";
import Bro_5 from "@/assets/main/Bro_5.jpg";
import Bro_6 from "@/assets/main/Bro_6.jpg";

const imagesPlatinum = ref([
  { src: Pla_1 },
  { src: Pla_2 },
  { src: Pla_3 },
  { src: Pla_4 },
  { src: Pla_5 },
  { src: Pla_6 },
]);
const imagesGold = ref([
  { src: Gold_1 },
  { src: Gold_2 },
  { src: Gold_3 },
  { src: Gold_4 },
  { src: Gold_5 },
  { src: Gold_6 },
]);
const imagesSilver = ref([
  { src: Sil_1 },
  { src: Sil_2 },
  { src: Sil_3 },
  { src: Sil_4 },
  { src: Sil_5 },
  { src: Sil_6 },
]);
const imagesBronze = ref([
  { src: Bro_1 },
  { src: Bro_2 },
  { src: Bro_3 },
  { src: Bro_4 },
  { src: Bro_5 },
  { src: Bro_6 },
]);

// Carousel

///// for FontAwesome
import { library } from "@fortawesome/fontawesome-svg-core";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { fas } from "@fortawesome/free-solid-svg-icons";

library.add(fas);

/////

const partInfo = ref(1);

const updateState = (value, event) => {
  partInfo.value = value;

  // Remove active class from all buttons
  const buttons = document.querySelectorAll(".btn span");
  buttons.forEach((button) => button.classList.remove("active"));

  // Add active class to the clicked button
  const button = event.currentTarget.querySelector("span");
  button.classList.add("active");
};

onMounted(() => {
  // Set initial state to 전신 (partInfo.value = 1)
  const initialButton = document.querySelector(
    ".btn-holder:first-child .btn span"
  );
  initialButton.classList.add("active");
});
</script>

<style scoped>
.upside-buttons {
  display: flex;
  margin-left: 18%;
  margin-right: 18%;
  margin-block-start: 2%;
}

* {
  box-sizing: border-box;
  margin: 0;
  padding: 0;
}
body {
  font-family: "Roboto", sans-serif;
  color: rgb(22, 214, 80);
  background-color: rgb(235, 12, 12);
}
h1 {
  padding: 50px 0;
  font-size: 45px;
  text-align: center;
  color: rgb(21, 187, 104);
}
:active,
:hover,
:focus {
  outline: 0 !important;
  outline-offset: 0;
}
::before,
::after {
  position: absolute;
  content: "";
}

.btn-holder {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  max-width: 1000px;
  margin: 10px auto 35px;
}
.btn {
  /* 육각형 스타일 추가 polygon */
  position: relative;
  display: inline-block;
  width: auto;
  height: auto;
  background-color: transparent;
  border: none;
  cursor: pointer;
  margin: 0px 25px 15px;
  min-width: 150px;
}
.btn span {
  position: relative;
  display: inline-block;
  font-size: 14px;
  font-weight: bold;
  letter-spacing: 2px;
  text-transform: uppercase;
  top: 0;
  left: 0;
  width: 100%;
  padding: 15px 20px;
  transition: 0.3s;
}

/*--- btn-5 ---*/
.btn-5 span {
  color: rgb(28, 31, 30);
  border: 2px solid rgb(152, 134, 175);
  transition: 0.2s;
  font-size: 70px;
}
.btn-5 span p {
  font-size: 26px;
}
.btn-5 span:hover {
  background-color: rgb(126, 230, 221);
}
/* 21. hover-border-11 */
.btn.hover-border-11::before,
.btn.hover-border-11::after {
  width: 100%;
  height: 2px;
  background-color: rgb(0, 0, 0);
  z-index: 2;
  transition: 0.35s;
}
.btn.hover-border-11::before {
  top: 0;
  right: 0;
}
.btn.hover-border-11::after {
  bottom: 0;
  left: 0;
}
.btn.hover-border-11:hover::before,
.btn.hover-border-11:hover::after {
  width: 0%;
  transition: 0.2s 0.2s ease-out;
}
.btn.hover-border-11 span::before,
.btn.hover-border-11 span::after {
  width: 2px;
  height: 100%;
  background-color: rgb(55, 68, 67);
  z-index: 2;
  transition: 0.25s;
}
.btn.hover-border-11 span::before {
  bottom: 0;
  right: -2px;
}
.btn.hover-border-11 span::after {
  top: 0;
  left: -2px;
}
.btn.hover-border-11 span:hover::before,
.btn.hover-border-11 span:hover::after {
  height: 0%;
}

/* Add this CSS for the active state */
.btn-5 span.active,
.btn-5 span.active::before,
.btn-5 span.active::after,
.btn-5.active::before,
.btn-5.active::after {
  background-color: rgb(0, 0, 0);
  color: aliceblue;
}

/* Carousel CSS */
.carousel-container {
  width: 100%;
  max-width: 1200px;
  margin: 0 auto;
  text-align: center;
}

.panel {
  width: 33.33%;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 10px;
  box-sizing: border-box;
}

.carousel-image {
  width: 100%;
  height: auto;
  width: auto;
  min-height: 225px;
  min-width: 400px;
  max-height: 350px;
  max-width: 250px;
  object-fit: contain;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s;
}
.carousel-image:hover {
  transform: scale(1.05);
}

.navigation-buttons {
  margin-top: 20px;
}

.navigation-buttons span {
  font-family: "Roboto", sans-serif;
  font-size: 36px;
  font-weight: bold;
}

.carousel-buttons {
  margin: 0 10px;
  padding: 10px 20px;
  font-size: 32px;
  border: none;
  border-radius: 10px;
  color: rgb(0, 0, 0);
  cursor: pointer;
  transition: background-color 0.3s;
}

.carousel-buttons:hover {
  color: aqua !important;
}

hr {
  margin-left: 10%;
  margin-right: 10%;
  opacity: 30%;
}
</style>
