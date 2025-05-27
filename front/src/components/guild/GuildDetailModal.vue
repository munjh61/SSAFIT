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
            <h2> 모임 상세 보기 </h2>
            <h4>모임장 @{{ store.owner }}</h4>
          </div>
          <div class="input-container" :class="{ ownerOnly: !store.isOwner }">
            <fieldset>
              <legend>모임 이름</legend>
              <input v-model="store.guildName" :readonly="!store.isOwner" />
            </fieldset>
          </div>

          <div class="input-container" :class="{ ownerOnly: !store.isOwner }">
            <fieldset>
              <legend>소개글</legend>
              <textarea v-model="store.description" rows="4" style="resize: none;" :readonly="!store.isOwner" />
            </fieldset>
          </div>

          <div class="button-group">
            <button v-if="!store.isMember" @click="apply">지원하기</button>
            <button v-else @click="quit">탈퇴하기</button>
          </div>
        </div>
        <div v-if="store.isOwner" class="main-contents">
          <h3>✅ 현재 모임 인원 ({{ store.crews.length }})</h3>
          <ul>
            <li v-for="crew in store.crews" :key="crew.userId" class="member-item">
              <span class="member-info">{{ crew.userId }} - {{ crew.roll || '멤버' }}</span>
              <div class="member-buttons">
                <button @click="kick(crew.userId)">강퇴</button>
              </div>
            </li>
          </ul>

          <h3>📝 가입 신청자 목록 ({{ store.candidates.length }})</h3>
          <ul>
            <li v-for="cand in store.candidates" :key="cand.userId" class="member-item">
              <span class="member-info">
                {{ cand.userId }} - 상태:
                <span v-if="cand.status == 2">지원</span>
                <span v-else-if="cand.status == 1">초대</span>
                <span v-else>기타</span>
              </span>
              <div class="member-buttons">
                <button @click="accept(cand.userId)">수락</button>
                <button @click="refuse(cand.userId)">거절</button>
              </div>
            </li>
          </ul>
        </div>

      </div>
    </div>
  </Teleport>
</template>

<script setup>
import { useAuthStore } from '@/stores/auth'
import { useGuildDetailStore } from '@/stores/guildDetail'
import { onMounted } from 'vue'
const store = useGuildDetailStore()
const props = defineProps({
  guildId: Number
})

const emit = defineEmits(['close'])

const close = () => {
  emit('close')
}

const apply = async () => {
  await store.apply(props.guildId)
  alert(store.msg)
}

const quit = () => {
  store.quit(props.guildId)
}

const accept = async (targetId) => {
  const result = await store.applyManage(props.guildId, targetId, "applyAccept");
  if (result) {
    const idx = store.candidates.findIndex(c => c.userId === targetId);
    if (idx !== -1) {
      const accepted = store.candidates.splice(idx, 1)[0];
      accepted.status = 0;
      store.crews.push(accepted);
    }
  }
  alert(store.msg);
};


const refuse = async (targetId) => {
  await store.applyManage(props.guildId, targetId, "applyRefuse");
  store.candidates = store.candidates.filter(c => c.userId !== targetId);
};

const kick = async (targetId) => {
  await store.applyManage(props.guildId, targetId, "quit");
  store.crews = store.crews.filter(c => c.userId !== targetId);
};


onMounted(async () => {
  store.start(props.guildId)
})

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

/* 넓이를 input container에만 줘서 나머지 %들이 맞춰짐 */
button {
  width: 100%;
  height: 40px;
  border: 0;
  border-radius: 8px;
  color: #ffffff;
  background-color: #4a90e2;
}

button:hover {
  background-color: #357ABD;
}

.ownerOnly input,
.ownerOnly textarea {
  pointer-events: none;
}

.member-item {
  width: 100%;
  padding: 4px 0;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.member-info {
  font-size: 14px;
  color: #333;
}

.member-buttons {
  display: flex;
  gap: 6px;
}

.member-buttons button {
  width: 48%;
  padding: 4px 8px;
  font-size: 12px;
  height: auto;
  width: auto;
  background-color: #ddd;
  color: #333;
  border: none;
  border-radius: 6px;
  cursor: pointer;
}

.member-buttons button:hover {
  background-color: #aaa;
}
</style>