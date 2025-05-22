<template>
    <header class="header">
        <RouterLink to="/" class="logo">SSAFIT</RouterLink>
            <nav class="nav">
              <RouterLink to="/myPage" class="mypage">마이페이지</RouterLink>
              <RouterLink to="/bucketList" class="bucketlist">버킷리스트</RouterLink>
              <RouterLink to="/calender" class="calender">캘린더</RouterLink>
              <RouterLink to="/guild" class="guild">모임</RouterLink>
            </nav>
        <div class="profile">
          <img src="@/assets/images/profile.jpg" alt="profile" />
              <div class="info">
                  <div class="userName">문준호</div>
                  <div class="userId">@munhealth</div>
              </div>
        </div>
    </header>
</template>

<script setup>
</script>

<style scoped>
.header {
  width: 100%;
  max-width: 100%;
  position: relative;
  z-index: 10;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 30px 10px;
  /* box-shadow: 0px 2px 8px #D9D9D9; */
}
.nav a {
  margin: 0 18px;
  text-decoration: none;
  color: #000000;
  position: relative;
  /* font-weight: bold; */
}
.nav a::after {
  content: "";
  position: absolute;
  bottom: -6px;
  left: 0;
  width: 0%;
  height: 4px;
  background-color: #1BA9B5;
  transition: width 0.3s ease-in-out;
}
.nav a:hover::after {
  width: 100%;
}
/* 프로필 정보 */
.profile {
  display: flex;
  align-items: center;
  padding-right: 100px;
}
.profile img {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  /* img랑 info 사이 간격 */
  margin-right: 15px;
}
.profile .info {
  display: flex; 
  flex-direction: column;
  justify-content: center;
  line-height: 1.2;
  /* info끼리 간격 */
  gap: 4px; 
}
.userName {
  font-weight: bold;
  font-size: 17px;
}
.userId {
  font-size: 14px;
  color: #666;
}
/* 로고 */
.logo{
    font-weight: bold;
    font-size: x-large;
    color: #000000;
    text-decoration: none;
    padding-left: 100px;
}
</style>