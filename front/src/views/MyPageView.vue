<template>
  <HeaderBar />
    <div class="container">
        <div class="userProfile">
          <ProfileSection :stats="userStats" />
        </div>
        <div class="userRecords">
          <RecordGrid :records="userRecords" />
        </div>
    </div>
</template>

<script setup>
import HeaderBar from '@/components/HeaderBar.vue'
import ProfileSection from '@/components/ProfileSection.vue'
import RecordGrid from '@/components/RecordGrid.vue'

const userStats = {
  posts: 10,
  followers: 50,
  following: 100
}

const userRecords = [
   { id: 1, img: new URL('@/assets/images/my1.jpg', import.meta.url).href, caption: '오전 조깅 완료' },
  { id: 2, img: new URL('@/assets/images/my2.jpg', import.meta.url).href, caption: '요가 처음 도전!' },
  { id: 3, img: new URL('@/assets/images/my3.jpg', import.meta.url).href, caption: '요가 처음 도전!' },
  { id: 4, img: new URL('@/assets/images/my4.jpg', import.meta.url).href, caption: '요가 처음 도전!' },
  { id: 5, img: new URL('@/assets/images/my5.jpg', import.meta.url).href, caption: '요가 처음 도전!' },
  { id: 6, img: new URL('@/assets/images/my6.jpg', import.meta.url).href, caption: '요가 처음 도전!' },
  { id: 7, img: new URL('@/assets/images/my7.jpg', import.meta.url).href, caption: '요가 처음 도전!' },
  { id: 8, img: new URL('@/assets/images/my8.jpg', import.meta.url).href, caption: '요가 처음 도전!' },
  { id: 9, img: new URL('@/assets/images/my9.jpg', import.meta.url).href, caption: '요가 처음 도전!' },
]


</script>

<style scoped>
.container{
  max-width: 1600px;
  margin: 0 auto;
  padding: 0 16px;
}
.userProfile{
  display: flex;
  justify-content: center;
  padding-top: 60px;
}
.userRecords{
  padding-top: 100px;
  display: flex;
  justify-content: center;
  
}


</style>