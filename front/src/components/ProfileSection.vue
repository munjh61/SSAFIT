<template>
  <div class="top">
    <div class="profile-box">
      <img class="avatar" src="@/assets/images/profile.jpg" />
      <div class="info">
        <h2>문준호 <RouterLink to="/settings" class="settings">⚙️</RouterLink></h2>
        <div class="status">
          <p>배드민턴 제일 좋아함.</p>
          <p>꾸준히 운동 즐기기</p>
        </div>
      </div>
    </div>
    <div class="stats">
      <div class="outer">
        <div class="num">
          <span class="big">{{ stats.posts }}</span>
          <span>기록</span>
        </div>
      </div>
      <div class="outer">
        <div class="follower">
          <span class="big">{{ stats.followers }}</span>
          <span>팔로워</span>
        </div>
      </div>
      <div class="outer">
        <div class="following">
          <span class="big">{{ stats.following }}</span>
          <span>팔로잉</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
defineProps({
  stats: Object
})
</script>

<style scoped>
.outer{
  display: flex;
  justify-content: center;
  align-items: center;
}
.top{
  display: flex;
  justify-content: center;
  align-content: center;
  gap: 140px;
  margin-bottom: 40px;
}
.num, .follower, .following {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-content: center;
  align-items: center;
  gap: 8px;
}
.big{
  font-size: x-large;
  font-weight: bold;
}
.profile-box {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.settings{
  background: none;
  border: none;
  font-size: 24px;
  text-decoration: none;
}
.avatar {
  width: 100px;
  height: 100px;
  border-radius: 50%;
}
.info {
  display: flex;
  flex-direction: column;
  margin-left: 40px;
  gap: 15px;
}
.info h2, .info p{
  margin: 0;
}
.status {
  font-size: 18px;
  color: #666;
}
.outer span {
  margin-right: 20px;
  /* font-weight: bold; */
}
.stats{
  display: flex;
  justify-content: flex-start;
  gap: 25px;
}
.record-btn {
  border: 1px solid #000;
  background: white;
  padding: 8px 16px;
  border-radius: 4px;
  cursor: pointer;
}
</style>