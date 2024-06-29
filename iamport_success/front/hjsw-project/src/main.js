import { createApp } from "vue";
import { createPinia } from "pinia";
import axios from "axios";
import "./assets/reset.css";
import Flicking from "@egjs/vue3-flicking";
import "@egjs/vue3-flicking/dist/flicking.css";
import piniaPersist from 'pinia-plugin-persistedstate';

axios.defaults.baseURL = "http://localhost:8080";
axios.defaults.withCredentials = true;
const pinia = createPinia();
pinia.use(piniaPersist);

import App from "./App.vue";
import router from "./router";

import Antd from "ant-design-vue";
import 'ant-design-vue/dist/reset.css';


const app = createApp(App);
app.config.globalProperties.axios = axios;

// Flicking
app.component("Flicking", Flicking);

app.use(pinia);
app.use(router);
app.use(Antd);

app.mount("#app");
