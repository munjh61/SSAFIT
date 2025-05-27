<template>
  <HeaderBar />
    <div class="container">
        <section class="img-section">
            <div class="overlay"></div>
            <div class="search-wrapper">
                <SearchBar />
            </div>
        </section>

        <section class="exercise-list">
            <h2>추천 운동</h2>
            <div class="grid">
                <div class="card" v-for="(ex, idx) in exercises" :key="idx" @click="showBoardDetail(ex.boardId)">
                    <img :src="ex.img" :alt="ex.title" />
                    <div class="title">{{ ex.title }}</div>
                </div>
            </div>
        </section>

        <!-- BoardDetail 모달 -->
        <BoardDetail 
            v-if="showDetail" 
            :boardId="selectedBoardId"
            @close="showDetail = false"
        />
    </div>
</template>

<script setup>
import {ref, onMounted } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';
import HeaderBar from '@/components/HeaderBar.vue';
import SearchBar from '@/components/SearchBar.vue';
import BoardDetail from '@/components/BoardDetail.vue';

const router = useRouter();
const exercises = ref([]);
const serverUrl = import.meta.env.VITE_API_BASE_URL
const showDetail = ref(false);
const selectedBoardId = ref(null);

const showBoardDetail = (boardId) => {
  selectedBoardId.value = boardId;
  showDetail.value = true;
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

    // Map<Long, Map<String, Object>> 형태의 데이터를 배열로 변환
    const exerciseArray = []
    for (const [boardId, entry] of Object.entries(response.data)) {
      const board = entry.board
      const images = entry.images

      exerciseArray.push({
        boardId: board.boardId,
        title: board.title,
        img: images && images.length > 0 
          ? `/images/${images[0].name}`
          : '/images/default.jpg'
      })
    }
    exercises.value = exerciseArray
  } catch (error) {
    console.error('추천 운동 목록 불러오기 실패', error);
  }
})
</script>

<style scoped>
.container{
  max-width: 1600px;
  margin: 0 auto;
  padding: 0 16px;
}
.img-section {
  position: relative;
  height: 553px;
  background-image: url('@/assets/images/mainImage.jpg');
  background-size: cover;
  background-position: center;
  display: flex;
  justify-content: center;
  align-items: center;
  overflow: hidden;
  box-shadow: 0 2px 8px #D9D9D9;
}
.overlay{
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(255, 255, 255, 0.2);
  z-index: 1;
}
.search-wrapper {
  position: absolute;
  bottom: 35px;
  left: 50%;
  z-index: 2;
  transform: translateX(-50%);
}
.grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 24px;
  margin-top: 40px;
  gap: 50px 30px;
}

.card {
  background-color: #fff;
  border-radius: 5px;
  overflow: hidden;
  text-align: left;
  cursor: pointer;
  transition: transform 0.2s ease-in-out;
}

.card:hover {
  transform: translateY(-5px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.card img {
  width: 100%;
  border-radius: 5px;
  height: 300px;
  object-fit: cover;
}

.card .title {
  padding: 12px 0;
  font-weight: bold;
  font-size: large;
}

.exercise-list h2{
  margin-top: 60px;
}
</style>