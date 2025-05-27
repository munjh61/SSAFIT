<template>
  <div class="board-card">
    <img :src="imgUrl" alt="게시글 이미지" class="card-img" />
    <p class="card-title">{{ title }}</p>
  </div>
</template>

<script setup>
defineProps({
  imgUrl: String,
  title: String,
  boardId: Number
})
</script>

<style scoped>
.board-card {
  position: relative;
  flex: 0 0 auto;
  width: 300px;
  height: 250px;
  margin: 10px;
  flex-direction: column;
  overflow: visible;
}

.card-img {
  width: 100%;
  height: 200px;
  object-fit: cover;
  border-radius: 5px;
}

.card-title {
  padding: 10px;
  font-weight: bold;
  font-size: 18px;
  text-align: left;
  color: #333;
  background-color: #fff;
}
</style>
