<template>
    <Teleport to="body">
        <div class="modal-overlay" @click.self="closeModal">
            <div class="modal">
                <div class="logo">
                    <img src="/src/assets/images/SSAFIT.png" alt="">
                </div>
                <div class="title">
                    <p>회원 탈퇴</p>
                    <p class="icon">🧹</p>
                </div>
                <div class="main-contents-container">
                    <div class="inputs">
                        <div class="input-container">
                            <fieldset>
                                <legend>비밀번호를 재입력 해주세요.</legend>
                                <input type="password" v-model="password">
                            </fieldset>
                        </div>
                    </div>
                    <button @click="quit">회원 탈퇴</button>
                </div>
            </div>
        </div>
    </Teleport>
</template>

<script setup>
import { useAuthStore } from '@/stores/auth'
import { ref } from 'vue'
import { useRouter } from 'vue-router'
const store = useAuthStore()
const emit = defineEmits(["close"])
const password = ref('')
const router = useRouter()
const closeModal = () => emit('close')

const quit = async () => {
    const success = await store.quit(password.value)
    if (success) {
        emit('close')
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
.main-contents-container{
    display: flex;
    flex-grow: 1;
    flex-direction: column;
    justify-content: space-between;
    padding: 30px;
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
</style>