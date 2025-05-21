<template>
    <header class="header">
        <div class="logo">SSAFIT</div>
            <nav class="nav">
              <a href="#">마이페이지</a>
              <a href="#">버킷리스트</a>
              <a href="#">캘린더</a>
              <a href="#">모임</a>
            </nav>
        <div class="profile">
        <img src="@/assets/images/profile.jpg" alt="profile" />
            <div>
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
  position: relative;
  z-index: 10;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px 60px;
  box-shadow: 0px 2px 8px #D9D9D9;
}
.nav a {
  margin: 0 10px;
  text-decoration: none;
  color: #000000;
  /* font-weight: bold; */
}
.profile {
  display: flex;
  align-items: center;
}
.profile img {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  margin-right: 8px;
  /* border: 1px solid #000000; */
}
.logo{
    font-weight: bold;
    font-size: large;
}
</style>