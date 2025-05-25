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
          <textarea v-model="content" @input="updateCount" :maxlength="2000" placeholder="내용을 입력하세요" />
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

const content = ref('')
const previewUrl = ref('')
const imageFile = ref(null)
const serverUrl = import.meta.env.VITE_API_BASE_URL

const emit = defineEmits(['close'])

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
  formData.append('content', content.value)
  if (imageFile.value) {
    formData.append('image', imageFile.value)
  }

  try {
    await axios.post(`${serverUrl}/api/board`, formData, {
      headers: {
        Authorization: token,
        'Content-Type': 'multipart/form-data'
      },
      withCredentials: true
    })
    alert('게시글이 등록되었습니다!')
    close()
  } catch (err) {
    console.error('글 등록 실패:', err)
    alert('등록 실패')
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
}
.text-section textarea {
  width: 100%;
  max-width: 100%;
  flex: 1;
  resize: none;
  font-size: 16px;
  padding: 20px;
  border-radius: 12px;
  border: 1px solid #ccc;
  box-sizing: border-box;
}
.char-count {
  bottom: 40px;
  right: 16px;
  font-size: 14px;
  color: #888;
}
.submit-btn {
  margin-left: 12px;
  align-self: flex-end;
  padding: 10px 20px;
  border: none;
  background-color: #10217D;
  color: white;
  font-weight: bold;
  border-radius: 6px;
  cursor: pointer;
  display: flex;
}
.tail {
  display: flex;
  justify-content: flex-end;
  margin-top: 8px;
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
  display: flex;
  font-size: 14px;
  color: #888;
}
</style>
