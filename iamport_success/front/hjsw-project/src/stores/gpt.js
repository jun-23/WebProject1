import { ref } from "vue";
import { defineStore } from "pinia";
import router from "@/router";
import axios from "axios";

const REST_API_GPT = `http://localhost:8080/api/gpt`;

export const useGptStore = defineStore("gpt",() =>{

    const response = ref("")
    const sendMessage = function(p1,p2,p3){
        axios.get(`${REST_API_GPT}/chat?p1=${p1.value}&p2=${p2.value}&p3=${p3.value}`)
        .then((res) => {
            console.log("호잇")
            console.log(res.data)
            response.value = res.data;
        })
        .catch((err) => {
            console.error("오류발생" , err)
        })

    }
    
      return{
     sendMessage,response
      }
})