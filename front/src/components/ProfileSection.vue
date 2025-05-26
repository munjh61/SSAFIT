<template>
  <div class="top">
    <div class="profile-box">
      <img class="avatar" src="@/assets/images/profile.jpg" />
      <div class="info">
        <h2>문준호 <!--<RouterLink to="/settings" class="settings">⚙️</RouterLink>--></h2>
        <div class="status">
          <p>배드민턴 제일 좋아함.</p>
          <p>꾸준히 운동 즐기기</p>
        </div>
      </div>
    </div>
    <div class="stats">
      <div class="stat">
        <p class="big">{{ stats.posts }}</p>
        <span>기록</span>
      </div>
      <div class="stat">
        <p class="big">{{ store.followerCnt }}</p>
        <span @click="openFollowModal('follower')">팔로워</span>
      </div>
      <div class="stat">
        <p class="big">{{ store.followingCnt }}</p>
        <span @click="openFollowModal('following')">팔로잉</span>
      </div>
    </div>
  </div>  
  <!-- 로그인/라우팅 연결되면 userId 직접 전달되도록 수정 예정 -->
  <Follow v-if="showFollowModal" :mode="mode" :userId="'user01'" @close="closeFollowModal" @changeMode="changeMode" />
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { useFollowStore } from '@/stores/follow'
import Follow from '@/components/follow/Follow.vue'
const store = useFollowStore()
const props = defineProps({
  stats: Object,
})

const showFollowModal = ref(false)
const mode = ref('follower')

const openFollowModal = (selectedMode) => {
  mode.value = selectedMode
  showFollowModal.value = true
}

const closeFollowModal = () => {
  showFollowModal.value = false
}

const changeMode = function (q) {
  mode.value = q
}

const followerCnt = ref(0)
const followingCnt = ref(0)

onMounted(async () => {
    // 로그인/라우팅 연결되면 userId 직접 전달되도록 수정 예정
    await store.getFollowData('user01')
    followerCnt.value = store.followerCnt
    followingCnt.value = store.followingCnt
})
</script>

<style scoped>
.top {
  display: flex;
  justify-content: center;
  align-content: center;
  gap: 140px;
  margin-bottom: 40px;
}

.big {
  font-size: x-large;
  font-weight: bold;
}

.profile-box {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.settings {
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

.info h2,
.info p {
  margin: 0;
}

.status {
  font-size: 18px;
  color: #666;
}

.stats {
  display: flex;
  justify-content: space-evenly;

}

.stat {
  min-width: 100px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.stats span:hover{
  color: #4a90e2;
  cursor: pointer;
}
</style>