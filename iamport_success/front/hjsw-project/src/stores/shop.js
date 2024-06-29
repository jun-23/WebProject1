import { ref } from "vue";
import { defineStore } from "pinia";
import router from "@/router";
import axios from "axios";

const REST_KAKAO_API_BUY = `http://localhost:8080/api/kakaopay/buy`;
const REST_KAKAO_API = `http://localhost:8080/api/kakaopay`;
const REST_API_SHOP = `http://localhost:8080/api/shop`;

export const useShopStore = defineStore("shop", () => {
  const shopList = ref([]);

  const getShopList = function(){
    axios
      .get(`${REST_API_SHOP}/`)
      .then((response) =>{
        shopList.value = response.data;
      })
      .catch((error) => {
        console.error("오류: ", error)
      })
  };

  const getShopListByCategory = function(category) {
    axios
      .get(`${REST_API_SHOP}/category/${category}`)
      .then((response) => {
        shopList.value = response.data;
        console.log(router.currentRoute.value);
        router.push(router.currentRoute.value);
      })
      .catch((error) => {
        console.error("오류: ", error);
      });
  };

  const product = ref({});

  const getShopDetail = function (id) {
    console.log(id);
    axios.get(`${REST_API_SHOP}/${id}`).then((response) => {
      product.value = response.data;
    });
  };

  const ProductPay = function(request) {
    try {
      console.log(request);
      const response = axios.post(`${REST_KAKAO_API_BUY}`, request);
    } catch (error) {
      console.error("오류 발생:", error);
    }
  };

  return {
    shopList, 
    getShopList, 
    getShopListByCategory, 
    product, 
    getShopDetail, 
    ProductPay
  };
}, {
  persist: true
});
