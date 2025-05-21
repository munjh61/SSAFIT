<template>
  <div class="card">
    <img :src="imgUrl" alt="exercise" />
    <div class="title"> {{ title }}</div>
  </div>
</template>

<script setup>
defineProps({
  imgUrl: String,
  title: String
})
</script>

<style scoped>
.card {
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
  font-size: 16px;
  text-align: left;
  color: #333;
  background-color: #fff;   
}
</style>