<template>
    <div class="login">
        <div class="inputs">
            <div class="input-container">
                <fieldset>
                    <legend>아이디</legend>
                    <input type="text" v-model="userId">
                </fieldset>
            </div>
            <div class="input-container">
                <fieldset>
                    <legend>비밀번호</legend>
                    <input type="password" v-model="password">
                </fieldset>
            </div>
        </div>
        <p>{{ store.msg }}</p>
        <button class="login-button" @click="login">로그인</button>
    </div>
</template>

<script setup>
import { ref } from "vue";
import { useAuthStore } from "@/stores/auth.js";
import { useRouter } from "vue-router";

const userId = ref('')
const password = ref('')
const store = useAuthStore()
const router = useRouter();

const emit = defineEmits([
    "close",
])

const login = async () => {
    let result = await store.login(userId.value, password.value);
    if(result){
        emit('close')
        router.replace({name : 'main'})
    }
}

</script>

<style scoped>
.login {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 20px;
}

.inputs{
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

/* 넓이를 input container에만 줘서 나머지 %들이 맞춰짐 */
.login-button {
    width: 100%;
    height: 40px;
    border: 0;
    border-radius: 8px;
    color: #ffffff;
    background-color: #4a90e2;
}

.login-button:hover {
    background-color: #357ABD;
}
</style>