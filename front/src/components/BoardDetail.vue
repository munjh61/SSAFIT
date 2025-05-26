<template>
  <div class="overlay" @click.self="emit('close')">
    <div class="modal-card">
      <div class="left">
        <img :src="imageUrl" alt="게시글 이미지" />
      </div>
      <div class="right">
        <div class="header">
          <h3>{{ board?.title }}</h3>
          <p class="date">{{ formatDate(board?.regDate) }}</p>
        </div>
        <p class="content">{{ board?.content }}</p>

        <div class="actions">
          <!-- <span @click="toggleLike" :class="{ liked: isLiked }">❤️</span> {{ likeCount }} -->
          <span class="bucket-count">{{ bucketCount }}</span>
          <button @click="toggleBucket">⭐ 버킷 추가</button>

          <button v-if="board?.userId === store.userId" @click="showEdit = true">✏️ 수정</button>

          <button
            v-if="board?.userId === store.userId"
            @click="deletePost"
          >
            ❌ 삭제
          </button>
        </div>

        <div class="comments">
          <div v-for="comment in comments" :key="comment.commentId" class="comment">
            <strong>{{ comment.userId }}</strong>
            
            <div v-if="editCommentId !== comment.commentId">
              {{ comment.content }}
            </div>
            <div v-else>
              <input v-model="editContent" />
              <button @click="editComment(comment.commentId)">💾 저장</button>
              <button @click="cancelEdit">취소</button>
            </div>

            <div v-if="comment.userId === store.userId">
              <button @click="startEdit(comment)">✏️</button>
              <button @click="deleteComment(comment.commentId)">❌</button>
            </div>
            <div class="time">{{ formatDate(comment.regDate) }}</div>
          </div>
        </div>

        <div class="comment-form">
          <input v-model="newComment" placeholder="댓글을 입력하세요" />
          <button @click="submitComment">등록</button>
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
const store = useAuthStore()

const props = defineProps({ boardId: Number })
const emit = defineEmits(['close', 'created'])

const serverUrl = import.meta.env.VITE_API_BASE_URL
const token = `Bearer ${sessionStorage.getItem('ssafit-login-token')}`

store.userId

const board = ref(null)
const imageUrl = ref('')
const comments = ref([])
const newComment = ref('')
const bucketCount = ref(0)
const showEdit = ref(false)

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

watch(board, () => {
  console.log('📌 board.userId:', board.value?.userId)
  console.log('👤 로그인한 ID:', store.userId)
})

watch(comments, () => {
  console.log('💬 댓글 목록:', comments.value)
  comments.value.forEach(c => {
    console.log('🧑 댓글 작성자:', c.userId, '👤 로그인 유저:', store.userId, '→ 같나?', c.userId === store.userId)
  })
})

const formatDate = (date) => {
  if (!date) return ''
  const d = new Date(date)
  return isNaN(d) ? '날짜 없음' : d.toLocaleString('ko-KR')
}

onMounted(async () => {
  const res = await fetch(`${serverUrl}/api/public/board/${props.boardId}`, {
    headers: { Authorization: token },
    credentials: 'include'
  })

  if (!res.ok) {
    console.error('❌ 게시글 데이터 불러오기 실패:', res.status)
    return
  }
  const data = await res.json()
  board.value = data.board
  const images = data.images?.[props.boardId]
  console.log('🖼 이미지 데이터:', images)
  
  // 이미지 URL 설정 수정
  if (images && images.length > 0) {
    imageUrl.value = `http://localhost:5173/images/${images[0].name}`
  } else {
    imageUrl.value = 'http://localhost:5173/images/default.jpg'
  }

  const bucketRes = await fetch(`${serverUrl}/api/bucket/count/${props.boardId}`, {
    headers: { Authorization: token },
    credentials: 'include'
  })
  bucketCount.value = await bucketRes.json()

  console.log('📌 게시글 데이터:', data)
  console.log('🖼 이미지 목록:', images)

  const commentRes = await fetch(`${serverUrl}/api/public/comment/board/${props.boardId}`, {
    headers: {
    Authorization: token 
  },
  credentials: 'include'
  })

  if (!commentRes.ok) {
    console.error('❌ 댓글 목록 가져오기 실패:', commentRes.status)
    return
  }

  comments.value = await commentRes.json()
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
      console.error('❌ 삭제 실패', res.status)
      alert('삭제에 실패했습니다.')
      return
    }

    alert('삭제되었습니다.')
    emit('close')
  } catch (err) {
    console.error('삭제 중 에러:', err)
    alert('에러가 발생했습니다.')
  }
}

const handleUpdate = async () => {
  showEdit.value = false
  await fetchBoard()
}

const fetchBoard = async () => {
  const res = await fetch(`${serverUrl}/api/public/board/${props.boardId}`, {
    headers: { Authorization: token },
    credentials: 'include'
  })

  if (!res.ok) {
    console.error('❌ 게시글 데이터 불러오기 실패:', res.status)
    return
  }

  const data = await res.json()
  board.value = data.board
  const images = data.images?.[props.boardId]
  console.log('🖼 이미지 데이터:', images)
  
  // 이미지 URL 설정 수정
  if (images && images.length > 0) {
    imageUrl.value = `http://localhost:5173/images/${images[0].name}`
  } else {
    imageUrl.value = 'http://localhost:5173/images/default.jpg'
  }

  const bucketRes = await fetch(`${serverUrl}/api/bucket/count/${props.boardId}`, {
    headers: { Authorization: token },
    credentials: 'include'
  })
  bucketCount.value = await bucketRes.json()
}


//버킷리스트 추가 버튼튼
const toggleBucket = async () => {
  await fetch(`${serverUrl}/api/bucket`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Authorization: token
    },
    credentials: 'include',
    body: JSON.stringify({ boardId: props.boardId })
  })
  alert('버킷리스트에 추가되었습니다!')

  //숫자 갱신
  const bucketRes = await fetch(`${serverUrl}/api/bucket/count/${props.boardId}`, {
    headers: { Authorization: token },
    credentials: 'include'
  })
  bucketCount.value = await bucketRes.json()
}
//댓글 작성성
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
    console.error('❌ 댓글 등록 실패:', res.status, msg)
    alert('댓글 등록에 실패했습니다.')
    return
  }
  newComment.value = ''
  const commentRes = await fetch(`${serverUrl}/api/public/comment/board/${props.boardId}`, {
    headers: { Authorization: token },
    credentials: 'include'
  })
  if (!commentRes.ok) {
    console.error('❌ 댓글 목록 가져오기 실패:', commentRes.status)
    return
  }
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
    console.error('❌ 댓글 목록 다시 불러오기 실패:', res.status)
    return
  }

  comments.value = await res.json()
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
  width: 65%;
  height: 70%;
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
.date {
  font-size: 0.9rem;
  color: #888;
}
.content {
  flex-grow: 1;
  margin-bottom: 16px;
}
.actions {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 16px;
}
.liked {
  color: red;
}
.comments {
  flex-grow: 1;
  overflow-y: auto;
}
.comment {
  padding: 6px 0;
  border-bottom: 1px solid #eee;
}
.time {
  font-size: 0.8rem;
  color: #aaa;
}
.comment-form {
  display: flex;
  gap: 8px;
}
.comment-form input {
  flex: 1;
  padding: 6px;
  border: 1px solid #ccc;
  border-radius: 4px;
}
</style>
