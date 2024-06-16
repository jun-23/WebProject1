import { ref } from "vue";
import { defineStore } from "pinia";
import router from "@/router";
import axios from "axios";

const REST_API = `http://localhost:8080/`;
const REST_API_VIDEO = `http://localhost:8080/videoapi/video`;
const REST_API_REVIEW = `http://localhost:8080/videoapi/review`;

export const useVideoStore = defineStore("video", () => {
  //영상정보가 잘 가는지 확인하기 위한
  const videoList = ref([]);

  const gotoVideoApi = function () {
    axios
      .get(`${REST_API_VIDEO}`)
      .then((response) => {
        videoList.value = response.data;
        // console.log(response.data);
      })
      .catch((error) => {
        console.error("오류: ", error);
      });
  };

  //영상 한개를 가져온다.
  const video = ref({});

  const getVideo = function (id) {
    axios
      .get(`${REST_API_VIDEO}/${id}`)
      .then((response) => {
        video.value = response.data;
      })
      .catch((err) => {
        console.log(err);
      });
  };

  //해당 클래스에 대한 리뷰들을 가져온다.
  const reviewList = ref([]);
  const getTierReviewList = function () {
    axios
      .get(`${REST_API_REVIEW}`)
      .then((response) => {
        reviewList.value = response.data;
        // console.log(response.data);
      })
      .catch((error) => {
        console.error("오류: ", error);
      });
  };

  // 해당 클래스에 대한 리뷰를 생성한다.
  const createReview = function (email, tier, tierReview) {
    axios({
      url: `${REST_API_REVIEW}`,
      method: "POST",
      params: { email: email, tier: tier },
      data: tierReview,
    })
      .then((response) => {
        // console.log("리뷰 생성 성공: ", response.data);
        // console.log("tierReview 메서드 입력 값: ", tierReview);

        // 새로 생성된 리뷰를 리뷰 목록에 추가 -> 동적으로 화면에 표기됨(굳이 안불러와도 실시간 반영)
        reviewList.value.push(response.data);
      })
      .catch((error) => {
        console.error("리뷰 생성 오류: ", error);
      });
  };

  // 리뷰 업데이트
  const updateReview = function (reviewId, email, tierReview) {
    axios({
      url: `${REST_API_REVIEW}/${reviewId}`,
      method: "PUT",
      params: { email: email },
      data: tierReview,
    })
      .then((response) => {
        console.log("리뷰 수정 성공: ", response.data);
        const index = reviewList.value.findIndex(
          (review) => review.tierReviewId === reviewId
        );
        if (index !== -1) {
          reviewList.value[index] = response.data;
        }
      })
      .catch((error) => {
        console.error("리뷰 수정 오류: ", error);
      });
  };

  //리뷰 삭제
  const deleteReview = function (reviewId, email) {
    axios
      .delete(`${REST_API_REVIEW}/${reviewId}`, { params: { email } })
      .then(() => {
        console.log("리뷰 삭제 성공");
        reviewList.value = reviewList.value.filter(
          (review) => review.tierReviewId !== reviewId
        );
      })
      .catch((error) => {
        console.error("리뷰 삭제 오류: ", error);
      });
  };

  return {
    videoList,
    gotoVideoApi,
    video,
    getVideo,
    reviewList,
    getTierReviewList,
    createReview,
    updateReview,
    deleteReview,
  };
},{
  persist:true
});
