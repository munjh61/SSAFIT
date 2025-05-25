<template>
    <div class="new">
        <div class="title">
            <p>{{ store.msg }}</p>
        </div>
        <div class="inputs">
            <div class="input-container" v-if="step == 0">
                <fieldset>
                    <legend>아이디</legend>
                    <input type="text" v-model="userId">
                </fieldset>
            </div>
            <div class="input-container" v-if="step == 1">
                <fieldset>
                    <legend>비밀번호</legend>
                    <input type="password" v-model="password">
                </fieldset>
                <p>영어, 숫자, 특수문자를 포함한 8 ~ 20글자</p>
            </div>
            <div class="input-container" v-if="step == 2">
                <fieldset>
                    <legend>이메일</legend>
                    <div class="email-input">
                        <label style="display:none">이메일</label>
                        <input type="email" v-model="email" placeholder="이메일 입력" />
                        <button @click="send">인증 메일 전송</button>
                    </div>
                    <div class="email-input">
                        <label style="display:none">인증코드</label>
                        <input type="text" v-model="code" placeholder="인증 코드 입력" />
                        <button @click="verify">인증하기</button>
                    </div>
                </fieldset>
            </div>
            <div class="input-container" v-if="step == 3">
                <fieldset>
                    <legend>닉네임</legend>
                    <input type="text" v-model="userName">
                </fieldset>
            </div>
        </div>
        <div class="auth-button">
            <div class="two-button-box">
                <button v-if="step < 3" style="width: 49%;" @click="stepDown">이전</button>
                <button v-if="step < 3" style="width: 49%;" @click="stepUp">다음</button>
            </div>
            <button v-if="step == 3" @click="regist">회원가입</button>
        </div>
    </div>
</template>

<script setup>
import { ref, watch } from 'vue';
import { useAuthNewStore } from '@/stores/authNew';
const store = useAuthNewStore()

const userId = ref('')
const password = ref('')
const email = ref('')
const userName = ref('')

const emit = defineEmits(['close'])

const step = ref(0)
// 아이디 유효성 체크
const checkUserId = ref(false)
const watchUserId = watch(userId, (newValue) => {
    checkUserId.value = false
})
// 비밀번호 유효성 체크
const checkPassword = ref(false)
const watchPassword = watch(password, (newValue) => {
    checkPassword.value = false
})
// 이메일 유효성 체크
const checkEmail = ref(false)
const watchEmail = watch(email, (newValue) => {
    checkEmail.value = false
})
// 유저 닉네임 유효성 체크 (필요한가?)
const checkUserName = ref(false)
const watchUserName = watch(userName, (newValue) => {
    checkUserName.value = false
})

const reset = () => {
    userId.value = ''
    password.value = ''
    userName.value = ''
    email.value = ''
    step.value = 1
    store.reset()
}

const stepUp = async () => {
    // 1 아이디 중복 확인
    if (step.value == 0) {
        const result = await store.checkUserId(userId.value)
        if (!result) return;
        checkUserId.value = true
    }
    // 2 비밀번호 유효성 확인
    if (step.value == 1) {
        const result = store.isValidPassword(password.value)
        if (!result) return;
        checkPassword.value = true
    }
    // 3 이메일 중복 확인
    if (step.value == 2) {
        if (!checkEmail.value) return;
    }

    if (step.value < 3) {
        step.value++
    }
}

const stepDown = () => {
    if (step.value > 0) {
        step.value--
    }
}

// 이메일 인증
const code = ref('')
const isSend = ref(false)
const isSending = ref(false)

const send = async () => {
    const isUnique = await store.checkEmail(email.value)
    if(!isUnique) return
    isSending.value = true    
    const success = await store.send(email.value)
    if (success) {
        isSend.value = true;
    }
    isSending.value = false
}

const verify = async () => {
    const success = await store.verify(code.value)
    if (success) {
        checkEmail.value = true
    }
}

// 회원가입
const regist = async () => {
    if (checkUserId.value && checkPassword.value && checkEmail.value && userName.value.trim() !== '') {
        const result = await store.regist(userId.value, password.value, email.value, userName.value)
        if (result) {
            alert(store.msg)
            emit('close')
        }
    }
}
</script>

<style scoped>
.new {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    height: 100%;
}

.inputs {
    margin-bottom: 10px;
    display: flex;
    flex-direction: column;
    align-items: space-between;
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

.auth-button button {
    width: 100%;
    height: 40px;
    border: 0;
    border-radius: 8px;
    color: #ffffff;
    background-color: #4a90e2;
}

.auth-button button:hover {
    background-color: #357ABD;
}

.two-button-box {
    display: flex;
    justify-content: space-between;
}

.title {
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    margin-bottom: 20px;
}

.title p {
    margin-top: 0;
    margin-bottom: 10px;
}

.title span {
    cursor: pointer;
}

.title span.active {
    font-weight: bold;
    color: #4a90e2;
}

.email-input {
    display: flex;
    gap: 8px;
    margin-bottom: 10px;
    flex-wrap: wrap;
}

.email-input input {
    flex: 1 1 65%;
    min-width: 180px;
}

.email-input button {
    flex: 1 1 30%;
    min-width: 100px;
    height: 40px;
    border: none;
    background-color: #4a90e2;
    color: white;
    border-radius: 8px;
    cursor: pointer;
}

.email-input button:hover {
    background-color: #357ABD;
}
</style>