<template>
    <HeaderBar />
    <div class="container">
    <h2>✨나의 버킷리스트✨</h2>
        <BucketItem v-for="item in bucketList" 
        :key="item.id" 
        :date="item.date"
        :imgUrl="item.img"
        :title="item.title"
        :done="item.done" />
    </div>
</template>

<script setup>
import HeaderBar from '@/components/HeaderBar.vue'
import BucketItem from '@/components/BucketItem.vue';

const bucketList = [{
    id: 1,
    date: '2025-05-09',
    img: new URL('@/assets/images/exercise1.jpg', import.meta.url).href,
    title: '암벽 등반',
    done: false
},
{
    id: 2,
    date: '2025-05-10',
    img: new URL('@/assets/images/exercise6.jpg', import.meta.url).href,
    title: '스키',
    done: false
  }
]
</script>

<style scoped>
.container h2{
    margin-top: 40px;
    margin-bottom: 50px;
}
.container{
  max-width: 1600px;
  margin: 0 auto;
  padding: 0 16px;
  display: flex;
  justify-content: center;
  align-content: center;
  align-items: center;
  flex-direction: column;
}

</style>