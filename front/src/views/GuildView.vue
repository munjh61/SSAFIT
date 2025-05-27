<template>
    <div class="guild-view">
        <HeaderBar />

        <div class="img-section">
            <div class="overlay" />
            <div class="search-container">
                <input type="text" v-model="store.searchKeyword" @keydown.enter="search" placeholder="모임 이름을 검색하세요"
                    class="select" />
                <button @click="search"> 🔍 </button>
            </div>
        </div>

        <div class="content-section">
            <div class="list-header">
                <div class="filter-bar">
                    <button :class="{ active: !showMineOnly }" @click="showMineOnly = false">전체 보기</button>
                    <button :class="{ active: showMineOnly }" @click="showMineOnly = true">내 모임만</button>
                </div>
                <button class="create-btn" @click="showCreateModal = true">+ 모임 생성</button>
            </div>

            <div class="guild-list">
                <GuildCard v-for="guild in filteredGuilds" :key="guild.guildId" v-bind="guild" @del="store.deleteGuild(guild.guildId)"/>
            </div>
        </div>

        <GuildDetailModal v-if="showDetailModal" :guild-id="selectedId" @close="showDetailModal = false" />
        <GuildCreateModal v-if="showCreateModal" @close="showCreateModal = false" />
    </div>
</template>

<script setup>
import GuildCard from '@/components/guild/GuildCard.vue';
import GuildCreateModal from '@/components/guild/GuildCreateModal.vue';
import GuildDetailModal from '@/components/guild/GuildDetailModal.vue';
import HeaderBar from '@/components/HeaderBar.vue';
import { useGuildStore } from '@/stores/guildForm';
import { computed, onMounted, ref } from 'vue';

const store = useGuildStore();

onMounted(() => {
    store.getList('')
})

const showMineOnly = ref(false)

const filteredGuilds = computed(() => {
    const keyword = store.searchKeyword.toLowerCase()
    return store.guilds.filter(g => g.guildName.toLowerCase().includes(keyword) || g.description.toLowerCase().includes(keyword)
    )
})
const search = () => {
    store.getList(store.searchKeyword)
}
const selectedId = ref(0)
const showDetailModal = ref(false)
const showCreateModal = ref(false)
</script>

<style scoped>
/* 기존 스타일 그대로 유지 */
.guild-view {
    display: flex;
    flex-direction: column;
    align-items: center;
}

.img-section {
    position: relative;
    min-width: 1600px;
    min-height: 553px;
    background-image: url('@/assets/images/mainImage.jpg');
    background-size: cover;
    background-position: center;
    display: flex;
    justify-content: center;
    align-items: center;
    overflow: hidden;
    box-shadow: 0 2px 8px #D9D9D9;
}

.overlay {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(255, 255, 255, 0.2);
    z-index: 1;
}

.content-section {
    width: 100%;
    max-width: 1200px;
    padding: 40px 16px;
}

.search-container {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 758px;
    height: 65px;
    margin: 30px auto;
    border: 1px solid #00aacc;
    border-radius: 999px;
    background-color: #fff;
    padding: 0 10px;
    box-sizing: border-box;
    z-index: 2;
}

.search-container input {
    flex: 1;
    border: none;
    outline: none;
    font-size: 16px;
    background-color: transparent;
    padding: 0 16px;
}

.search-container button {
    background: none;
    border: none;
    font-size: 20px;
    color: #00aacc;
    cursor: pointer;
    padding: 0 10px;
}

.search-container button:hover {
    opacity: 0.8;
}

.filter-bar {
    display: flex;
    justify-content: flex-end;
    margin-bottom: 16px;
    gap: 8px;
}

.filter-bar button {
    padding: 6px 12px;
    border-radius: 6px;
    border: none;
    background-color: #eee;
    cursor: pointer;
}

.filter-bar button.active {
    background-color: #2f80ed;
    color: white;
}

.guild-list {
    display: flex;
    flex-direction: column;
    gap: 16px;
}

.list-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 16px;
}

.create-btn {
    background-color: #2f80ed;
    color: white;
    padding: 8px 14px;
    border-radius: 6px;
    font-size: 0.95rem;
    text-decoration: none;
    transition: background-color 0.2s;
}

.create-btn:hover {
    background-color: #1664c0;
}
</style>
