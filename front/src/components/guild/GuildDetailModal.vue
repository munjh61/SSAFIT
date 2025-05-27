<template>
  <Teleport to="body">
    <div class="modal-overlay" @click.self="close">
      <div class="modal">
        <div inputs>
          <div class="logo">
            <img src="/src/assets/images/SSAFIT.png" alt="">
          </div>
          <div class="title">
            <p class="icon">🔐</p>
            <h2>모임 생성 </h2>
          </div>
          <div class="input-container">
            <fieldset>
              <legend>모임 이름</legend>
              <input v-model="guildName" />
            </fieldset>
            <p class="error-msg">nameError</p>
          </div>

          <div class="input-container">
            <fieldset>
              <legend>소개글</legend>
              <textarea v-model="description" rows="4" style="resize: none;" />
            </fieldset>
          </div>

          <div class="button-group">
            <button @click="submit">생성</button>
            <button class="cancel" @click="close">취소</button>
          </div>
        </div>
      </div>
    </div>
  </Teleport>
</template>

<script setup>
import { ref } from 'vue'

const emit = defineEmits(['close'])

const close = () => {
  guildName.value = ''
  description.value = ''
  emit('close')
}

const guildName = ref('')
const description = ref('')

</script>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 999;
  animation: fadeIn 0.3s ease;
}

/* 모달 본체 */
.modal {
  width: 400px;
  min-height: 600px;
  max-height: 90vh;
  background-color: #ffffff;
  border: 1px solid #4a90e2;
  border-radius: 20px;
  padding: 20px 20px 10px;
  display: flex;
  flex-direction: column;
  align-items: center;
  overflow: hidden;
  animation: slideUp 0.3s ease;
}

/* 로고 이미지 영역 */
.logo {
  text-align: center;
  margin-top: 10px;
  margin-bottom: 10px;
  height: 100px;
}

.logo img {
  width: 200px;
  height: 100px;
  object-fit: contain;
}

.title {
  margin-top: 0;
  text-align: center;
  font-weight: bold;
  color: #4a90e2;
}

.icon {
    font-size: 40px;
    margin: 10px 0;
}

.input-container {
  margin-bottom: 10px;
}

.input-container fieldset {
  border: 2px solid #ccc;
  border-radius: 8px;
  padding: 10px 30px;
  transition: border-color 0.3s, box-shadow 0.3s;
}

.input-container fieldset:focus-within {
  border-color: #4a90e2;
  box-shadow: 0 0 0 3px rgba(74, 144, 226, 0.2);
}

.input-container legend {
  font-size: 14px;
  padding: 0 5px;
  color: #555;
  font-weight: 600;
}

.input-container textarea,
.input-container input {
  border: none;
  outline: none;
  width: 300px;
  font-size: 16px;
  padding: 4px 0;
  background: transparent;
  color: #333;
}

/* 메인 컨텐츠 스크롤 영역 */
.main-contents {
  width: 100%;
  flex-grow: 1;
  overflow-y: auto;
  max-height: 100%;
  padding-right: 6px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

/* 스크롤바 꾸미기 */
.main-contents::-webkit-scrollbar {
  width: 6px;
}

.main-contents::-webkit-scrollbar-thumb {
  background-color: #ccc;
  border-radius: 4px;
}

.main-contents::-webkit-scrollbar-track {
  background-color: transparent;
}

.button-group {
  display: flex;
  justify-content: space-evenly;
}

/* 넓이를 input container에만 줘서 나머지 %들이 맞춰짐 */
button {
  width: 48%;
  height: 40px;
  border: 0;
  border-radius: 8px;
  color: #ffffff;
  background-color: #4a90e2;
}

button:hover {
  background-color: #357ABD;
}
</style>