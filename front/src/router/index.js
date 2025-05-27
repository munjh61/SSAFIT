import { createRouter, createWebHistory } from "vue-router";
import MainView from "../views/MainView.vue";
import RecExerciseAllView from "../views/RecExerciseAllView.vue";
import RecFollowerAllView from "@/views/RecFollowerAllView.vue";
import MyPageView from "@/views/MyPageView.vue";
import CalenderView from "@/views/CalenderView.vue";
import GuildView from "@/views/GuildView.vue";
import BucketListView from "@/views/BucketListView.vue";
import JoonhoTestView from "@/views/JoonhoTestView.vue";
import SearchResultsView from "@/views/SearchResultsView.vue";
import RecommendedExercise from "@/components/RecommendedExercise.vue";

// const isAuth = true; //로그인 됨
const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: "/",
            name: "main",
            component: MainView,
            meta: { title: "SSAFIT", requiresAuth: false },
        },
        {
            path: "/exercise/all",
            name: "exercise-all",
            component: RecExerciseAllView,
            meta: { title: "SSAFIT", requiresAuth: false },
        },
        {
            path: "/follower/all",
            name: "follower-all",
            component: RecFollowerAllView,
            meta: { title: "SSAFIT", requiresAuth: false },
        },
        {
            path: "/myPage/:userId",
            name: "mypage",
            component: MyPageView,
            meta: { title: "SSAFIT 마이페이지", requiresAuth: false },
        },
        {
            path: "/bucketList",
            name: "bucketList",
            component: BucketListView,
            meta: { title: "SSAFIT 버킷리스트", requiresAuth: true },
        },
        {
            path: "/calender",
            name: "calender",
            component: CalenderView,
            meta: { title: "SSAFIT 일정", requiresAuth: true },
        },
        {
            path: "/guild",
            name: "guild",
            component: GuildView,
            meta: { title: "SSAFIT 모임", requiresAuth: false },
        },
        {
            path: "/search",
            name: "search-results",
            component: SearchResultsView,
            meta: { title: "검색", requiresAuth: false },
        },
        // {
        //   path:'/settings',
        //   name:'settings',
        //   component:SettingView
        // }
        //------------------junho
        {
            path: "/joonhoTestView",
            name: "joonhoTestView",
            component: JoonhoTestView,
        },
        {
            path: "/recommended",
            name: "recommended",
            component: RecommendedExercise,
            meta: { title: "SSAFIT", requiresAuth: false },
        },
    ],
});

router.beforeEach((to, from, next) => {
    if (to?.meta?.requiresAuth && sessionStorage.getItem("ssafit-login-token")){
      return next({ name: "main" });
    } 
    next();
});

router.afterEach((to) => {
    const defaultTitle = "SSAFIT";
    document.title = to.meta.title || defaultTitle;
});

export default router;
