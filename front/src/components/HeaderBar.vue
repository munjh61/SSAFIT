<template>
  <header class="header">
    <RouterLink to="/" class="logo">SSAFIT</RouterLink>
    <nav class="nav">
      <RouterLink v-if="!store.isLoggedIn" :to="'/myPage/user01'" class="mypage">마이페이지</RouterLink>
      <RouterLink v-if="store.isLoggedIn" :to="`/myPage/${store.userId}`" class="mypage">마이페이지</RouterLink>
      <RouterLink to="/bucketList" class="bucketlist">버킷리스트</RouterLink>
      <!-- <RouterLink to="/calender" class="calender">캘린더</RouterLink> -->
      <RouterLink to="/guild" class="guild">모임</RouterLink>
    </nav>
    <div class="auth">
      <div class="isLoggedIn-wrapper" v-if="store.isLoggedIn">
        <div class="isLoggedIn">
          <div class="profile">
            <img src="@/assets/images/profile.jpg" alt="profile" />
            <div class="info">
              <div class="userName">{{ store.userName }}</div>
              <div class="userId">{{ store.userId }}</div>
            </div>
          </div>
        </div>
        <div class="hide">
          <button @click="store.logout" class="auth-button">
            <img src="/src/assets/images/logout.png" class="auth-icon">
            <span style="color: #FA5252;">로그아웃</span>
          </button>
          <button @click="toggleUserPropertyModal" class="auth-button">
            <img src="/src/assets/images/usersetting.png" class="auth-icon">
            <span>회원 정보 변경</span>
          </button>
          <button @click="toggleQuitModal" class="auth-button">
            <img src="/src/assets/images/deleteuser.png" class="auth-icon">
            <span>탈퇴</span>
          </button> 
        </div>
      </div>
      <div v-if="!store.isLoggedIn">
        <button @click="toggleAuthModal" class="auth-button">
          <span style="color: #20C997;">로그인</span>
          <img src="/src/assets/images/login.png" class="auth-icon">
        </button>
      </div>
    </div>
    <Auth v-if="showAuth" @close="toggleAuthModal"/>
    <UserProperty v-if="showUserProperty" @close="toggleUserPropertyModal"/>
    <Quit v-if="showQuit" @close="toggleQuitModal"/>
  </header>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { useAuthStore } from '@/stores/auth'
import Auth from '@/components/auth/Auth.vue'
import UserProperty from '@/components/auth/UserProperty.vue'
import Quit from '@/components/auth/Quit.vue'
const store = useAuthStore()
// 로그인, 회원가입, 아이디찾기, 비밀번호재설정
const showAuth = ref(false)
const toggleAuthModal = function () {
  showAuth.value = !showAuth.value
}
// 로그인 후, 회원 정보 변경
const showUserProperty = ref(false)
const toggleUserPropertyModal = function(){
  showUserProperty.value = !showUserProperty.value
}

const showQuit = ref(false)
const toggleQuitModal = function (){
  showQuit.value = !showQuit.value
}

onMounted(()=>{
  useAuthStore().me()
})

</script>

<style scoped>
.header {
  width: 100%;
  margin: 0 auto;
  padding: 20px 40px;
  display: flex;
  justify-content: space-evenly;
  align-items: center;
  border-bottom: 1px solid #eee;
  background-color: white;
  min-height: 80px;
  position: relative;
  z-index: 10;
}


/* 로고 */
.logo {
  font-size: 24px;
  font-weight: bold;
  text-decoration: none;
  color: #1BA9B5;
}

/* 네비게이션 */
.nav {
  display: flex;
  gap: 30px;
}

.nav a {
  position: relative;
  text-decoration: none;
  color: #333;
  font-weight: 500;
  font-size: 16px;
  padding: 4px 0;
}

.nav a::after {
  content: "";
  position: absolute;
  bottom: -4px;
  left: 0;
  width: 0%;
  height: 2px;
  background-color: #1BA9B5;
  transition: width 0.3s ease-in-out;
}

.nav a:hover::after {
  width: 100%;
}

/* 로그인 영역 */
.auth {
  position: relative;
  display: flex;
  align-items: center;
}

/* 로그인 상태 */
.isLoggedIn {
  display: flex;
  align-items: center;
  cursor: pointer;
}

.profile {
  width: 150px;
  display: flex;
  justify-content: space-evenly;
  align-items: center;
}

.profile img {
  width: 36px;
  height: 36px;
  border-radius: 50%;
  margin-right: 10px;
}

.profile .info {
  display: flex;
  flex-direction: column;
  gap: 2px;
  line-height: 1.2;
}

.userName {
  font-size: 14px;
  font-weight: 600;
}

.userId {
  font-size: 12px;
  color: #888;
}

/* 드롭다운 메뉴 */
.isLoggedIn-wrapper {
  position: relative;
}

.hide {
  position: absolute;
  top: 100%;
  right: 0;
  display: none;
  flex-direction: column;
  gap: 8px;
  background-color: white;
  padding: 10px;
  z-index: 10;
  width: 150px;
}

.isLoggedIn-wrapper:hover .hide {
  display: flex;
}

/* 로그인 버튼 */
.auth-button {
  display: flex;
  align-items: center;
  gap: 6px;
  background: none;
  border: none;
  cursor: pointer;
  font-size: 14px;
}

.auth-button:hover{
  font-weight: 500;
}

.auth-icon {
  width: 20px;
  height: 20px;
}
</style>