<template>
    <Teleport to="body">
        <div class="modal-overlay" @click.self="closeModal">
            <div class="modal">
                <div class="logo">
                    <img src="/src/assets/images/SSAFIT.png" alt="">
                </div>
                <div v-if="mode == 'login'">
                    <p class="icon">🔐</p>
                </div>
                <div v-if="mode == 'find'">
                    <p class="icon">🔍</p>
                </div>
                <div v-if="mode == 'join'">
                    <p class="icon">🖊️</p>
                </div>
                <div class="mode">
                    <div v-if="mode == 'login'">
                        <AuthLogin @close="closeModal" />
                    </div>
                    <div v-if="mode == 'find'">
                        <AuthFind @close="closeModal" />
                    </div>
                    <div v-if="mode == 'join'">
                        <AuthNew @close="closeModal"></AuthNew>
                    </div>
                </div>
                <div class="mode-list">
                    <p>
                        <span :class="{ active: mode === 'login' }" @click="mode = 'login'">로그인</span> ｜
                        <span :class="{ active: mode === 'join' }" @click="mode = 'join'">회원가입</span> ｜
                        <span :class="{ active: mode === 'find' }" @click="mode = 'find'">아이디/비밀번호 찾기</span>
                    </p>
                </div>
            </div>
        </div>
    </Teleport>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useAuthStore } from '@/stores/auth.js'
import AuthLogin from '@/components/auth/AuthLogin.vue'
import AuthFind from '@/components/auth/AuthFind.vue'
import AuthNew from '@/components/auth/AuthNew.vue'

const emit = defineEmits([
    "close",
])

const closeModal = function () {
    useAuthStore().resetMsg()
    emit('close')
}


const mode = ref('')

onMounted(() => {
    mode.value = 'login'
})

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

.mode {
    flex: 1;
    display: flex;
    justify-content: center;
    width: 100%;
}

.mode-list span {
    cursor: pointer;
}

.mode-list span.active {
    font-weight: bold;
    color: #4a90e2;
}

.icon {
    font-size: 40px;
    margin: 10px 0;
}
</style>