import { createRouter, createWebHistory } from "vue-router";
import WorkOutClassView from "../views/WorkOutClassView.vue";
import Login from "@/components/Login.vue";
import Redirect from "@/components/Redirect.vue";
import VideoList from "@/components/VideoList.vue";
import VideoDetail from "@/components/VideoDetail.vue";
import ShopView from "@/views/ShopView.vue";
import CommunityView from "@/views/CommunityView.vue";
import ShopList from "@/components/ShopList.vue";
import ShopDetail from "@/components/ShopDetail.vue";
import MyPageView from "@/views/MyPageView.vue";
import MyPageOrderList from "@/components/MyPageOrderList.vue";
import MyPageOrderDetail from "@/components/MyPageOrderDetail.vue";
import CommunityPostList from "@/components/Community/CommunityPostList.vue";
import CommunityPostDetail from "@/components/Community/CommunityPostDetail.vue";
import CommunityPostCreate from "@/components/Community/CommunityPostCreate.vue";
import CommunityPostUpdate from "@/components/Community/CommunityPostUpdate.vue";
import MyPageCancelOrderDetail from "@/components/MyPageCancelOrderDetail.vue";
import Test from "@/components/Test.vue";
import MyPagePostByMe from "@/components/MyPagePostByMe.vue";
import DesciptTier from "@/components/DesciptTier.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/tier",
      name: "tier",
      component: DesciptTier,
    },
    {
      path: "/test",
      name: "test",
      component: Test,
    },
    {
      path: "/",
      name: "WorkOutClassView",
      component: WorkOutClassView,
    },
    {
      path: "/login",
      name: "login",
      component: Login,
      //메타 필드를 사용하여 로그인 페이지라는 것을 표시
      meta: { noHeader: true },
    },
    {
      path: "/mypage",
      name: "mypage",
      component: MyPageView,
      children: [
        {
          path: "",
          name: "mypageOrderList",
          component: MyPageOrderList,
        },
        {
          path: ":id",
          name: "orderdetail",
          component: MyPageOrderDetail,
        },
        {
          path: "cancel/:id",
          name: "cancelOrderdetail",
          component: MyPageCancelOrderDetail,
        },
        {
          path: "post",
          name: "myPost",
          component: MyPagePostByMe,
        },
      ],
    },
    {
      path: "/redirect",
      name: "redirect",
      component: Redirect,
    },
    {
      path: "/videoList/:tier/:part",
      name: "videoList",
      component: VideoList,
    },
    {
      path: "/video/:id",
      name: "videoDetail",
      component: VideoDetail,
    },
    {
      path: "/shop",
      name: "shop",
      component: ShopView,
      children: [
        {
          path: "",
          name: "shopList",
          component: ShopList,
        },
        {
          path: ":id",
          name: "shopDetail",
          component: ShopDetail,
        },
      ],
    },
    {
      path: "/community",
      name: "community",
      component: CommunityView,
      children: [
        {
          path: "",
          name: "communityPostList",
          component: CommunityPostList,
        },
        {
          path: ":id",
          name: "communityPostDetail",
          component: CommunityPostDetail,
        },
        {
          path: ":id/update",
          name: "updatePost",
          component: CommunityPostUpdate,
        },
      ],
    },
    {
      path: "/postCreate",
      name: "postCreate",
      component: CommunityPostCreate,
    },
    {
      path: `/:pathMatch(.*)*`,
      redirect: '/',
    }
  ],
});

export default router;
