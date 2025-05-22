<template>
    <div class="record-grid">
        <div class="record" v-for="record in records" :key="record.id">
            <img :src="record.img" :alt="record.caption" />
        </div>
    </div>
</template>

<script setup>
defineProps({
    records: Array
})
</script>

<style scoped>
.record-grid{
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: 16px;
}
.record img{
    width: 100%;
    height: 200px;
    object-fit: cover;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}
</style>