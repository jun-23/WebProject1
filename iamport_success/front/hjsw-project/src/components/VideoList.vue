<template>
  <div class="container">
    <h2 v-if="PresentTier === 'platinum'">PLATINUM 등급 영상 클래스</h2>
    <h2 v-if="PresentTier === 'gold'">GOLD 등급 영상 클래스</h2>
    <h2 v-if="PresentTier === 'shilver'">SILVER 등급 영상 클래스</h2>
    <h2 v-if="PresentTier === 'bronze'">BRONZE 등급 영상 클래스</h2>
    <table class="videoTable">
      <tr>
        <th>제목</th>
        <th>만든이</th>
        <th>시간</th>
        <th>파트</th>
        <th>티어</th>
      </tr>
      <tr v-for="video in store.videoList" :key="video.id">
        <td v-if="video.tier === PresentTier && video.part === PresentPart">
          <div v-if="Tiergrade <= loginStore.grade" class="detailLink">
            <router-link :to="`/video/${video.videoId}`"
              >{{ video.title }}번</router-link
            >
          </div>
          <div v-if="Tiergrade > loginStore.grade" class="detailLink">
            <router-link :to="`/tier`"
              >{{ video.title }}번</router-link
            >
          </div>
        </td>

        <td v-if="video.tier === PresentTier && video.part === PresentPart">
          {{ video.maker }}
        </td>
        <td v-if="video.tier === PresentTier && video.part === PresentPart">
          {{ video.runningtime }}분
        </td>
        <td v-if="video.tier === PresentTier && video.part === PresentPart">
          {{ video.part }}
        </td>
        <td v-if="video.tier === PresentTier && video.part === PresentPart">
          {{ video.tier }}
        </td>
      </tr>
    </table>
    <hr />
  </div>

  <div class="container">
    <h3>해당 클래스의 리뷰들</h3>
    <table class="reviewClass">
      <tr>
        <th style="width: 10%; font-size: 0.5em"></th>
        <th style="font-size: 0.8em"></th>
        <th style="width: 10%"></th>
      </tr>
      <tr
        v-for="review in store.reviewList.filter((c) => c.tier === PresentTier)"
        :key="review.tierReviewId"
      >
        <td>{{ review.user.email }}</td>
        <td v-if="editReviewId === review.tierReviewId">
          <input type="text" v-model="editTierReview" />
          <!-- 수정 후에 저장 or 취소 -->
          <section class="btn-section">
            <button class="btn-cp" @click="saveReview(review.tierReviewId)">
              <i class="fab fa-lg first-logo fa-codepen"
                ><font-awesome-icon :icon="['fas', 'floppy-disk']"
              /></i>
              <i class="fab fa-lg second-logo fa-codepen"
                ><font-awesome-icon :icon="['fas', 'floppy-disk']"
              /></i>
            </button>
            <button class="btn-cp" @click="cancelEdit">
              <i class="fab fa-lg first-logo fa-codepen"
                ><font-awesome-icon :icon="['fas', 'xmark']"
              /></i>
              <i class="fab fa-lg second-logo fa-codepen"
                ><font-awesome-icon :icon="['fas', 'xmark']"
              /></i>
            </button>
          </section>
          <!-- // / // -->
        </td>
        <td v-if="editReviewId != review.tierReviewId">
          {{ review.tierReview }}
        </td>
        <td>
          <div v-if="review.user.email === loginStore.JustUserInfo.email">
            <!-- 수정 Or 삭제 버튼-->
            <section class="btn-section">
              <button
                class="btn-cp"
                @click="editReview(review.tierReviewId, review.tierReview)"
              >
                <i class="fab fa-lg first-logo fa-codepen"
                  ><font-awesome-icon :icon="['fas', 'pen-to-square']"
                /></i>
                <i class="fab fa-lg second-logo fa-codepen"
                  ><font-awesome-icon :icon="['fas', 'pen-to-square']"
                /></i>
              </button>
              <button class="btn-cp" @click="deleteReview(review.tierReviewId)">
                <i class="fab fa-lg first-logo fa-codepen"
                  ><font-awesome-icon :icon="['fas', 'trash-can']"
                /></i>
                <i class="fab fa-lg second-logo fa-codepen"
                  ><font-awesome-icon :icon="['fas', 'trash-can']"
                /></i>
              </button>
            </section>
            <!-- 거식 -->
          </div>
        </td>
      </tr>
    </table>
    <hr />
    <!-- TierReview를 추가할 수 있는 입력창입니다.-->
    <div>
      <h4>TierReview 입력</h4>
      <input
        type="text"
        class="inputField"
        id="newReview"
        placeholder="내용"
        v-model="newReview.tierReview"
      />
      <label for="newReview"></label>
      <button class="button" @click="addReview">댓글 입력</button>
    </div>
  </div>
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

// 새로운 TierReview
const newReview = ref({
  tierReview: "",
});
// 리뷰 생성
const addReview = function () {
  const email = loginStore.JustUserInfo.email; // 적절한 이메일 값 처리
  store.createReview(email, PresentTier.value, newReview.value);
};

// 수정할 리뷰 REF
const editReviewId = ref(null);
const editTierReview = ref("");

//리뷰 수정 시작
const editReview = function (reviewId, tierReview) {
  editReviewId.value = reviewId;
  editTierReview.value = tierReview;
};
//리뷰 수정 저장
const saveReview = function (reviewId) {
  const email = loginStore.JustUserInfo.email; // 후추 수정
  store.updateReview(reviewId, email, {
    tierReview: editTierReview.value,
  });
  editReviewId.value = null;
  editTierReview.value = "";
};
//리뷰 수정 취소
const cancelEdit = function () {
  editReviewId.value = null;
  editTierReview.value = "";
};
// 리뷰 삭제
const deleteReview = function (reviewId) {
  const email = loginStore.JustUserInfo.email; // 적절한 이메일 값으로 변경
  store.deleteReview(reviewId, email);
};

onMounted(async () => {
  await loginStore.getJustUserInfo(); // 로그인 정보 추출
  if(loginStore.JustUserInfo.data){
    console.log(loginStore.JustUserInfo.email.value);
  }
  await loginStore.getGrade(loginStore.JustUserInfo.email); //grade 추출
  store.gotoVideoApi(); // 영상 정보들 추출
  // console.log(PresentTier.value); // 잘 추출한다.
  // console.log(PresentPartNum.value); // 잘 추출한다.
  if (PresentPartNum.value === 1) {
    PresentPart.value = "전신";
  } else if (PresentPartNum.value === 2) {
    PresentPart.value = "상체";
  } else if (PresentPartNum.value === 3) {
    PresentPart.value = "하체";
  } else if (PresentPartNum.value === 4) {
    PresentPart.value = "특수";
  }
  if (PresentTier === "bronze") {
    Tiergrade.value = 0;
  } else if (PresentTier.value === "shilver") {
    Tiergrade.value = 1;
  } else if (PresentTier.value === "gold") {
    Tiergrade.value = 2;
  }else if (PresentTier.value === "platinum") {
    Tiergrade.value = 3;
  }

  console.log(PresentPart.value); // 잘 추출한다.
  store.getTierReviewList();
  

  console.log(PresentTier.value)
  console.log("티어그래드")
  console.log(Tiergrade.value)
});
onMounted(() => {});

// 제약을 걸어야 하는 상황에서는 onMounted를 통해 받아온 유저 정보에서
// 티어가 원하는 티어가 해당 페이지 티어 미만일 경우 Home으로 Route 해버리거나
// 아니면 인증되었습니다~ 띄우고 그냥 아무짓도 안하기
// 매순간 유저정보를 들고 있으니까 가능
</script>
<style>
/* 스타일 시작 */
/* 전체 컨테이너 */
.container {
  max-width: 1200px;
  margin: auto;
  padding: 20px;
}

/* 헤더 스타일 */
.header {
  background-color: #333;
  color: #fff;
  padding: 20px;
  text-align: center;
}

/* 영상 목록 테이블 스타일 */
.videoTable {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 20px;
}

.videoTable th,
.videoTable td {
  padding: 12px;
  text-align: left;
  border-bottom: 1px solid #ddd;
}

.videoTable th {
  background-color: #f2f2f2;
  color: #333;
}

.videoTable td {
  color: #666;
}

.videoTable th:first-child,
.videoTable td:first-child {
  width: 50%;
}

/* 리뷰 목록 테이블 스타일 */
.reviewTable {
  width: 100%;
  border-collapse: collapse;
}

.reviewTable th,
.reviewTable td {
  padding: 12px;
  text-align: left;
  border-top: 1px dotted #ddd;
}

.reviewTable th {
  background-color: #f2f2f2;
  color: #333;
}

.reviewTable td {
  color: #666;
}

/* 버튼 스타일 */
.button {
  padding: 10px 20px;
  background-color: #007bff;
  color: #fff;
  border: none;
  cursor: pointer;
  border-radius: 5px;
  transition: background-color 0.3s ease;
}

.button:hover {
  background-color: #0056b3;
}

/* 입력창 스타일 */
.inputField {
  width: 100%;
  padding: 10px;
  margin-bottom: 20px;
  border: 1px solid #ccc;
  border-radius: 5px;
  box-sizing: border-box;
}

.inputField:focus {
  outline: none;
  border-color: #007bff;
}

/* 수정/삭제 버튼 그룹 스타일 */
.editButtons {
  display: flex;
  align-items: center;
}

.editButtons button {
  margin-right: 10px;
}

/* 스타일 끝 */
</style>
