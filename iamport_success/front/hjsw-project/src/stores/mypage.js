import { ref } from "vue";
import { defineStore } from "pinia";
import router from "@/router";
import axios from "axios";

const REST_KAKAO_API = `http://localhost:8080/api/kakaopay`;

export const useMyPageStore = defineStore("mypage", () => {
    const orderList = ref([])

    const getOrderList = function(email){
      axios
      .get(`${REST_KAKAO_API}/list/${email}`)
        .then((response) =>{
          orderList.value = response.data;
        })
        .catch((error) => {
          console.error("오류: ", error)
        })
    };

    const order = ref({})

    const getOrder = function(id){
      axios
      .get(`${REST_KAKAO_API}/detail/${id}`)
        .then((response) =>{
          order.value = response.data;
          console.log(order)
        })
        .catch((error) => {
          console.error("오류: ", error)
        })
    };


    return {
        orderList, getOrderList,order, getOrder
    
    };
},{
  persist:true
});