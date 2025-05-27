<template>
  <div class="slider-container">
    <button class="scroll-btn left" @click="scrollLeft">‹</button>

    <div class="recommend-wrapper" ref="sliderRef">
      <BoardCard
        v-for="board in boards"
        :key="board.boardId"
        :img-url="board.img"
        :title="board.title"
        :board-id="board.boardId"
        @click="$emit('show-detail', board.boardId)"
      />
    </div>

    <button class="scroll-btn right" @click="scrollRight">›</button>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import BoardCard from './BoardCard.vue'

const boards = ref([])
const sliderRef = ref(null)
const serverUrl = import.meta.env.VITE_API_BASE_URL

const props = defineProps({
  imgUrl: String,
  title: String,
  boardId: Number
})

const scrollLeft = () => {
  sliderRef.value.scrollBy({ left: -320, behavior: 'smooth' })
}

const scrollRight = () => {
  sliderRef.value.scrollBy({ left: 320, behavior: 'smooth' })
}

onMounted(async () => {
  try {
    const response = await axios.get(`${serverUrl}/api/public/board`)
    const boardArray = []

    for (const [boardId, entry] of Object.entries(response.data)) {
      const board = entry.board
      const images = entry.images

      boardArray.push({
        boardId: board.boardId,
        title: board.title || '제목 없음',
        img: images && images.length > 0
          ? `${serverUrl}/images/${images[0].name}`
          : '/images/default.jpg'
      })
    }

    boards.value = boardArray
  } catch (err) {
    console.error('최신 게시글 불러오기 실패:', err)
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
