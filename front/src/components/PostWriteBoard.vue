<template>
  <div class="overlay" @click.self="close">
    <div class="modal-card">
      <div class="modal-header">
        <span class="back-btn" @click="close">←</span>
        <h2>글작성</h2>
      </div>
      <div class="modal-content">
        <!-- 왼쪽: 이미지 업로드 및 미리보기 -->
        <div class="image-section">
          <label class="upload-box">
            <input type="file" accept="image/*" @change="handleImage" hidden />
            <img :src="previewUrl || '/images/upload.png'" alt="preview" />
          </label>
        </div>

        <!-- 오른쪽: 글쓰기 -->
        <div class="text-section">
          <div class="input-group">
            <input v-model="title" placeholder="제목을 입력하세요" class="input-field title" />
            <input v-model="tag" placeholder="태그를 입력하세요" class="input-field tag" />
          </div>
          <textarea v-model="content" @input="updateCount" :maxlength="2000" placeholder="내용을 입력하세요" class="input-field content" />
          <div class="tail">
            <div class="right-group">
              <div class="char-count">{{ content.length }}/2000</div>
              <button @click="submitPost" class="submit-btn">등록</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'

const props = defineProps({
  board: Object,
  editMode: Boolean
})

const title = ref(props.board?.title || '')
const tag = ref(props.board?.tag || '')
const content = ref(props.board?.content || '')
const previewUrl = ref('')
const imageFile = ref(null)
const serverUrl = import.meta.env.VITE_API_BASE_URL

const emit = defineEmits(['close', 'created'])

const close = () => {
  emit('close')
}

const handleImage = (event) => {
  const file = event.target.files[0]
  if (file) {
    imageFile.value = file
    previewUrl.value = URL.createObjectURL(file)
  }
}

const updateCount = () => {
}

const submitPost = async () => {
  const token = `Bearer ${sessionStorage.getItem('ssafit-login-token')}`
  const formData = new FormData()

  formData.append('title', title.value)
  formData.append('tag', tag.value)
  formData.append('content', content.value)
  
  try {
    if(props.editMode && props.board?.boardId){
      //글 수정
      await axios.put(`${serverUrl}/api/board/${props.board.boardId}`, formData, {
        headers: {
          Authorization: token,
        },
        withCredentials: true
      })
      alert('수정 완료!')
      emit('updated')
    }else{
      // 이미지가 있는 경우 먼저 이미지 업로드
      let imageUrl = 'default.jpg'
      if (imageFile.value) {
        const imageFormData = new FormData()
        imageFormData.append('image', imageFile.value)
        
        try {
          const imageResponse = await axios.post(`${serverUrl}/api/upload`, imageFormData, {
            headers: {
              Authorization: token,
              'Content-Type': 'multipart/form-data'
            },
            withCredentials: true
          })
          
        } catch (imageError) {
          console.error('이미지 업로드 실패:', imageError)
        }
      }

      // 게시글 등록
      formData.append('image', imageFile.value) // 원본 이미지 파일도 함께 전송
      const response = await axios.post(`${serverUrl}/api/board`, formData, {
        headers: {
          Authorization: token,
        },
        withCredentials: true
      })

      const newBoardId = response.data?.boardId
      if (!newBoardId) {
        alert('게시글 등록은 되었지만 boardId를 받지 못했습니다.')
        return
      }

      emit('created', {
        boardId: newBoardId,
        title: title.value,
        imgName: imageUrl // 업로드된 이미지 이름 사용
      })
      alert('게시글이 등록되었습니다!')
      close()
      window.location.reload()
    }
  } catch (err) {
    console.error('실패:', err)
    alert('에러 발생')
  }
}
</script>

<style scoped>
.overlay {
  position: fixed;
  top: 0; left: 0; right: 0; bottom: 0;
  background: rgba(0, 0, 0, 0.3);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}
.modal-card {
  background: white;
  width: 60%;
  height: 60%;
  border-radius: 20px;
  display: flex;
  flex-direction: column;
  padding: 20px;
  box-shadow: 0 5px 20px rgba(0,0,0,0.2);
}
.modal-header {
  display: flex;
  align-items: center;
  gap: 16px;
}
.back-btn {
  cursor: pointer;
  font-size: 24px;
}
.modal-content {
  display: flex;
  flex: 1;
  overflow: hidden;
}
.image-section{
  flex: 1;
  position: relative;
  overflow: hidden;
}
.image-section img{
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 12px;
}
.upload-box {
  display: block;
  width: 100%;
  height: 100%;
  cursor: pointer;
}
.upload-box img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 12px;
}
.text-section {
  flex: 1;
  position: relative;
  display: flex;
  flex-direction: column;
  margin-left: 15px;
  gap: 16px;
}

.input-group {
  display: flex;
  gap: 12px;
}

.input-field {
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 16px;
  font-size: 25px;
  transition: all 0.2s;
}

.input-field:focus {
  outline: none;
  border-color: #10217D;
  box-shadow: 0 0 0 2px rgba(16, 33, 125, 0.1);
}

.input-field.title {
  flex: 1;
  font-weight: 500;
  height: 25px;
}

.input-field.tag {
  flex: 1;
  height: 25px;
}

.input-field.content {
  flex: 1;
  min-height: 300px;
  resize: none;
  line-height: 1.6;
  font-size: 25px;
}

.input-field::placeholder {
  color: #aaa;
  font-size: 25px;
}

.text-section textarea.input-field {
  width: 100%;
  max-width: 100%;
  box-sizing: border-box;
}

.tail {
  display: flex;
  justify-content: flex-end;
  margin-top: auto;
  padding-top: 8px;
  width: 100%;
  box-sizing: border-box;
}

.right-group {
  display: flex;
  align-items: center;
  gap: 12px;
  min-width: 160px;
  justify-content: right;
}

.char-count {
  font-size: 14px;
  color: #888;
}

.submit-btn {
  padding: 12px 32px;
  border: none;
  background-color: #10217D;
  color: white;
  font-weight: bold;
  border-radius: 8px;
  cursor: pointer;
  font-size: 16px;
  transition: all 0.2s;
}

.submit-btn:hover {
  background-color: #1a2f8f;
  transform: translateY(-1px);
}
</style>
