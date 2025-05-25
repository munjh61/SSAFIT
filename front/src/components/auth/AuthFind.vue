<template>
    <div class="find">
        <div class="problem">
            <p>
                <span :class="{ active: type === 'id' }" @click="problemType('id')">아이디 찾기</span> ｜
                <span :class="{ active: type === 'pw' }" @click="problemType('pw')">비밀번호 재설정</span> ｜
            </p>
            <p>{{ store.msg }}</p>
        </div>
        <div class="inputs">
            <template v-if="!isVerified">
                <div class="input-container" v-if="!isSend">
                    <fieldset>
                        <legend>인증 이메일</legend>
                        <input type="email" v-model="address">
                    </fieldset>
                </div>
                <div class="input-container" v-if="isSend">
                    <fieldset>
                        <legend>인증코드</legend>
                        <input type="text" v-model="code">
                    </fieldset>
                </div>
            </template>
            <template v-if="isVerified">
                <div class="input-container" v-if="type === 'id'">
                    <fieldset>
                        <legend>아이디</legend>
                        <p>{{ findIdResult }}</p>
                    </fieldset>
                </div>
                <div class="input-container" v-if="type === 'pw'">
                    <fieldset>
                        <legend>아이디</legend>
                        <input type="userId" v-model="userId">
                    </fieldset>
                </div>
                <div class="input-container" v-if="type === 'pw'">
                    <fieldset>
                        <legend>새로운 비밀번호</legend>
                        <input type="text" v-model="password">
                    </fieldset>
                </div>
            </template>
        </div>
        <div class="auth-button">
            <button v-if="!isSend" @click="send">
                <img src="@/assets/images/spinner.svg" v-if="isSending">
                <span v-if="!isSending">메일 발송</span>
            </button>
            <div class="two-button-box">
                <button style="width: 49%;" v-if="isSend && !isVerified" @click="problemType(type)">뒤로</button>
                <button style="width: 49%;" v-if="isSend && !isVerified" @click="verify">인증</button>
            </div>
            <button v-if="isVerified && type == 'pw'" @click="changePw">
                <img src="@/assets/images/spinner.svg" v-if="isChanging">
                <span v-if="!isChanging">비밀번호 재설정</span>
            </button>
        </div>
    </div>
</template>

<script setup>
import { ref } from "vue";
import { useAuthFindStore } from "@/stores/authFind.js";
const store = useAuthFindStore()
const type = ref('id')
const address = ref('')
const code = ref('')
const isSend = ref(false)
const isSending = ref(false)
const isVerified = ref(false)
const findIdResult = ref('')
const userId = ref('')
const password = ref('')
const isChanging = ref(false)

const emit = defineEmits(['close'])

const problemType = function (p) {
    type.value = p;
    code.value = ''
    userId.value = ''
    isSend.value = false;
    isSending.value = false;
    isVerified.value = false;
    findIdResult.value = ''
    userId.value = ''
    password.value = ''
    isChanging.value = false
    store.reset()
}

const send = async () => {
    isSending.value = true
    const success = await store.send(address.value)
    if (success) {
        isSend.value = true;
    }
    isSending.value = false
}

const verify = async () => {
    const success = await store.verify(code.value)
    if (success) {
        isVerified.value = true
        if (type.value == 'id') {
            findIdResult.value = await store.findId()
        }
    }
}

const changePw = async () => {
    isChanging.value = true
    const success = await store.changePw(userId.value, address.value, password.value)
    isChanging.value = false
    if (success) {
        emit('close')
    }
}

</script>

<style scoped>
.find {
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

.two-button-box{
    display: flex;
    justify-content: space-between;
}

.problem {
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    margin-bottom: 20px;
}

.problem p {
    margin-top: 0;
    margin-bottom: 10px;
}

.problem span {
    cursor: pointer;
}

.problem span.active {
    font-weight: bold;
    color: #4a90e2;
}
</style>