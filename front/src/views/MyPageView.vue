<template>
  <HeaderBar />
    <div class="container">
        <div class="userProfile">
          <ProfileSection :stats="userStats" />
        </div>
        
        <RecordGrid :records="userRecords" />
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
  { id: 1, img: '/images/my1.jpg', caption: '오전 조깅 완료' },
  { id: 2, img: '/images/my2.jpg', caption: '요가 처음 도전!' },
  { id: 3, img: '/images/my3.jpg', caption: '요가 처음 도전!' },
  { id: 4, img: '/images/my4.jpg', caption: '요가 처음 도전!' },
  { id: 5, img: '/images/my5.jpg', caption: '요가 처음 도전!' },
  { id: 6, img: '/images/my6.jpg', caption: '요가 처음 도전!' },
  { id: 7, img: '/images/my7.jpg', caption: '요가 처음 도전!' },
  { id: 8, img: '/images/my8.jpg', caption: '요가 처음 도전!' },
  { id: 9, img: '/images/my9.jpg', caption: '요가 처음 도전!' },
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
  padding-top: 50px;
}


</style>