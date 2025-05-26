<template>
    <div class="card">
        <div class="info">
            <h3>{{ props.fName }}</h3>
            <p> @{{ props.fUserId }}</p>
        </div>
        <template v-if="props.isLoginUser">
            <button v-if="isF" @click="del">언팔로우</button>
            <button v-if="!isF" @click="add">팔로우</button>
        </template>
    </div>
</template>

<script setup>
import { useFollowStore } from '@/stores/follow';
import { onMounted, ref } from 'vue';
const props = defineProps({
    fId: Number,
    fUserId: String,
    fName: String,
    isFollowed: Boolean,
    isLoginUser: Boolean
})

const emit = defineEmits(['addFollow', 'delFollow'])

const followStore = useFollowStore();

const add = async () => {
    const result = await followStore.addFollow(props.fUserId)
    if (result) {
        emit('addFollow')
        isF.value = !isF.value
    }

}

const del = async () => {
    const result = await followStore.deleteFollow(props.fUserId)
    if (result) {
        isF.value = !isF.value
        emit('delFollow')
    }
}

const isF = ref(false)

onMounted(() => {
    isF.value = props.isFollowed
})

</script>

<style scoped>
.card {
    width: 200px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 10px 20px;
    border-radius: 12px;
    background-color: #f9f9f9;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    margin-bottom: 10px;
}

.info {
    flex: 1;
    display: flex;
    flex-direction: column;
    align-items: center;
}

.info h3 {
    margin: 0;
    font-size: 18px;
    color: #222;
}

.info p {
    margin: 2px 0 0 0;
    font-size: 14px;
    color: #888;
    font-weight: 500;
}

button {
    background-color: #007bff;
    color: white;
    border: none;
    border-radius: 8px;
    padding: 6px 12px;
    font-size: 14px;
    cursor: pointer;
    transition: background-color 0.2s ease;
    white-space: nowrap;
}

button:hover {
    background-color: #0056b3;
    transform: translateY(-1px);
    box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15);
}
</style>