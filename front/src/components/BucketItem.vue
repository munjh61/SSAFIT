<template>
    <div class="bucket-card">
        <div v-if="localDone === 2" class="overlay">
            ✔️
        </div>
        <div class="card-header">
            <span class="date">{{ date }}</span>
            <button class="delete-btn" @click="deleteItem">❌</button>
        </div>
            <img :src="imgUrl" class="exercise-img" />

            <div class="card-bottom">
                <div class="title">{{ title }}</div>
                <button class="done-btn" @click="mark" :disabled="localDone === 2">완료</button>
            </div>
    </div>

</template>

<script setup>
import{ref, watch} from 'vue'

const props = defineProps({
    date: String,
    imgUrl: String,
    title: String,
    done: Number,
    bucketId: Number
})

const localDone = ref(props.done)

const emit = defineEmits(['delete', 'mark']) // 삭제 요청

const deleteItem = () => {
  if(confirm('정말 삭제하시겠습니까?')){
    emit('delete', props.bucketId)
  }
}

watch(() => props.done, (newVal) => {
  localDone.value = newVal
})

const mark = () => {
  emit('mark', props.bucketId)
}
</script>

<style scoped>
.bucket-card {
  position: relative;
  width: 40%;
  height: 80%;
  background: white;
  padding: 16px;
  border-radius: 16px;
  margin-bottom: 50px;
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