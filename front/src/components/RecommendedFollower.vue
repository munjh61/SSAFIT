<template>
  <div class="slider-container">
    <button class="scroll-btn left" @click="scrollLeft">‹</button>

    <div class="scroll-wrapper" ref="sliderRef">
      <div v-for="user in followers" :key="user.followingId" class="card">
        <div class="userName">{{ user.followingName }}</div>
        <div class="userId">{{ user.followingId }}</div>
        <button 
          @click="toggleFollow(user.followingId)"
          :class="{ 'following': isFollowing[user.followingId] }"
        >
          {{ isFollowing[user.followingId] ? '언팔로우' : '팔로우' }}
        </button>
      </div>
    </div>

    <button class="scroll-btn right" @click="scrollRight">›</button>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const serverUrl = import.meta.env.VITE_API_BASE_URL
const followers = ref([])
const sliderRef = ref(null)
const isFollowing = ref({})

const scrollLeft = () => {
  sliderRef.value?.scrollBy({
    left: -180,
    behavior: 'smooth',
  })
}

const scrollRight = () => {
  sliderRef.value?.scrollBy({
    left: 180,
    behavior: 'smooth',
  })
}

const checkFollowStatus = async (userId) => {
  try {
    const token = `Bearer ${sessionStorage.getItem('ssafit-login-token')}`
    const response = await axios.get(`${serverUrl}/api/follow/check/${userId}`, {
      headers: {
        Authorization: token
      },
      withCredentials: true
    })
    isFollowing.value[userId] = response.data
  } catch (error) {
    console.error('팔로우 상태 확인 실패', error)
  }
}

const toggleFollow = async (targetUserId) => {
  try {
    const token = `Bearer ${sessionStorage.getItem('ssafit-login-token')}`
    if (!isFollowing.value[targetUserId]) {
      // 팔로우
      await axios.post(`${serverUrl}/api/follow/${targetUserId}`, {}, {
        headers: {
          Authorization: token
        },
        withCredentials: true
      })
      alert(`${targetUserId}님을 팔로우했어요!`)
    } else {
      // 언팔로우
      await axios.delete(`${serverUrl}/api/follow/${targetUserId}`, {
        headers: {
          Authorization: token
        },
        withCredentials: true
      })
      alert(`${targetUserId}님을 언팔로우했어요!`)
    }
    // 상태 토글
    isFollowing.value[targetUserId] = !isFollowing.value[targetUserId]
  } catch (error) {
    console.error('팔로우/언팔로우 실패', error)
    alert('작업에 실패했습니다')
  }
}

onMounted(async () => {
  try {
    const token = `Bearer ${sessionStorage.getItem('ssafit-login-token')}`
    const response = await axios.get(`${serverUrl}/api/follow/recommend`, {
      headers: {
        Authorization: token
      },
      withCredentials: true
    })
    followers.value = response.data
    
    // 각 사용자의 팔로우 상태 확인
    for (const user of followers.value) {
      await checkFollowStatus(user.followingId)
    }
  } catch (error) {
    console.error('팔로우 추천 목록 불러오기 실패', error)
  }
})

</script>

<style scoped>
button{
  color: #1BA9B5;
}
.slider-container {
  position: relative;
  
}

.scroll-btn.left {
  left: 10px;
  top: 50%;
}
.scroll-btn.right {
  right: 10px;
  top: 50%;
}

.scroll-btn {
  position: absolute;
  transform: translateY(-50%);
  background-color: white;
  border: 1px solid #ccc;
  border-radius: 50%;
  width: 36px;
  height: 36px;
  font-size: 18px;
  cursor: pointer;
  opacity: 0;
  transition: opacity 0.3s ease-in-out;
  z-index: 2;
}

.scroll-wrapper {
  display: flex;
  overflow-x: auto;
  overflow-y: hidden;
  scroll-behavior: smooth;
  gap: 16px;
  padding: 10px 0;
  height: 180px;

  scrollbar-width: none;   
  -ms-overflow-style: none; 
}

.scroll-wrapper::-webkit-scrollbar {
  display: none;                  
}

.slider-container:hover .scroll-btn {
  opacity: 1;
}

/* 카드 스타일 */
.card {
  flex: 0 0 auto;
  border: 1px solid #ddd;
  padding: 16px;
  width: 140px;
  height: 140px;
  text-align: center;
  border-radius: 8px;
  background-color: #fff;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.userName {
  font-weight: bold;
  font-size: 1rem;
  margin: 0;
}

.userId {
  font-size: 0.9rem;
  color: #666;
  margin: 4px 0;
}

button {
  margin: 0;
  padding: 6px 12px;
  border: 1px solid #1BA9B5;
  background-color: white;
  border-radius: 4px;
  cursor: pointer;
  transition: all 0.2s ease;
  font-size: 0.9rem;
}

button.following {
  background-color: #1BA9B5;
  color: white;
}

button:hover {
  background-color: #f5f5f5;
}

button.following:hover {
  background-color: #ff4444;
  border-color: #ff4444;
}
</style>
