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
import HeaderBar from '@/components/HeaderBar.vue';
import SearchBar from '@/components/SearchBar.vue';

const exercises = [
    { img: new URL('../assets/images/exercise1.jpg', import.meta.url).href, title: '암벽등반' },
    { img: new URL('../assets/images/exercise2.jpg', import.meta.url).href, title: '필라테스' },
    { img: new URL('../assets/images/exercise3.jpg', import.meta.url).href, title: '크로스핏' },
    { img: new URL('../assets/images/exercise4.jpg', import.meta.url).href, title: '요가' },
    { img: new URL('../assets/images/exercise5.jpg', import.meta.url).href, title: '사이클' },
    { img: new URL('../assets/images/exercise6.jpg', import.meta.url).href, title: '스키' },
]

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