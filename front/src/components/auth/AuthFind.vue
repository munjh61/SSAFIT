<template>
    <div class="find">
        <p>
            <span :class="{ active: problem === 'id' }" @click="mode = 'id'">아이디 찾기</span> ｜
            <span :class="{ active: problem === 'pw' }" @click="mode = 'pw'">비밀번호 재설정</span> ｜
        </p>
        <div class="inputs">
            <div class="input-container">
                <fieldset>
                    <legend>인증 이메일</legend>
                    <input type="email" v-model="email">
                </fieldset>
            </div>
            <div class="input-container">
                <fieldset>
                    <legend>인증 이메일</legend>
                    <input type="email" v-model="email">
                </fieldset>
            </div>
            <div class="input-container">
                <fieldset>
                    <legend>인증코드</legend>
                    <input type="text" v-model="code">
                </fieldset>
            </div>
            <div>
                <button class="find-button">메일 발송</button>
                <button class="find-button">인증</button>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref } from "vue";
const email = ref('')
const code = ref('')
</script>

<style scoped>
.inputs {
    margin-bottom: 30px;
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
    width: 49%;
    height: 40px;
    border: 0;
    border-radius: 8px;
    color: #ffffff;
    background-color: #4a90e2;
}

.find-button:hover {
    background-color: #357ABD;
}
</style>