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
    <PostWriteBoard v-if="isWriting" @close="isWriting = false"/>

    <BoardDetail
      v-if="showDetail"
      :boardId="selectBoardId"
      @close="showDetail = false"
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

onMounted(async () => {
  const token = `Bearer ${sessionStorage.getItem('ssafit-login-token')}`
  // console.log('토큰:', token)
  try {
    // // 1. 사용자 통계 정보
    // const statsResponse = await axios.get(`${serverUrl}/api/public/`, {
    //         headers:{
    //             Authorization: token
    //         },
    //         withCredentials: true
    //     })
    // userStats.value = statsResponse.data 

    // 2. 사용자 기록 이미지 목록
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

    userRecords.value = boards.map(board => {
      const imgList = images[board.boardId]
      const imgName = imgList && imgList.length > 0 ? imgList[0].name : 'default.jpg'
      return {
        id: board.boardId,
        caption: board.title,
        img: `/images/${imgName}`
      }
    })
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