<template>
  <div class="top">
    <div class="profile-box">
      <img class="avatar" src="@/assets/images/profile.jpg" />
      <div class="info">
        <div class="title">
          <h2>문준호</h2>
          <button v-if="!isFollowed && canFollow" @click="add">팔로우</button>
          <button v-if="isFollowed && canFollow" @click="del">언팔로우</button>
        </div>
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
        <p class="big" @click="openFollowModal('follower')">{{ followerCnt }}</p>
        <span @click="openFollowModal('follower')">팔로워</span>
      </div>
      <div class="stat">
        <p class="big" @click="openFollowModal('following')">{{ followingCnt }}</p>
        <span @click="openFollowModal('following')">팔로잉</span>
      </div>
    </div>
  </div>

  <Follow v-if="showFollowModal" :mode="mode" :userId="userId" @close="closeFollowModal" @changeMode="changeMode" />
</template>

<script setup>
import { onMounted, ref, computed } from 'vue'
import { useRoute } from 'vue-router'
import { useFollowStore } from '@/stores/follow'
import { useAuthStore } from '@/stores/auth'
import Follow from '@/components/follow/Follow.vue'

const route = useRoute()
const userId = computed(() => route.params.userId)

const props = defineProps({
  stats: Object,
})

const followStore = useFollowStore()
const authStore = useAuthStore()

// 모달 관련
const showFollowModal = ref(false)
const mode = ref('follower')
const openFollowModal = (selectedMode) => {
  mode.value = selectedMode
  showFollowModal.value = true
}
const closeFollowModal = () => {
  showFollowModal.value = false
}
const changeMode = (q) => {
  mode.value = q
}

// 상태값
const isFollowed = ref(false)
const canFollow = ref(false)

// store의 followerCnt, followingCnt를 직접 computed로 연결
const followerCnt = computed(() => followStore.followerCnt)
const followingCnt = computed(() => followStore.followingCnt)

// 팔로우/언팔로우 액션
const add = async () => {
  const success = await followStore.addFollow(userId.value)
  if (success) {
    isFollowed.value = true
    await followStore.getFollowData(userId.value)
  }
}

const del = async () => {
  const success = await followStore.deleteFollow(userId.value)
  if (success) {
    isFollowed.value = false
    await followStore.getFollowData(userId.value)
  }
}

onMounted(async () => {
  if (!authStore.userId) {
    console.log(1)
    await authStore.me()
  }

  if (!sessionStorage.getItem('ssafit-login-token')) {
    console.log(2)
    isFollowed.value = false
    return
  }

  if (authStore.userId === userId.value) {
    console.log(3)
    canFollow.value = false
  } else {
    console.log(4)
    console.log(`loginUser ${authStore.userId}`)
    console.log(`targetUser ${userId.value}`)
    canFollow.value = true
    isFollowed.value = await followStore.isFollowed(userId.value)
    const msg = isFollowed.value ? "이미" : "아직"
    console.log(msg)
  }

  await followStore.getFollowData(userId.value)
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

.title {
  min-width: 100px;
  display: flex;
  justify-content: space-between;
}

.title h2,
.title p {
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

.stats p:hover {
  color: #4a90e2;
  cursor: pointer;
}

.stats span:hover {
  color: #4a90e2;
  cursor: pointer;
}

.title button {
  padding: 6px 14px;
  font-size: 14px;
  font-weight: 600;
  border-radius: 20px;
  border: none;
  cursor: pointer;
  transition: background-color 0.3s, color 0.3s;
  background-color: #4a90e2;
  color: white;
}

.title button:hover {
  background-color: #357ab8;
}
</style>