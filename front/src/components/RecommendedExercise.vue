<template>
    <div class="slider-container">
        <button class="scroll-btn left" @click="scrollLeft">‹</button>

            <div class="recommend-wrapper" ref="sliderRef">
                <ExerciseCard
                v-for="exercise in exercises"
                :key="exercise.boardId"
                :img-url="exercise.img"
                :title="exercise.title"
                :boardId="exercise.boardId"
                @click="$emit('show-detail', exercise.boardId)"
                />
            </div>
        <button class="scroll-btn right" @click="scrollRight">›</button>
    </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import ExerciseCard from './ExerciseCard.vue'
import axios from 'axios'

const serverUrl = import.meta.env.VITE_API_BASE_URL
const exercises = ref([])

const sliderRef = ref(null)

const props = defineProps({
  imgUrl: String,
  title: String,
  boardId: Number
})

const scrollLeft = () => {
  sliderRef.value.scrollBy({
    left: -320, 
    behavior: 'smooth'
  })
}

const scrollRight = () => {
  sliderRef.value.scrollBy({
    left: 320,
    behavior: 'smooth'
  })
}

onMounted(async () => {
  try {
    const token = `Bearer ${sessionStorage.getItem('ssafit-login-token')}`
    console.log('토큰:', token)  // 토큰 확인

    const response = await axios.get(`${serverUrl}/api/board/recommend`, {
      headers: {
        Authorization: token
      },
      withCredentials: true
    })

    console.log('서버 응답 전체 데이터:', response)
    console.log('추천 운동 목록 데이터:', response.data)

    // Map<Long, Map<String, Object>> 형태의 데이터를 배열로 변환
    const exerciseArray = []
    for (const [boardId, entry] of Object.entries(response.data)) {
      const board = entry.board
      const images = entry.images

      exerciseArray.push({
        boardId: board.boardId,
        title: board.title,
        img: images && images.length > 0 
          ? `${serverUrl}/images/${images[0].name}`
          : '/images/default.jpg'
      })
    }
    exercises.value = exerciseArray
  } catch (err) {
    console.error('추천 운동 목록 불러오기 실패:', err)
    console.error('에러 상세 정보:', {
      message: err.message,
      response: err.response,
      status: err.response?.status,
      data: err.response?.data
    })
  }
})

</script>

<style scoped>
.slider-container {
  position: relative;
}

.scroll-btn {
  position: absolute;
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

.left {
  left: 8px;
  top: 90px;
}
.right {
  right: 8px;
  top: 90px;
}

.slider-container:hover .scroll-btn {
  opacity: 1;
}
.recommend-wrapper::-webkit-scrollbar {
  display: none;
}

.recommend-wrapper {
  display: flex;
  overflow-x: auto;
  overflow-y: hidden;
  gap: 10px;
  scroll-behavior: smooth;
  flex: 1;
}
</style>