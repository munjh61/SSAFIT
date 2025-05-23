<template>
    <div class="bucket-card">
        <div v-if="done" class="overlay">
            ✔️
        </div>
        <div class="card-header">
            <span class="date">{{ date }}</span>
            <button class="delete-btn" @click="deleteItem">❌</button>
        </div>
            
            <img :src="imgUrl" class="exercise-img" />

            <div class="card-bottom">
                <div class="title">{{ title }}</div>
                <button class="done-btn" @click="mark">완료</button>
            </div>
            
    </div>

</template>

<script setup>
import{ref} from 'vue'

const done = ref(false)

defineProps({
    date: String,
    imgUrl: String,
    title: String,
    done: Boolean
})

const deleteItem = () => {
  alert('삭제 되었습니다.')
}

const mark = () => {
  done.value = !done.vlaue
  alert('버킷리스트 달성을 축하드립니다')
}
</script>

<style scoped>
.bucket-card {
  position: relative;
  width: 50%;
  height:100%;
  background: white;
  padding: 16px;
  border-radius: 16px;
  margin-bottom: 32px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
  overflow: hidden;
}
.overlay{
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(255, 255, 255, 0.6);
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 80px;
  color: rgba(0, 0, 0, 0.4);
  z-index: 2;
}

.card-header {
  font-size: 14px;
  color: #aaa;
  margin-bottom: 8px;
  display: flex;
  justify-content: space-between;
}

.card-bottom{
    display: flex;
    justify-content: space-between;
}

.exercise-img {
  width: 100%;
  height: auto;
  border-radius: 12px;
  margin-bottom: 12px;
}

.title {
  font-weight: bold;
  font-size: 18px;
  margin-bottom: 8px;
}

.done-btn {
  background: black;
  color: white;
  padding: 6px 14px;
  border: none;
  border-radius: 6px;
  cursor: pointer;
}

.delete-btn {
  background: none;
  border: none;
  cursor: pointer;
  font-size: 12px;
}
</style>