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
import { ref } from 'vue'

defineProps({
  imgUrl: String,
  title: String
})

const isBucketmarked = ref(false)

const addBucketmark = () => {
  isBucketmarked.value = !isBucketmarked.value
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