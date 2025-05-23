import { createRouter, createWebHistory } from 'vue-router'
import MainView from '../views/MainView.vue'
import RecExerciseAllView from '../views/RecExerciseAllView.vue'
import RecFollowerAllView from '@/views/RecFollowerAllView.vue'
import MyPageView from '@/views/MyPageView.vue'
import CalenderView from '@/views/CalenderView.vue'
import GuildView from '@/views/GuildView.vue'
import BucketListView from '@/views/BucketListView.vue'
import JoonhoTestView from '@/views/JoonhoTestView.vue'

// const isAuth = true; //로그인 됨
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'main',
      component: MainView,
    },
    {
      path:'/exercise/all', 
      name:'exercise-all', 
      component: RecExerciseAllView
    },
    {
      path:'/follower/all', 
      name:'follower-all', 
      component: RecFollowerAllView
    },
    {
      path:'/myPage',
      name:'mypage',
      component: MyPageView
    },
    {
      path:'/bucketList',
      name:'bucketlist',
      component: BucketListView
    },
    {
      path:'/calender',
      name:'calender',
      component:CalenderView
    },
    {
      path:'/guild',
      name:'guild',
      component:GuildView
    },
    //------------------junho
    {
      path:'/joonhoTestView',
      name:'joonhoTestView',
      component:JoonhoTestView
    },
  ],
})

export default router
