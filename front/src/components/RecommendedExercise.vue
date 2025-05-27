<template>
    <div class="slider-container">
        <button class="scroll-btn left" @click="scrollLeft">‹</button>

            <div class="recommend-wrapper" ref="sliderRef">
                <ExerciseCard
                v-for="(exercise, index) in exercises"
                :key="index"
                :img-url="exercise.img"
                :title="exercise.title"
                :boardId="exercise.boardId"
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
    const response = await axios.get(`${serverUrl}/api/board/recommend`, {
      headers: {
        Authorization: token
      },
      withCredentials: true
    })

    console.log('추천 운동 목록:', response.data)

    // 백엔드에서 받은 데이터 형식에 맞게 매핑
    exercises.value = response.data.map(board => {
      return {
        boardId: board.boardId,
        img: board.images && board.images.length > 0 
          ? `/images/${board.images[0].name}` 
          : '/images/default.jpg',
        title: board.title
      }
    })
  } catch (err) {
    console.error('추천 운동 목록 불러오기 실패:', err)
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