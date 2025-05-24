<template>
  <div class="slider-container">
    <button class="scroll-btn left" @click="scrollLeft">‹</button>

    <div class="scroll-wrapper" ref="sliderRef">
      <div v-for="user in followers" :key="user.userId" class="card">
        <img :src="user.imgUrl" alt="user" />
        <div class="userName">{{ user.name }}</div>
        <!-- <div class="info">{{ user.intro }}</div> -->
        <button>Follow</button>
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

// onMounted(async () => {
//   try {
//     //유저 추천 컨트롤러 만들어야 됨됨
//     const response = await axios.get(`${serverUrl}/api/public/user/recommend`)
//     console.log('팔로우 추천 목록:', response.data)
//     followers.value = response.data
//   } catch (error) {
//     console.error('팔로우 추천 목록 불러오기 실패', error)
//   }
// })
</script>

<style scoped>
button{
  color: #10217D;
}
.slider-container {
  position: relative;
}

.scroll-btn.left {
  left: 8px;
  top: 140px;
}
.scroll-btn.right {
  right: 8px;
  top: 140px;
}

.scroll-btn {
  position: absolute;
  top: 50%;
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
  gap: 22px;
  padding: 20px 0;
  flex: 1;
  height: 240px;

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
  padding: 20px;
  width: 150px;
  text-align: center;
  border-radius: 8px;
  background-color: #fff;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 8px;
}

img {
  width: 80px;
  height: 80px;
  border-radius: 50%;
}

.userName {
  font-weight: bold;
  margin-top: 10px;
}

.info {
  font-size: 12px;
  color: #666;
}

button {
  margin-top: 8px;
  padding: 6px 12px;
  border: 1px solid #000;
  background-color: white;
  border-radius: 4px;
  cursor: pointer;
}
</style>
