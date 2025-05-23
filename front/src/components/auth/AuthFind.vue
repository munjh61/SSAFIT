<template>
    <div class="find">
        <div class="problem">
            <p>
                <span :class="{ active: type === 'id' }" @click="problemType('id')">아이디 찾기</span> ｜
                <span :class="{ active: type === 'pw' }" @click="problemType('pw')">비밀번호 재설정</span> ｜
            </p>
        </div>
        <div class="inputs">
            <div class="input-container" v-if="!isSend">
                <fieldset>
                    <legend>인증 이메일</legend>
                    <input type="email" v-model="email">
                </fieldset>
            </div>
            <div class="input-container" v-if="type == 'pw' && !isSend">
                <fieldset>
                    <legend>아이디</legend>
                    <input type="userId" v-model="userId">
                </fieldset>
            </div>
            <div class="input-container" v-if="isSend">
                <fieldset>
                    <legend>인증코드</legend>
                    <input type="text" v-model="code">
                </fieldset>
            </div>
        </div>
        <div>
            <button class="find-button" v-if="!isSend" @click="send">메일 발송</button>
            <button class="find-button" v-if="isSend" @click="verify">인증</button>
        </div>
    </div>
</template>

<script setup>
import { ref } from "vue";
import { useAuthFindStore } from "@/stores/authfind";
const email = ref('')
const code = ref('')
const userId = ref('')
const type = ref('id')
const isSend = ref(false)
const store = useAuthFindStore()

const problemType = function(p){
    type.value = p;
    isSend.value = false;
    store.verifiedEmail.value = '';
}

const send = () =>{
    isSend.value = !isSend.value;
}

const verify = () =>{

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
    margin-bottom: 30px;
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

.find-button {
    width: 100%;
    height: 40px;
    border: 0;
    border-radius: 8px;
    color: #ffffff;
    background-color: #4a90e2;
}

.find-button:hover {
    background-color: #357ABD;
}

.problem {
    display: flex;
    justify-content: center;
}

.problem p {
    margin-top: 0;
    margin-bottom: 20px;
}

.problem span {
    cursor: pointer;
}

.problem span.active {
    font-weight: bold;
    color: #4a90e2;
}
</style>