<template>
  <HeaderBar />
    <div class="container">
        <div class="userProfile">
          <ProfileSection :stats="userStats" />
        </div>
        <div class="divider"></div>
        <div class="userRecords">
          <RecordGrid :records="userRecords" @write="WriteClick" @open-detail="openDetail"/>
        </div>
    </div>
    <PostWriteBoard v-if="isWriting" @close="isWriting = false" @created="handlePostCreated" />

    <BoardDetail
      v-if="showDetail"
      :boardId="selectBoardId"
      @close="showDetail = false"
      @deleted="handlePostDeleted"
    />
</template>

<script setup>
import HeaderBar from '@/components/HeaderBar.vue'
import ProfileSection from '@/components/ProfileSection.vue'
import RecordGrid from '@/components/RecordGrid.vue'
import PostWriteBoard from '@/components/PostWriteBoard.vue'
import BoardDetail from '@/components/BoardDetail.vue'

import {ref, onMounted} from 'vue'
import axios from 'axios'

const serverUrl = import.meta.env.VITE_API_BASE_URL
const isWriting = ref(false)
const showDetail = ref(false)
const selectBoardId = ref(null)
const userStats = ref({
  posts: 0,
  followers: 0,
  following: 0
})
const userRecords = ref([])
const editingBoard = ref(null)
const isEditing = ref(false)

const WriteClick = () => {
  isWriting.value = true
}

const openDetail = (boardId) => {
  selectBoardId.value = boardId
  showDetail.value = true
}

const openEdit = (board) => {
  editingBoard.value = board
  isEditing.value = true
}

const handlePostCreated = (postData) => {
  console.log('🔥 새 글 도착:', postData)
  isWriting.value = false

  // 방금 등록된 글을 리스트에 추가
  userRecords.value.unshift({
    id: postData.boardId,
    caption: postData.title,
    img: `http://localhost:5173/images/${postData.imgName}`
  })

  // 게시글 수 증가
  userStats.value = {
    ...userStats.value,
    posts: userStats.value.posts + 1
  }

  // BoardDetail 자동 표시 제거
  if (!postData.boardId) {
    console.error('❌ boardId가 undefined입니다!', postData)
  }
}

const handlePostDeleted = () => {
  // 게시글 수 감소
  userStats.value = {
    ...userStats.value,
    posts: Math.max(0, userStats.value.posts - 1)
  }
}

onMounted(async () => {
  const token = `Bearer ${sessionStorage.getItem('ssafit-login-token')}`
  try {
    const recordsResponse = await axios.get(`${serverUrl}/api/board`, {
      headers:{
        Authorization: token
      },
      withCredentials: true
    })
    console.log('✅ API 응답 전체:', recordsResponse.data)
    console.log('📋 게시글 목록 boards:', recordsResponse.data.boards)
    console.log('🖼 이미지 목록 images:', recordsResponse.data.images)

    const boards = recordsResponse.data.boards
    const images = recordsResponse.data.images

    // 게시글 수 업데이트
    userStats.value = {
      ...userStats.value,
      posts: boards.length
    }

    userRecords.value = boards.map(board => {
      const imageArray = images[board.boardId];
      const imgName = imageArray && imageArray.length > 0 ? imageArray[0].name : 'default.jpg';
      
      return {
        id: board.boardId,
        caption: board.title,
        img: `http://localhost:5173/images/${imgName}`
      }
    })

    console.log('🔄 변환된 userRecords:', userRecords.value)
  } catch (error) {
    console.error('마이페이지 데이터 불러오기 실패:', error)
    console.log('응답 코드:', error.response.status)
    console.log('응답 데이터:', error.response.data)
  }
})

</script>

<style scoped>
.container{
  max-width: 1600px;
  margin: 0 auto;
  padding: 0 16px;
}
.userProfile{
  display: flex;
  justify-content: center;
  padding-top: 60px;
}
.divider {
  width: 60%;
  height: 1px;
  background-color: #e2e2e2;
  margin: 24px auto;
}
.userRecords{
  display: flex;
  justify-content: center;
  
}


</style>