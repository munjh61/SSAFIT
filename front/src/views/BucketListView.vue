<template>
  <HeaderBar />
  <div class="container">
    <h2>✨나의 버킷리스트✨</h2>
    <div class="graph">
      <progress :value="bucketStats.rate" max="100"></progress>
      <p>{{ bucketStats.rate }}%</p>
    </div>
    <div class="stats">
        <div class="num">
          <p>나의 버킷리스트: {{ bucketStats.total }}</p>
          <p>미달성: {{ bucketStats.doing }}</p>
          <p>달성: {{ bucketStats.done }}</p>
        </div>
      </div>
      <BucketItem v-for="item in bucketList" 
      :key="item.bucketId" 
      :date="item.doneDate"
      :imgUrl="item.imgUrl"
      :title="item.title"
      :done="item.done"
      :bucketId="item.bucketId"
      @delete="deleteBucket"
      @mark="markDone"/>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import HeaderBar from '@/components/HeaderBar.vue';
import BucketItem from '@/components/BucketItem.vue';

const serverUrl = import.meta.env.VITE_API_BASE_URL

const bucketList = ref([])
const bucketStats = ref({
total: 0,
done: 0,
doing: 0,
rate: 0
})

//화면 출력, 통계 출력력
onMounted(async () => {
  try{
      const token = `Bearer ${sessionStorage.getItem('ssafit-login-token')}`
      //버킷리스트 목록 불러오기
      const response = await axios.get(`${serverUrl}/api/bucket/list`,{
          headers:{
              Authorization: token
          },
          withCredentials: true
      })

      const{ bucketList: rawList, bucketImgs }= response.data

      bucketList.value = rawList.map(bucket => {
          const imgs = bucketImgs[bucket.bucketId] || []
          const title = imgs.length > 0 ? imgs[0].title : null
          return {
              ...bucket,
              imgUrl: imgs.length > 0 ? `/images/${imgs[0].name}` : null, title
          }
      })
      // 통계 불러오기
      const statsRes = await axios.get(`${serverUrl}/api/bucket/stats`, {
          headers: { Authorization: token },
          withCredentials: true
      })

      bucketStats.value = statsRes.data

  }catch(err){
      console.error('버킷리스트 불러오기 실패: ', err)
  }
})

//삭제
const deleteBucket = async (bucketId) => {
try {
    const token = `Bearer ${sessionStorage.getItem('ssafit-login-token')}`
    await axios.delete(`${serverUrl}/api/bucket/${bucketId}`, {
        headers: {
            Authorization: token
          },
          withCredentials: true
      })
      
      // 삭제 후 리스트에서 제거
      bucketList.value = bucketList.value.filter(b => b.bucketId !== bucketId)
      alert('삭제 완료!')
  } catch (err) {
  console.error('삭제 실패:', err)
  alert('삭제 중 오류가 발생했습니다.')
}
}

//완료처리
const markDone = async (bucketId) => {
try {
  const token = `Bearer ${sessionStorage.getItem('ssafit-login-token')}`
  await axios.put(`${serverUrl}/api/bucket/${bucketId}`, {}, {
    headers: {
      Authorization: token
    },
    withCredentials: true
  })

  const item = bucketList.value.find(b => b.bucketId === bucketId)
  if (item) item.done = 2

  alert('버킷리스트 달성을 축하합니다!')
} catch (err) {
  console.error('완료 상태 변경 실패', err)
  alert('변경 실패')
}
}


</script>

<style scoped>
.container h2{
  margin-top: 40px;
  margin-bottom: 30px;
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

.num{
display: flex;
flex-direction: row;
gap: 20px;
justify-content: center;
align-items: center;
}
.graph{
display: flex;
flex-direction: row;
align-items: center;
}
.graph p{
margin-left: 10px;
}
.stats {
margin-top: 1px;
margin-bottom: 50px;
padding: 12px;
width: 300px;
background: #f7f7f7;
border-radius: 8px;
align-content: center;
justify-content: center;
align-items: center;
}
.stats p {
margin: 4px 0;
}
</style>