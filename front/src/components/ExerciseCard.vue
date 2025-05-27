<template>
  <div class="card">
      <button class="bucket-btn" :class="{active: isBucketmarked}" @click="addBucketmark">
        {{ isBucketmarked ? '★' : '☆' }}
      </button>
    <img :src="imgUrl" alt="exercise" />
    <div class="title"> {{ title }}</div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const props = defineProps({
  imgUrl: String,
  title: String,
  boardId: Number
})

const isBucketmarked = ref(false)
const serverUrl = import.meta.env.VITE_API_BASE_URL

const token = `Bearer ${sessionStorage.getItem('ssafit-login-token')}`


const checkBucketStatus = async () => {
  try {
    const response = await axios.get(`${serverUrl}/api/board/check/${props.boardId}`, {
      headers: {
        Authorization: token
      },
      withCredentials: true
    })
    isBucketmarked.value = response.data
  } catch (err) {
    console.error('버킷리스트 상태 확인 실패:', err)
  }
}

onMounted(() => {
  checkBucketStatus()
})

const addBucketmark = async() => {
  try{
    if(!isBucketmarked.value) {
      await axios.post(`${serverUrl}/api/bucket`, {
        boardId: props.boardId,
        done: 1
      }, {
        headers: {
          Authorization: token
        },
        withCredentials: true
      })
  
      isBucketmarked.value = true
      alert('버킷리스트에 등록되었습니다')
    }else{
      await axios.delete(`${serverUrl}/api/bucket/main/${props.boardId}`, {
        headers: { Authorization: token },
        withCredentials: true
      })
      isBucketmarked.value = false
      alert('버킷리스트에서 삭제되었습니다.')
    }
  }catch (err) {
    console.error('처리 실패', err)
    alert('처리에 실패했습니다.')
  }

}
</script>

<style scoped>
.card {
  position: relative;
  flex: 0 0 auto;
  width: 300px;
  height: 250px;
  margin: 10px;
  flex-direction: column;
  overflow: visible;
}
img {
  width: 100%;
  height: 200px;
  object-fit: cover;
  border-radius: 5px;
}
.title {
  padding: 10px;
  font-weight: bold;
  font-size: 18px;
  text-align: left;
  color: #333;
  background-color: #fff;   
}
.bucket-btn {
  position: absolute;
  top: 10px;
  right: 10px;
  background: none;
  border: none;
  font-size: 24px;
  cursor: pointer;
  color: #ccc;
  transition: color 0.3s ease;
  z-index: 10;
}
.bucket-btn:hover {
  color: gold;
}

.bucket-btn:focus {
  outline: none;
}

.bucket-btn.active {
  color: gold;
}
</style>