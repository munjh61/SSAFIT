<template>
    <div>
        <Teleport to="body">
            <div class="modal-overlay" @click.self="closeModal">
                <div class="modal">
                </div>
            </div>
        </Teleport>
    </div>
</template>

<script setup>

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
</style>