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
                <div class="card" v-for="(ex, idx) in exercises" :key="idx">
                    <img :src="ex.img" :alt="ex.title" />
                    <div class="title">{{ ex.title }}</div>
                </div>
            </div>
        </section>
    </div>
</template>

<script setup>
import {ref, onMounted } from 'vue';
import axios from 'axios';
import HeaderBar from '@/components/HeaderBar.vue';
import SearchBar from '@/components/SearchBar.vue';

const exercises = ref([]);
const serverUrl = import.meta.env.VITE_API_BASE_URL


onMounted(async () => {
  try {
    const token = `Bearer ${sessionStorage.getItem('ssafit-login-token')}`
    const response = await axios.get(`${serverUrl}/api/board/recommend`, {
      headers: {
        Authorization: token
      },
      withCredentials: true
    })

    console.log('응답 데이터:', response.data);

    const boards = response.data.boards;
    const images = response.data.images;

    exercises.value = boards.map(board => {
      const imgList = images[board.boardId];
      const firstImg = imgList && imgList.length > 0 ? imgList[0].name : 'default.jpg';
      
      return {
        title: board.title,
        img: `/images/${firstImg}`
      };
    });
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
</style>