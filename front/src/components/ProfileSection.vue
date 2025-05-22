<template>
  <div class="profile-box">
    <img class="avatar" src="@/assets/images/profile.jpg" />
    <div class="info">
      <h2>문준호 <RouterLink to="/settings" class="settings">⚙️</RouterLink></h2>
      <p class="status">비타민D 제일 좋아함 꾸준히 운동 즐기기</p>
      <div class="stats">
        <span>기록 {{ stats.posts }}</span>
        <span>팔로워 {{ stats.followers }}</span>
        <span>팔로잉 {{ stats.following }}</span>
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
.profile-box {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 32px;
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
  flex: 1;
  margin-left: 24px;
}
.status {
  font-size: 14px;
  color: #666;
}
.stats span {
  margin-right: 20px;
  font-weight: bold;
}
.record-btn {
  border: 1px solid #000;
  background: white;
  padding: 8px 16px;
  border-radius: 4px;
  cursor: pointer;
}
</style>