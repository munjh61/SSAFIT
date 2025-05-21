<template>
  <div class="search-container">
    <input
      type="text"
      v-model="keyword"
      @keydown.enter="search"
      placeholder=""
    />
    <button @click="search">
      🔍
    </button>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const keyword = ref('')

const search = () => {
  if (keyword.value.trim() === '') {
    alert('검색어를 입력해주세요')
    return
  }

  console.log('검색 실행:', keyword.value)
}
</script>

<style scoped>
.search-container {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 758px;
  height: 65px;
  margin: 30px auto;
  border: 1px solid #00aacc;
  border-radius: 999px;
  background-color: #fff;
  padding: 0 10px;
  box-sizing: border-box;
}

input {
  flex: 1;
  border: none;
  outline: none;
  font-size: 16px;
  background-color: transparent;
  padding: 0 16px;
}

button {
  background: none;
  border: none;
  font-size: 20px;
  color: #00aacc;
  cursor: pointer;
  padding: 0 10px;
}
</style>