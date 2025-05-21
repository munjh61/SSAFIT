<template>
    <div class="slider-container">
        <button class="scroll-btn left" @click="scrollLeft">‹</button>

            <div class="recommend-wrapper" ref="sliderRef">
                <ExerciseCard
                v-for="(exercise, index) in exercises"
                :key="index"
                :imgUrl="exercise.img"
                :title="exercise.title"
                />
            </div>
        <button class="scroll-btn right" @click="scrollRight">›</button>
    </div>
</template>

<script setup>
import { ref } from 'vue'
import ExerciseCard from './ExerciseCard.vue'

const sliderRef = ref(null)

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

const exercises = [
    { img: '/src/assets/images/exercise1.jpg', title: '암벽등반' },
    { img: '/src/assets/images/exercise2.jpg', title: '필라테스' },
    { img: '/src/assets/images/exercise3.jpg', title: '크로스핏' },
    { img: '/src/assets/images/exercise4.jpg', title: '요가' },
    { img: '/src/assets/images/exercise5.jpg', title: '사이클' },
    { img: '/src/assets/images/exercise6.jpg', title: '스키' },
]

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