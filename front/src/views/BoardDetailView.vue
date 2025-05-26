<template>
  <div class="board-detail">
    <HeaderBar />
    <div class="container">
      <div v-if="isLoading" class="loading">
        로딩 중...
      </div>
      <div v-else-if="error" class="error">
        {{ error }}
      </div>
      <div v-else-if="board" class="board-content">
        <div class="board-header">
          <h1>{{ board.title }}</h1>
          <div class="board-info">
            <div class="user-info">
              <img src="@/assets/images/profile.jpg" alt="프로필" class="profile-img">
              <span class="author">{{ board.userId }}</span>
            </div>
            <span class="date">{{ formatDate(board.regDate) }}</span>
          </div>
          <div class="tags" v-if="board.tag">
            <span v-for="tag in (board.tag || '').split(',')" :key="tag" class="tag">
              #{{ tag.trim() }}
            </span>
          </div>
        </div>
        
        <div class="board-body">
          <div v-if="board.images && board.images.length > 0" class="image-gallery">
            <img 
              v-for="image in board.images" 
              :key="image.imageId" 
              :src="`/images/${image.name}`" 
              :alt="image.name"
              class="board-image"
            >
          </div>
          <p class="content">{{ board.content }}</p>
        </div>

        <div class="board-actions">
          <div class="action-buttons" v-if="isAuthor">
            <button @click="handleEdit" class="btn btn-edit">수정</button>
            <button @click="handleDelete" class="btn btn-delete">삭제</button>
          </div>
          <button @click="goBack" class="btn btn-back">목록으로</button>
        </div>
      </div>
      <div v-else class="not-found">
        게시글을 찾을 수 없습니다.
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import axios from 'axios'
import HeaderBar from '@/components/HeaderBar.vue'

const route = useRoute()
const router = useRouter()
const authStore = useAuthStore()
const serverUrl = import.meta.env.VITE_API_BASE_URL

const board = ref(null)
const isLoading = ref(true)
const error = ref(null)

const isAuthor = computed(() => {
  return board.value && authStore.userId === board.value.userId
})

const fetchBoard = async () => {
  const boardId = route.params.id
  isLoading.value = true
  error.value = null
  
  try {
    console.log('게시글 상세 조회 요청:', `${serverUrl}/api/public/board/${boardId}`)
    const response = await axios.get(`${serverUrl}/api/public/board/${boardId}`)
    console.log('게시글 상세 조회 응답:', response.data)
    board.value = response.data
  } catch (err) {
    console.error('게시글 조회 중 오류 발생:', err)
    error.value = '게시글을 불러오는 중 오류가 발생했습니다.'
  } finally {
    isLoading.value = false
  }
}

const formatDate = (dateString) => {
  if (!dateString) return ''
  const date = new Date(dateString)
  return date.toLocaleDateString('ko-KR', {
    year: 'numeric',
    month: 'long',
    day: 'numeric',
    hour: '2-digit',
    minute: '2-digit'
  })
}

const handleEdit = () => {
  // 수정 기능 구현
  router.push({ name: 'board-edit', params: { id: board.value.boardId } })
}

const handleDelete = async () => {
  if (!confirm('정말로 이 게시글을 삭제하시겠습니까?')) return

  try {
    await axios.delete(`${serverUrl}/api/board/${board.value.boardId}`, {
      headers: {
        Authorization: `Bearer ${sessionStorage.getItem('ssafit-login-token')}`
      }
    })
    router.push({ name: 'search-results' })
  } catch (err) {
    console.error('게시글 삭제 중 오류 발생:', err)
    alert('게시글 삭제 중 오류가 발생했습니다.')
  }
}

const goBack = () => {
  router.back()
}

onMounted(() => {
  fetchBoard()
})
</script>

<style scoped>
.container {
  max-width: 1000px;
  margin: 0 auto;
  padding: 20px;
}

.loading, .error, .not-found {
  text-align: center;
  padding: 40px;
  color: #666;
}

.board-content {
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  padding: 24px;
}

.board-header {
  margin-bottom: 24px;
  border-bottom: 1px solid #eee;
  padding-bottom: 16px;
}

h1 {
  font-size: 24px;
  color: #333;
  margin-bottom: 16px;
}

.board-info {
  display: flex;
  justify-content: space-between;
  align-items: center;
  color: #666;
  font-size: 14px;
  margin-bottom: 12px;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 8px;
}

.profile-img {
  width: 32px;
  height: 32px;
  border-radius: 50%;
  object-fit: cover;
}

.author {
  font-weight: 500;
}

.tags {
  margin-top: 12px;
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

.board-body {
  line-height: 1.6;
  color: #333;
}

.image-gallery {
  margin-bottom: 20px;
}

.board-image {
  max-width: 100%;
  border-radius: 4px;
  margin-bottom: 16px;
}

.content {
  white-space: pre-line;
}

.board-actions {
  display: flex;
  justify-content: space-between;
  margin-top: 24px;
  padding-top: 24px;
  border-top: 1px solid #eee;
}

.action-buttons {
  display: flex;
  gap: 8px;
}

.btn {
  padding: 8px 16px;
  border-radius: 4px;
  border: none;
  font-size: 14px;
  cursor: pointer;
  transition: background-color 0.2s;
}

.btn-edit {
  background-color: #4a90e2;
  color: white;
}

.btn-edit:hover {
  background-color: #357abd;
}

.btn-delete {
  background-color: #e25555;
  color: white;
}

.btn-delete:hover {
  background-color: #c94444;
}

.btn-back {
  background-color: #f0f0f0;
  color: #666;
}

.btn-back:hover {
  background-color: #e0e0e0;
}
</style> 