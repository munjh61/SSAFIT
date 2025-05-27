<template>
  <div class="overlay" @click.self="emit('close')">
    <div class="modal-card">
      <div class="left">
        <img :src="imageUrl" alt="게시글 이미지" />
      </div>
      <div class="right">
        <div class="top">
          <div class="header">
            <div class="title-row">
              <h3>{{ board?.title }}</h3>
              <span class="author" @click="goToUserPage(board?.userId)" role="button">{{ board?.userId }}</span>
            </div>
            <p class="date">{{ formatDate(board?.regDate) }}</p>
          </div>
          <p class="content">{{ board?.content }}</p>
          <div class="actions">
            <!-- <span class="bucket-count">{{ bucketCount }}</span> -->
            <button class="action-btn" @click="toggleBucket">
              <span :class="{ 'filled-star': isInBucket }">{{ isInBucket ? '★' : '☆' }}</span>
              {{ isInBucket ? '버킷 삭제' : '버킷 추가' }}
            </button>
            <button class="action-btn" v-if="board?.userId === store.userId" @click="showEdit = true">✏️ 글 수정</button>
            <button class="action-btn" v-if="board?.userId === store.userId" @click="deletePost">❌ 글 삭제</button>
          </div>
          <div class="divider"></div>
        </div>

        <div class="comments">
          <div v-for="comment in comments" :key="comment.commentId" class="comment">
            <div class="comment-header">
              <strong>{{ comment.userId }}</strong>
              <div class="time">{{ formatDate(comment.regDate) }}</div>
            </div>
            
            <div class="comment-body">
              <div v-if="editCommentId !== comment.commentId" class="comment-content">
                {{ comment.content }}
                <div v-if="comment.userId === store.userId" class="comment-actions">
                  <button @click="startEdit(comment)">✏️</button>
                  <button @click="deleteComment(comment.commentId)">❌</button>
                </div>
              </div>
              <div v-else class="edit-form">
                <input v-model="editContent" placeholder="댓글을 입력하세요" />
                <div class="edit-buttons">
                  <button class="edit-btn save" @click="editComment(comment.commentId)">저장</button>
                  <button class="edit-btn cancel" @click="cancelEdit">취소</button>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div class="comment-form">
          <input v-model="newComment" placeholder="댓글을 입력하세요" />
          <button class="submit-btn" @click="submitComment">등록</button>
        </div>
      </div>
    </div>
  </div>
  <PostWriteBoard
    v-if="showEdit"
    :board="board"
    :editMode="true"
    @close="showEdit = false"
    @updated="handleUpdate"
  />
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import PostWriteBoard from './PostWriteBoard.vue'
import { useAuthStore } from '@/stores/auth'
import { useRouter } from 'vue-router'
import axios from 'axios'

const router = useRouter()
const store = useAuthStore()

const props = defineProps({ boardId: Number })
const emit = defineEmits(['close', 'created', 'deleted'])

const serverUrl = import.meta.env.VITE_API_BASE_URL
const token = `Bearer ${sessionStorage.getItem('ssafit-login-token')}`

store.userId

const board = ref(null)
const imageUrl = ref('')
const comments = ref([])
const newComment = ref('')
const bucketCount = ref(0)
const showEdit = ref(false)
const isInBucket = ref(false)

const editCommentId = ref(null)
const editContent = ref('')

const startEdit = (comment) => {
  editCommentId.value = comment.commentId
  editContent.value = comment.content
}

const cancelEdit = () => {
  editCommentId.value = null
  editContent.value = ''
}

const formatDate = (date) => {
  if (!date) return ''
  const d = new Date(date)
  return isNaN(d) ? '날짜 없음' : d.toLocaleString('ko-KR')
}

onMounted(async () => {
  try {
    // 게시글 상세 정보 조회
    const res = await fetch(`${serverUrl}/api/public/board/detail/${props.boardId}`, {
      headers: { Authorization: token },
      credentials: 'include'
    })
    
    if (!res.ok) throw new Error('게시글 조회 실패')
    
    const data = await res.json()
    board.value = data.board
    const images = data.images?.[props.boardId]
    
    // 이미지 URL 설정 수정
    if (images && images.length > 0) {
      imageUrl.value = `http://localhost:5173/images/${images[0].name}`
    } else {
      imageUrl.value = 'http://localhost:5173/images/default.jpg'
    }

    // 버킷리스트 상태 확인
    try {
      const bucketResponse = await axios.get(`${serverUrl}/api/board/check/${props.boardId}`, {
        headers: { Authorization: token },
        withCredentials: true
      })
      isInBucket.value = bucketResponse.data
    } catch (err) {
      console.error('버킷 상태 확인 실패:', err)
    }

    // 버킷 카운트 조회
    const bucketCountRes = await fetch(`${serverUrl}/api/bucket/count/${props.boardId}`, {
      headers: { Authorization: token },
      credentials: 'include'
    })
    
    if (!bucketCountRes.ok) {
      throw new Error('버킷 카운트 조회 실패')
    }
    
    bucketCount.value = await bucketCountRes.json()

    // 댓글 목록 조회
    await fetchComments()
  } catch (err) {
    console.error('데이터 로딩 실패:', err.message)
  }
})

// 게시글 삭제
const deletePost = async () => {
  if (!confirm('정말 삭제하시겠습니까?')) return

  try {
    const res = await fetch(`${serverUrl}/api/board/${props.boardId}`, {
      method: 'DELETE',
      headers: {
        Authorization: token
      },
      credentials: 'include'
    })

    if (!res.ok) {
      alert('삭제에 실패했습니다.')
      return
    }

    alert('삭제되었습니다.')
    emit('deleted')
    window.location.reload()
    emit('close')
  } catch (err) {
    alert('에러가 발생했습니다.')
  }
}

const handleUpdate = async () => {
  showEdit.value = false
  await fetchBoard()
}

const fetchBoard = async () => {
  try {
    const res = await fetch(`${serverUrl}/api/public/board/detail/${props.boardId}`, {
      headers: { Authorization: token },
      credentials: 'include'
    })

    if (!res.ok) throw new Error('게시글 조회 실패')

    const data = await res.json()
    board.value = data.board
    const images = data.images?.[props.boardId]
    
    if (images && images.length > 0) {
      imageUrl.value = `http://localhost:5173/images/${images[0].name}`
    } else {
      imageUrl.value = 'http://localhost:5173/images/default.jpg'
    }

    // 버킷리스트 상태 확인
    try {
      const bucketResponse = await axios.get(`${serverUrl}/api/board/check/${props.boardId}`, {
        headers: { Authorization: token },
        withCredentials: true
      })
      isInBucket.value = bucketResponse.data
    } catch (err) {
      console.error('버킷 상태 확인 실패:', err)
    }

    // 버킷 카운트 조회
    const bucketCountRes = await fetch(`${serverUrl}/api/bucket/count/${props.boardId}`, {
      headers: { Authorization: token },
      credentials: 'include'
    })
    
    if (!bucketCountRes.ok) {
      throw new Error('버킷 카운트 조회 실패')
    }
    
    bucketCount.value = await bucketCountRes.json()

    await fetchComments()
  } catch (err) {
    console.error('데이터 로딩 실패:', err.message)
  }
}

//버킷리스트 토글 버튼
const toggleBucket = async () => {
  try {
    if (!isInBucket.value) {
      await axios.post(`${serverUrl}/api/bucket`, {
        boardId: props.boardId,
        done: 1
      }, {
        headers: { Authorization: token },
        withCredentials: true
      })
      isInBucket.value = true
      alert('버킷리스트에 추가되었습니다!')
    } else {
      await axios.delete(`${serverUrl}/api/bucket/main/${props.boardId}`, {
        headers: { Authorization: token },
        withCredentials: true
      })
      isInBucket.value = false
      alert('버킷리스트에서 제거되었습니다!')
    }

    // 버킷 카운트 갱신
    const bucketCountRes = await fetch(`${serverUrl}/api/bucket/count/${props.boardId}`, {
      headers: { Authorization: token },
      credentials: 'include'
    })
    bucketCount.value = await bucketCountRes.json()
  } catch (err) {
    console.error('버킷리스트 처리 실패:', err)
    alert('처리에 실패했습니다.')
  }
}

//댓글 작성
const submitComment = async () => {
  const res = await fetch(`${serverUrl}/api/public/comment/board/${props.boardId}`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Authorization: token
    },
    credentials: 'include',
    body: JSON.stringify({
      boardId: props.boardId,
      content: newComment.value
    })
  })

  if (!res.ok) {
    const msg = await res.text()
    alert('댓글 등록에 실패했습니다.')
    return
  }
  newComment.value = ''
  const commentRes = await fetch(`${serverUrl}/api/public/comment/board/${props.boardId}`, {
    headers: { Authorization: token },
    credentials: 'include'
  })

  comments.value = await commentRes.json()
}

//댓글 수정
const editComment = async (commentId) => {
  const res = await fetch(`${serverUrl}/api/comment/${commentId}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
      Authorization: token
    },
    credentials: 'include',
    body: JSON.stringify({
      commentId, // 👈 필요함!
      content: editContent.value
    })
  })

  if (!res.ok) {
    alert('댓글 수정 실패')
    return
  }

  await fetchComments()
  cancelEdit()
}

//댓글 삭제
const deleteComment = async (commentId) => {
  if (!confirm('댓글을 삭제할까요?')) return

  const res = await fetch(`${serverUrl}/api/comment/${commentId}`, {
    method: 'DELETE',
    headers: { Authorization: token },
    credentials: 'include'
  })

  if (!res.ok) {
    alert('댓글 삭제 실패')
    return
  }

  await fetchComments()
}

const fetchComments = async () => {
  const res = await fetch(`${serverUrl}/api/public/comment/board/${props.boardId}`, {
    headers: { Authorization: token },
    credentials: 'include'
  })

  if (!res.ok) {
    return
  }

  comments.value = await res.json()
}

const goToUserPage = (userId) => {
  emit('close')  // 현재 모달 닫기
  router.push({
    name: 'mypage',
    params: { userId }
  })
}

</script>

<style scoped>
.overlay {
  position: fixed;
  top: 0; left: 0; right: 0; bottom: 0;
  background: rgba(0, 0, 0, 0.4);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}
.modal-card {
  display: flex;
  background: white;
  width: 55%;
  height: 55%;
  border-radius: 5px;
  overflow: hidden;
}
.left {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
}
.left img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
.right {
  flex: 1;
  padding: 20px;
  display: flex;
  flex-direction: column;
}
.header {
  margin-bottom: 16px;
}

.title-row {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 8px;
}

.title-row h3 {
  font-size: 1.5rem;
  margin: 0;
}

.author {
  color: #000000;
  font-size: 1rem;
  padding: 4px 8px;
  background-color: #0eebff;
  border-radius: 4px;
  cursor: pointer;
  transition: all 0.2s ease;
}

.author:hover {
  background-color: #00c3ff;
  transform: translateY(-1px);
}

.date {
  font-size: medium;
  color: #888;
}
.content {
  flex-grow: 1;
  margin-bottom: 16px;
  font-size: large;
}
.actions {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 16px;
  margin-top: 50px;
}
.divider {
  height: 1px;
  background-color: #ddd;
  width: 100%;
  margin: 24px 0;
}
.liked {
  color: red;
}
.comments {
  flex-grow: 1;
  overflow-y: auto;
}
.comment {
  padding: 12px 0;
  border-bottom: 1px solid #eee;
}
.comment-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 8px;
}
.comment-body {
  position: relative;
}
.comment-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 10px;
}
.comment-actions {
  display: flex;
  gap: 4px;
}
.comment-actions button {
  padding: 2px 6px;
  background: none;
  border: none;
  cursor: pointer;
}
.edit-form {
  display: flex;
  flex-direction: column;
  gap: 8px;
  margin-top: 4px;
  width: 100%;
}

.edit-form input {
  flex: 1;
  padding: 8px 12px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 0.95rem;
  transition: border-color 0.2s;
}

.edit-form input:focus {
  outline: none;
  border-color: #007bff;
}

.edit-buttons {
  display: flex;
  gap: 8px;
  justify-content: flex-end;
}

.edit-btn {
  padding: 6px 12px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 0.9rem;
  transition: all 0.2s;
  background: none;
}

.edit-btn.save {
  color: #007bff;
}

.edit-btn.save:hover {
  background-color: #007bff;
  color: white;
}

.edit-btn.cancel {
  color: #666;
}

.edit-btn.cancel:hover {
  background-color: #f0f0f0;
  color: #333;
}
.time {
  font-size: 0.8rem;
  color: #aaa;
}
.comment-form {
  display: flex;
  gap: 8px;
  margin-top: 16px;
}
.comment-form input {
  flex: 1;
  padding: 12px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 0.95rem;
  height: 45px;
  transition: border-color 0.2s;
}
.comment-form input:focus {
  outline: none;
  border-color: #007bff;
}
.submit-btn {
  padding: 8px 20px;
  background: none;
  border: 1px solid #ddd;
  border-radius: 4px;
  cursor: pointer;
  font-size: 0.95rem;
  color: #333;
  transition: all 0.2s;
}
.submit-btn:hover {
  background-color: #007bff;
  border-color: #007bff;
  color: white;
}
.action-btn {
  background: none;
  border: none;
  padding: 8px 12px;
  cursor: pointer;
  font-size: 1rem;
  color: #333;
  transition: color 0.2s;
  display: flex;
  align-items: center;
  gap: 4px;
}
.action-btn:hover {
  color: #007bff;
}
.filled-star {
  color: #FFD700;
}
.bucket-count {
  font-size: 1rem;
  color: #666;
}
</style>
