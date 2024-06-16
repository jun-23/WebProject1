<template>
  <div class="carousel-container">
    <div class="navigation-buttons">
      <button @click="prev" class="carousel-buttons">
        <font-awesome-icon :icon="['fas', 'chevron-left']" />
      </button>
      &emsp;&emsp;&emsp;&emsp;
      <span>PLATINUM</span>&emsp;&emsp;&emsp;&emsp;
      <button @click="next" class="carousel-buttons">
        <font-awesome-icon :icon="['fas', 'chevron-right']" />
      </button>
    </div>
    <Flicking
      ref="flicking"
      :options="{ align: 'prev', circular: true, panelsPerView: 3 }"
      @move-end="onMoveEnd"
    >
      <div class="panel">
        <router-link to="/">
          <img src="@/assets/google.png" class="carousel-image" />
        </router-link>
      </div>
      <div class="panel">
        <router-link to="/">
          <img src="@/assets/google.png" class="carousel-image" />
        </router-link>
      </div>
      <div class="panel">
        <router-link to="/">
          <img src="@/assets/google.png" class="carousel-image" />
        </router-link>
      </div>
      <div class="panel">
        <router-link to="/">
          <img src="@/assets/google.png" class="carousel-image" />
        </router-link>
      </div>
      <div class="panel">
        <img src="@/assets/ReadyAndRun_Logo.png" class="carousel-image" />
      </div>
      <div class="panel">
        <img src="@/assets/naver.png" class="carousel-image" />
      </div>
      <div class="panel">
        <img src="@/assets/kakao.png" class="carousel-image" />
      </div>
    </Flicking>
  </div>
</template>

<script setup>
///// for FontAwesome
import { library } from "@fortawesome/fontawesome-svg-core";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { fas } from "@fortawesome/free-solid-svg-icons";

library.add(fas);

/////

import { ref } from "vue";

const flicking = ref(null);

const prev = () => {
  flicking.value.prev().catch(console.error);
};

const next = () => {
  flicking.value.next().catch(console.error);
};

const onMoveEnd = (e) => {
  console.log("Move end", e);
};
</script>

<style scoped>
.carousel-container {
  width: 50%;
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
  min-height: 300px;
  min-width: 200px;
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
  color: aqua;
}
</style>
