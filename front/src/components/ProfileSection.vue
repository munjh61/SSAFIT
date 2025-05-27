<template>
  <div class="top">
    <div class="profile-box">
      <img class="avatar" src="@/assets/images/profile.jpg" />
      <div class="info">
        <div class="title">
          <h2>{{ otherStore.otherName }}</h2>
          <button v-if="!isFollowed && canFollow" @click="add">팔로우</button>
          <button v-if="isFollowed && canFollow" @click="del">언팔로우</button>
        </div>
        <div class="status-container">
          <span v-if="canModify" @click="modify" :class="{ active: canModify}">⚙️</span>
          <div class="status">
            <input v-model="otherStore.otherMsg1" :readonly="!canModify" />
            <input v-model="otherStore.otherMsg2" :readonly="!canModify" />
          </div>
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

    <Follow v-if="showFollowModal" :mode="mode" :userId="userId" @close="closeFollowModal" @changeMode="changeMode" />
  </div>
</template>

<script setup>
import { onMounted, ref, computed } from 'vue'
import { useRoute } from 'vue-router'
import { useFollowStore } from '@/stores/follow'
import { useAuthStore } from '@/stores/auth'
import { useOtherStore } from '@/stores/otherUser'
import { useAuthNewStore } from '@/stores/authNew'
import Follow from '@/components/follow/Follow.vue'

const route = useRoute()
const userId = computed(() => route.params.userId)

const props = defineProps({
  stats: Object,
})

const followStore = useFollowStore()
const authStore = useAuthStore()
const otherStore = useOtherStore()
const authNewStore = useAuthNewStore()

// 모달 상태
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
const canModify = ref(false)

const followerCnt = computed(() => followStore.followerCnt)
const followingCnt = computed(() => followStore.followingCnt)

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

const modify = async () => {
  const success = await authNewStore.update(null, null, null, otherStore.otherMsg1, otherStore.otherMsg2)
  if (success) {
    alert('상태 메시지가 저장되었습니다.')
  } else {
    alert('저장에 실패했습니다.')
  }
}

onMounted(async () => {
  otherStore.you(userId.value)
  followStore.getFollowData(userId.value)

  if (!sessionStorage.getItem('ssafit-login-token')) {
    return isFollowed.value = false
  }

  if (!authStore.userId) {
    await authStore.me()
  }

  if (authStore.userId === userId.value) {
    canModify.value = true
    canFollow.value = false
  } else {
    canModify.value = false
    canFollow.value = true
    isFollowed.value = await followStore.isFollowed(userId.value)
  }

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

.status-container {
  display: flex;
  align-items: center;
}

.status {
  display: flex;
  flex-direction: column;
  max-width: 700px;
}

.active {
  cursor: pointer;
}

.status input {
  font-size: 18px;
  color: #666;
  width: 600px;
  border: 0;
}

.status input:focus-within {
  outline: none;
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