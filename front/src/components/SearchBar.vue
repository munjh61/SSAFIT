<template>
  <div class="search-container">
    <div class="search-type">
      <select v-model="searchType">
        <option value="title">제목</option>
        <option value="content">내용</option>
        <option value="tag">태그</option>
      </select>
    </div>
    <input
      type="text"
      v-model="keyword"
      @keydown.enter="handleSearch"
      :placeholder="getPlaceholder"
    />
    <button @click="handleSearch">
      🔍
    </button>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import { useSearchStore } from '@/stores/searchStore'

const router = useRouter()
const searchStore = useSearchStore()
const keyword = ref('')
const searchType = ref('title')

const getPlaceholder = computed(() => {
  const types = {
    title: '제목으로 검색',
    content: '내용으로 검색',
    tag: '태그로 검색'
  }
  return types[searchType.value] || '검색어를 입력하세요'
})

const handleSearch = async () => {
  if (keyword.value.trim() === '') {
    alert('검색어를 입력해주세요')
    return
  }

  try {
    await searchStore.searchBoards({
      keyword: keyword.value,
      searchType: searchType.value
    })
    router.push({
      name: 'search-results',
      query: { 
        keyword: keyword.value,
        field: searchType.value
      }
    })
  } catch (error) {
    console.error('검색 중 오류 발생:', error)
  }
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

.search-type {
  padding: 0 10px;
}

select {
  padding: 8px;
  border: 1px solid #e0e0e0;
  border-radius: 4px;
  background-color: white;
  font-size: 14px;
  color: #333;
  cursor: pointer;
}

select:focus {
  outline: none;
  border-color: #00aacc;
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

button:hover {
  opacity: 0.8;
}
</style>