<template>
  <HeaderBar />
  <div class="container">
        <section class="img-section">
            <div class="overlay"></div>
            <div class="search-wrapper">
                <SearchBar />
            </div>
        </section>

        <section v-if="!isLoggedIn">
          <h2>🆕최신 게시글</h2>
          <RecentBoard @show-detail="showBoardDetail" />
        </section>

        <section class="e" v-if="isLoggedIn">
            <h2>🏃‍♂️‍➡️추천 운동</h2>
            <RecommendedExercise @show-detail="showBoardDetail" />
            <RouterLink class="more-link" to="/exercise/all">전체보기</RouterLink>
        </section>

        <section class="followers" v-if="isLoggedIn">
            <h2>👤팔로우 추천</h2>
            <RecommendedFollower />
            <RouterLink class="more-link" to="/follower/all">전체보기</RouterLink>
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
import { onMounted, ref } from 'vue'
import HeaderBar from '@/components/HeaderBar.vue'
import SearchBar from '@/components/SearchBar.vue'
import RecommendedExercise from '@/components/RecommendedExercise.vue'
import RecommendedFollower from '@/components/RecommendedFollower.vue'
import BoardDetail from '@/components/BoardDetail.vue'
import { useAuthStore } from '@/stores/auth'
import RecentBoard from '@/components/RecentBoard.vue'

const store = useAuthStore()
const isLoggedIn = ref(false)

const showDetail = ref(false)
const selectedBoardId = ref(null)

const showBoardDetail = (boardId) => {
  selectedBoardId.value = boardId
  showDetail.value = true
}

onMounted(async ()=>{
  await store.me()
  isLoggedIn.value = store.isLoggedIn
})
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
.container{
  max-width: 1600px;
  margin: 0 auto;
  padding: 0 16px;
}
.more-link {
  text-align: right;
  display: block;
  margin-top: 8px;
  font-size: 14px;
  color: black;
  text-decoration: underline;
  cursor: pointer;
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
</style>