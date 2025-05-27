<template>
    <div class="guild-card">
        <!-- 왼쪽: 프로필 이미지 -->
        <img class="guild-img" src="/src/assets/images/SSAFIT.png" />

        <!-- 오른쪽: 텍스트 정보 -->
        <div class="guild-info" @click="openDetail">
            <h2>
                {{ guildName }}
                <span class="headcount-badge">{{ formatHeadCount(headCount) }}</span>
            </h2>
            <p>설립자 {{ userId }} ｜ 설립일 {{ regDate.split('T')[0] }}</p>
            <p>{{ description }}</p>

        </div>

        <!-- 버튼: 소유자면 해체, 아니면 신청 -->
        <!-- 버튼 영역 -->
        <div class="action">
            <button v-if="isOwner" class="danger" @click.self="del">해체하기</button>
            <button v-else-if="!isMember" class="apply" @click.self="apply">신청하기</button>
            <span v-else>✔️ 가입됨</span>
        </div>

    </div>
</template>

<script setup>
import { computed, ref } from 'vue';

const props = defineProps({
    guildId: Number,
    guildName: String,
    description: String,
    userId: String, //owner
    regDate: [Date, String],
    headCount: Number,
})

const emit = defineEmits(['del', 'openDetail'])

const del = () => {
    emit('del')
}

const apply = async () => {
    await store.apply(props.guildId)
    alert(store.msg)
}

const openDetail = () => {
    emit('openDetail', props.guildId)
}

function formatHeadCount(count) {
    if (count >= 1_000_000_000) return (count / 1_000_000_000).toFixed(1).replace(/\.0$/, '') + 'B';
    if (count >= 1_000_000) return (count / 1_000_000).toFixed(1).replace(/\.0$/, '') + 'M';
    if (count >= 1_000) return (count / 1_000).toFixed(1).replace(/\.0$/, '') + 'K';
    return count.toString();
}

import { useAuthStore } from '@/stores/auth'
import { useGuildDetailStore } from '@/stores/guildDetail';
const store = useGuildDetailStore()
const authStore = useAuthStore()
const isOwner = computed(() => authStore.userId === props.userId)
const isMember = ref(false) // 아직은 더미 상태, 나중에 API로 확인 가능

</script>

<style scoped>
.guild-card {
    display: flex;
    align-items: center;
    gap: 20px;
    border: 1px solid #ddd;
    border-radius: 12px;
    padding: 16px 20px;
    background-color: #fff;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.05);
    transition: transform 0.2s ease;
    margin-bottom: 16px;
}

.guild-info:hover {
    cursor: pointer;
}

.guild-img {
    width: 100px;
    height: 100px;
    border-radius: 10px;
    object-fit: cover;
    background-color: #f0f0f0;
}

.guild-info {
    flex: 1;
}

.guild-info h2 {
    font-size: 1.4rem;
    margin: 0 0 8px 0;
    color: #333;
    white-space: nowrap;
}

.guild-info p {
    margin: 4px 0;
    color: #666;
    font-size: 0.95rem;
    white-space: nowrap;
}

.headcount-badge {
    display: inline-flex;
    align-items: center;
    justify-content: center;
    margin-left: 10px;
    width: 34px;
    height: 34px;
    border-radius: 50%;
    font-size: 0.8rem;
    font-weight: bold;
    color: white;
    background-color: dodgerblue;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    vertical-align: middle;
}

.action {
    display: flex;
    flex-direction: column;
    align-items: flex-end;
    gap: 6px;
}

button.apply {
    padding: 6px 12px;
    background-color: #2f80ed;
    color: white;
    border: none;
    border-radius: 6px;
    cursor: pointer;
    font-size: 0.9rem;
}

button.danger {
    padding: 6px 12px;
    background-color: #eb5757;
    color: white;
    border: none;
    border-radius: 6px;
    cursor: pointer;
    font-size: 0.9rem;
}

button:hover {
    opacity: 0.9;
}

.action span {
    font-size: 1.2rem;
    color: #27ae60;
}
</style>