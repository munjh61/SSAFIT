<template>
    <HeaderBar />
    <div class="container">
    <h2>✨나의 버킷리스트✨</h2>
        <BucketItem v-for="item in bucketList" 
        :key="item.bucketId" 
        :date="item.date"
        :imgUrl="item.imgUrl"
        :title="item.title"
        :done="item.done" />
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import HeaderBar from '@/components/HeaderBar.vue';
import BucketItem from '@/components/BucketItem.vue';

const bucketList = ref([])

onMounted(async () => {
    try{
        const response = await axios.get('/api/bucket/list')
        console.log('응답 데이터:', response.data)
        const{ bucketList: rawList, bucketImgs }= response.data

        bucketList.value = rawList.map(bucket => {
            const imgs = bucketImgs[bucket.bucketId] || []
            return {
                ...bucket,
                imgUrl: imgs.length > 0 ? `/images/${imgs[0].name}` : null
            }
        })
    }catch(err){
        console.error('버킷리스트 불러오기 실패: ', err)
    }
})

// const bucketList = [{
//     id: 1,
//     date: '2025-05-09',
//     img: new URL('@/assets/images/exercise1.jpg', import.meta.url).href,
//     title: '암벽 등반',
//     done: false
// },
// {
//     id: 2,
//     date: '2025-05-10',
//     img: new URL('@/assets/images/exercise6.jpg', import.meta.url).href,
//     title: '스키',
//     done: false
//   }
// ]
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