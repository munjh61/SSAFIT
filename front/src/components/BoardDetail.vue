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

          <button v-if="board?.userId === loginUserId" @click="showEdit = true">✏️ 수정</button>

          <!-- <button
            v-if="board?.userId === loginUserId"
            @click="deletePost"
          >
            ❌ 삭제
          </button> -->
        </div>

        <div class="comments">
          <div v-for="comment in comments" :key="comment.commentId" class="comment">
            <strong>{{ comment.userId }}</strong> {{ comment.content }}
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
  />
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import PostWriteBoard from './PostWriteBoard.vue'

const props = defineProps({ boardId: Number })
const emit = defineEmits(['close'])

const serverUrl = import.meta.env.VITE_API_BASE_URL
const token = `Bearer ${sessionStorage.getItem('ssafit-login-token')}`
const loginUserId = sessionStorage.getItem('ssafit-login-userId')

const board = ref(null)
const imageUrl = ref('')
const comments = ref([])
// const likeCount = ref(0)
// const isLiked = ref(false)
const newComment = ref('')
const bucketCount = ref(0)
const showEdit = ref(false)

watch(board, () => {
  console.log('📌 board.userId:', board.value?.userId)
  console.log('👤 로그인한 ID:', loginUserId)
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
  imageUrl.value = images?.[0]?.name ? `/images/${images[0].name}` : '/images/default.jpg'

  const bucketRes = await fetch(`${serverUrl}/api/bucket/count/${props.boardId}`, {
    headers: { Authorization: token },
    credentials: 'include'
  })
  bucketCount.value = await bucketRes.json()

  console.log('📌 게시글 데이터:', data)
  console.log('🖼 이미지 목록:', images)


  // const likeRes = await fetch(`${serverUrl}/like/count/${props.boardId}`)
  // likeCount.value = await likeRes.json()

  const commentRes = await fetch(`${serverUrl}/api/comment/${props.boardId}`)
  comments.value = await commentRes.json()
})

// const toggleLike = async () => {
//   const res = await fetch(`${serverUrl}/like/${props.boardId}`, {
//     method: 'POST',
//     headers: { Authorization: token },
//     credentials: 'include'
//   })
//   const liked = await res.json()
//   isLiked.value = liked
//   likeCount.value += liked ? 1 : -1
// }

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
