<template>
    <div class="record-container">
        <button class="record-btn" @click="emit('write')">기록하기</button>
        <div class="record-grid">
            <div class="record" v-for="record in records" :key="record.id">
                <img :src="record.img" :alt="record.caption" />
            </div>
        </div>
    </div>
</template>

<script setup>
defineProps({
    records: Array
})

const emit = defineEmits(['write'])
</script>

<style scoped>
.record-container{
    position: relative;
    padding-top: 3rem;
}
.record-btn{
    position: absolute;
    top: 0;
    right: 0;
    width:140px;
    padding: 8px 16px;
    background-color: #ffffff;
    color: #10217D;
    border: 1px solid #10217D;
    border-radius: 4px;
    cursor: pointer;
    font-weight: bold;
    font-size: 15px;
}
.record-grid{
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: 25px;
}
.record img{
    width: 300px;
    height: 300px;
    object-fit: cover;
    border-radius: 1px;
    box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}
</style>