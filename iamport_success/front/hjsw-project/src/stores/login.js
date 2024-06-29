import { ref } from "vue";
import { defineStore } from "pinia";
import router from "@/router";
import axios from "axios";

const REST_API_LOGIN = `http://localhost:8080/userapi/login`;
const REST_API_LOGOUT = `http://localhost:8080/userapi/logout`;
const REST_API_GOOGLE = `http://localhost:8080/oauth2/authorization/google`;
const REST_API_VIDEO = `http://localhost:8080/videoapi/video`;
const Rest_API_USER = `http://localhost:8080/userapi`;

export const useLoginStore = defineStore("login", () => {
  //redirect - 즉 최초 로그인 당시 사용하는 쿠키 / 세션 정보 활용
  // 이것을 쓰면 home으로 돌아간다.
  let LoginCookie = ref({});

  const loginByCookie = function () {
    axios
      .get(`${REST_API_LOGIN}`) // REST_API_LOGIN 변수를 사용하여 요청 보내기
      .then((response) => {
        // console.log("sessionID와 userEmail을 받고 Home으로 회귀");
        LoginCookie.value = response.data; //sessionID and userEmail을 Map<String,String>으로 받음
        // console.log(LoginCookie.value);
        router.push({ path: "/" }); // 홈 페이지로 라우팅
      })
      .catch((error) => {
        console.error("오류 발생:", error); // 오류가 발생한 경우 콘솔에 오류 메시지 출력
      });
  };

  // ------- -------

  // postCreat를 위한 정보 --> postCreate로 돌아간다.
  let OnlyUserInfo = ref({});

  const getOnlyUserInfo = function () {
    axios
      .get(`${REST_API_LOGIN}`) // REST_API_LOGIN 변수를 사용하여 요청 보내기
      .then((response) => {
        // console.log(
        //   "sessionID와 userEmail을 받고 postCreate로 돌아가서 유저 정보 기입"
        // );
        OnlyUserInfo.value = response.data; //sessionID and userEmail을 Map<String,String>으로 받음
        // console.log(OnlyUserInfo.value);
        router.push({ path: "/postCreate" }); // 홈 페이지로 라우팅
      })
      .catch((error) => {
        console.error("오류 발생:", error); // 오류가 발생한 경우 콘솔에 오류 메시지 출력
      });
  };

  //----- ------ ------ 

  let JustUserInfo = ref({});

  const getJustUserInfo = function () {
    axios
      .get(`${REST_API_LOGIN}`) // REST_API_LOGIN 변수를 사용하여 요청 보내기
      .then((response) => {
        // console.log("sessionID와 userEmail을 받고 현재 페이지로 라우팅");
        JustUserInfo.value = response.data; //sessionID and userEmail을 Map<String,String>으로 받음
        // console.log(JustUserInfo.value);
        // console.log("로그인되었습니다.")
        router.push(router.currentRoute.value); // 현재 페이지로 라우팅
      })
      .catch((error) => {
        console.error("오류 발생:", error); // 오류가 발생한 경우 콘솔에 오류 메시지 출력
      });
  };

  // 로그아웃 기능
  // 세션을 초기화 시키고 / 홈으로 보낸다.
  // 그리고 에러페이지 하나 만들어야 한다.
  const logout = function () {
    axios
      .get(`${REST_API_LOGOUT}`)
      .then((response) => {
        console.log(response.data);
        window.alert("로그아웃하였습니다.");
        window.location.href = "/"; // 해당 주소로 보내버리는 window 함수
      })
      .catch((error) => {
        console.error("오류: ", error);
      });
  };

  const grade = ref(0);
  const getGrade = function(email){
    console.log("이메일")
    console.log(email)
    axios
      .get(`${Rest_API_USER}/${email}`)
      .then((response)=>{
        grade.value = response.data;
        console.log("등급")
        console.log(grade.value)
      })
      .catch((error)=>{
        console.error("오류: ",error)
      })
  }

  return {
    loginByCookie,
    LoginCookie,
    logout,
    OnlyUserInfo,
    getOnlyUserInfo,
    JustUserInfo,
    getJustUserInfo,
    grade,
    getGrade,
  };
}
  ,{
    persist:true
  });
