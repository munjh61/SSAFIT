<template>
    <div class="container">
        <HeaderBar />

        <section class="img-section">
            <div class="search-wrapper">
                <SearchBar />
            </div>
        </section>

        <section>
            <h2>추천 운동</h2>
            <RecommendedExercise />
            <a class="more-link" href="../views/RecExerciseAllView.vue">전체보기</a>

        </section>

        <section class="followers">
            <h2>팔로우 추천</h2>
            <RecommendedFollower />
            <a class="more-link" href="../views/RecFollowerAllView.vue">전체보기</a>
        </section>
    </div>
</template>

<script setup>
import HeaderBar from '@/components/HeaderBar.vue'
import SearchBar from '@/components/SearchBar.vue'
import RecommendedExercise from '@/components/RecommendedExercise.vue'
import RecommendedFollower from '@/components/RecommendedFollower.vue'
</script>

<style scoped>
a{
    color: black;
}
section{
    margin-bottom: 65px;
}
h2 {
  margin-bottom: 16px;
}
.more-link {
  display: block;
  margin-top: 8px;
  font-size: 14px;
  color: black;
  text-decoration: underline;
  cursor: pointer;
}
.container {
  max-width: 1200px;
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
}
.search-wrapper {
  position: absolute;
  bottom: 35px;
  left: 50%;
  transform: translateX(-50%);
}
</style>