<template>
    <Teleport to="body">
        <div class="modal-overlay" @click.self="closeModal">
            <div class="modal">
                <div class="logo">
                    <img src="/src/assets/images/SSAFIT.png" alt="">
                </div>
                <div class="title">
                    <p>회원정보 수정</p>
                    <p class="icon">⚙️</p>
                </div>
                <div class="inputs">
                    <div class="input-container">
                        <fieldset>
                            <legend>닉네임</legend>
                            <input type="text" v-model="userName">
                        </fieldset>
                        <fieldset>
                            <legend>비밀번호</legend>
                            <input type="text" v-model="password">
                        </fieldset>
                        <fieldset class="email">
                            <legend>이메일</legend>
                            <div class="step1">
                                <input type="text" v-model="password">
                                <button @click="send">전송</button>
                            </div>
                            <div class="step2">
                                <input type="text" v-model="code">
                                <button @click="verify">인증</button>
                            </div>
                        </fieldset>
                    </div>
                </div>
                <button @click="update">정보 변경</button>
            </div>
        </div>
    </Teleport>
</template>

<script setup>
import { useAuthStore } from '@/stores/auth'
import { useAuthNewStore } from '@/stores/authNew'
import { ref } from 'vue'

const store = useAuthNewStore()
const emit = defineEmits(["close"])

const closeModal = () => emit('close')

// 폼 데이터
const userName = ref('')
const password = ref('')
const email = ref('')
const code = ref('')

// 유효성 플래그
const checkPassword = ref(false)
const checkEmail = ref(false)

// watch 생략 가능 (필요시 넣어도 됨)

// 이메일 인증 전송
const isSend = ref(false)
const isSending = ref(false)

const send = async () => {
    const isUnique = await store.checkEmail(email.value)
    if (!isUnique) return
    isSending.value = true
    const success = await store.send(email.value)
    if (success) isSend.value = true
    isSending.value = false
}

const verify = async () => {
    const success = await store.verify(code.value)
    if (success) checkEmail.value = true
}

// 서버로 정보 변경 요청
const update = async () => {
    // 수정 여부 판단
    const newPassword = password.value.trim() !== '' ? password.value : null
    const newEmail = email.value.trim() !== '' && checkEmail.value ? email.value : null
    const newName = userName.value.trim() !== '' ? userName.value : null

    // 아무것도 수정 안 했으면 리턴
    if (!newPassword && !newEmail && !newName) {
        alert("변경된 정보가 없습니다.")
        return
    }

    // 비번만 바꾸는 경우
    if (newPassword && !store.isValidPassword(newPassword)) {
        alert("비밀번호 형식이 올바르지 않습니다.")
        return
    }

    // const success = await store.update({password : newPassword, email: newEmail, userName: newName})
    const success = await store.update(newPassword, newEmail, newName, null, null)

    if (success) {
        alert("회원 정보가 변경되었습니다.")
        emit('close')
        useAuthStore().me()
    } else {
        alert("변경에 실패했습니다.")
    }
}

</script>

<style scoped>
/* 모달창 */
.modal {
    position: fixed;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    padding: 15px;
    border: 1px, solid, #4a90e2;
    background-color: #ffffff;
    border-radius: 20px;
    width: 400px;
    height: 600px;

    display: flex;
    flex-direction: column;
    align-items: center;

    animation: slideUp 0.3s ease;
}

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

.logo {
    margin-top: 30px;
    height: 100px;
}

.logo img {
    width: 200px;
    height: 100px;
}

.icon {
    font-size: 40px;
    margin: 10px 0;
}

.title {
    margin-top: 0;
    text-align: center;
    font-weight: bold;
    color: #4a90e2;
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

.input-container input {
    border: none;
    outline: none;
    width: 300px;
    font-size: 16px;
    padding: 4px 0;
    background: transparent;
    color: #333;
}

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

.email {
    display: flex;
    flex-direction: column;
    gap: 10px;
}

.email div {
    width: 100%;
    display: flex;
    justify-content: space-around;
}

.email input {
    width: 70%;
    border-bottom-color: #4a90e2;
    border-bottom-style: solid;
    border-bottom-width: 1px;
}

.email input:focus {
    border-bottom-color: #357ABD;
}

.email button {
    width: 25%;
}
</style>