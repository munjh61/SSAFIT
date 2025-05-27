<template>
  <div class="search-results">
    <HeaderBar />
    <div class="container">
      <div class="search-info">
        <h2>"{{ keyword }}" 검색 결과</h2>
        <p class="search-type">검색 유형: {{ getSearchTypeText }}</p>
      </div>
      
      <div v-if="searchStore.isLoading" class="loading">
        검색 중...
      </div>
      
      <div v-else-if="searchStore.error" class="error">
        {{ searchStore.error }}
      </div>
      
      <div v-else-if="searchStore.searchResults.length === 0" class="no-results">
        검색 결과가 없습니다.
      </div>
      
      <div v-else class="results-list">
        <div v-for="board in searchStore.searchResults" :key="board.boardId" class="board-card">
          <div class="card-wrapper" @click="openDetail(board.boardId)">
            <div class="card-image" v-if="board.images && board.images.length > 0">
              <img :src="`/images/${board.images[0].name}`" :alt="board.title">
            </div>
            <div class="card-image" v-else>
              <div class="no-image">이미지 없음</div>
            </div>
            <div class="card-content">
              <div class="top">
                <h3>{{ board.title }}</h3>
                <p class="content">{{ board.content }}</p>
                <div class="tags" v-if="board.tag">
                  <span v-for="tag in (board.tag || '').split(',')" :key="tag" class="tag">
                    #{{ tag.trim() }}
                  </span>
                </div>
              </div>
              <div class="board-info">
                <span class="author">{{ board.userId }}</span>
                <span class="date">{{ formatDate(board.regDate) }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <BoardDetail
      v-if="showDetail"
      :boardId="selectedBoardId"
      @close="showDetail = false"
      @deleted="handlePostDeleted"
    />
  </div>
</template>

<script setup>
import { onMounted, computed, ref } from 'vue'
import { useRoute } from 'vue-router'
import { useSearchStore } from '@/stores/searchStore'
import HeaderBar from '@/components/HeaderBar.vue'
import BoardDetail from '@/components/BoardDetail.vue'

const route = useRoute()
const searchStore = useSearchStore()

const showDetail = ref(false)
const selectedBoardId = ref(null)

const keyword = computed(() => route.query.keyword || '')
const searchType = computed(() => route.query.field || 'title')

const getSearchTypeText = computed(() => {
  const types = {
    title: '제목',
    content: '내용',
    tag: '태그'
  }
  return types[searchType.value] || '제목'
})

const openDetail = (boardId) => {
  selectedBoardId.value = boardId
  showDetail.value = true
}

const handlePostDeleted = () => {
  showDetail.value = false
  // 게시글이 삭제되면 검색 결과를 다시 불러옵니다
  if (keyword.value) {
    searchStore.searchBoards({
      keyword: keyword.value,
      field: searchType.value
    })
  }
}

onMounted(async () => {
  if (keyword.value) {
    await searchStore.searchBoards({
      keyword: keyword.value,
      field: searchType.value
    })
  }
})

const formatDate = (dateString) => {
  if (!dateString) return ''
  const date = new Date(dateString)
  return date.toLocaleDateString('ko-KR', {
    year: 'numeric',
    month: 'long',
    day: 'numeric'
  })
}
</script>

<style scoped>
.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.search-info {
  margin-top: 30px;
  margin-bottom: 40px;
}

h2 {
  margin-bottom: 8px;
  font-size: 24px;
  color: #333;
}

.search-type {
  color: #666;
  font-size: 14px;
}

.loading, .error, .no-results {
  text-align: center;
  padding: 40px;
  color: #666;
}

.results-list{
  display: flex;
  flex-direction: column;
  gap: 24px;
  align-items: center;
}

/* 보드 카드 크기 */
.board-card {
  width: 80%;
  height: 250px;
  border: 1px solid #eee;
  border-radius: 8px;
  overflow: hidden;
  transition: transform 0.2s, box-shadow 0.2s;
  background-color: white;
  cursor: pointer;
}

.board-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.card-wrapper {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: row;
  text-decoration: none;
  color: inherit;
}

.card-image {
  width: 250px;
  height: 100%;
  overflow: hidden;
  background-color: #f5f5f5;
}

.card-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.no-image {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #999;
  font-size: 14px;
}
.top{
  padding-bottom: 25px;
}

.card-content {
  flex: 1;
  min-height: 60%;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  padding: 30px;
}

.card-content h3 {
  margin: 0 0 12px;
  font-size: 18px;
  color: #333;
}

.content {
  color: #666;
  margin-bottom: 12px;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
  font-size: 14px;
}

.tags {
  margin-bottom: 12px;
}

.tag {
  display: inline-block;
  background-color: #f0f0f0;
  padding: 4px 8px;
  border-radius: 4px;
  margin-right: 8px;
  margin-bottom: 8px;
  font-size: 12px;
  color: #666;
}

.board-info {
  display: flex;
  justify-content: space-between;
  font-size: 12px;
  color: #999;
  margin-top: auto;
}

.author {
  font-weight: 500;
}
</style> 