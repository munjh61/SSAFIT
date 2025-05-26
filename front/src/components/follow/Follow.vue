<template>
    <div>
        <Teleport to="body">
            <div class="modal-overlay" @click.self="closeModal">
                <div class="modal">
                    <div class="logo">
                        <img src="/src/assets/images/SSAFIT.png" alt="">
                    </div>
                    <div class="mode-list">
                        <p>
                            <span :class="{ active: props.mode === 'follower' }" @click="changeMode('follower')">팔로워</span>｜
                            <span :class="{ active: props.mode === 'following' }" @click="changeMode('following')">팔로잉</span>
                        </p>
                    </div>
                    <div v-if="props.mode === 'follower'">
                        <p>팔로워리스트</p>
                    </div>
                    <div v-if="props.mode === 'following'">
                        <p>팔로잉리스트</p>
                    </div>
                </div>
            </div>
        </Teleport>
    </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { useFollowStore } from '@/stores/follow'
const props = defineProps({
    mode: String
})
const emit = defineEmits([
    "close", "changeMode"
])

const closeModal = function () {
    emit('close')
}

const changeMode = function (q) {
    emit('changeMode', q)
}

const store = useFollowStore()

onMounted(()=>{
    store.getFollowData('user02')
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
    height: 60%;

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

.mode-list span {
    cursor: pointer;
}

.mode-list span.active {
    font-weight: bold;
    color: #4a90e2;
}
</style>