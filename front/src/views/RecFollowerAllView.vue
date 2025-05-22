<template>
    <div class="container">
        <HeaderBar />
        <section class="img-section">
            <div class="overlay"></div>
            <div class="search-wrapper">
                <SearchBar />
            </div>
        </section>


    </div>
</template>

<script setup>
import HeaderBar from '@/components/HeaderBar.vue';
import SearchBar from '@/components/SearchBar.vue';

</script>

<style scoped>
.img-section {
  position: relative;
  height: 553px;
  background-image: url('@/assets/images/mainImage.jpg');
  background-size: cover;
  background-position: center;
  display: flex;
  justify-content: center;
  align-items: center;
  overflow: hidden;
  box-shadow: 0 2px 8px #D9D9D9;
}
.overlay{
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(255, 255, 255, 0.2);
  z-index: 1;
}
</style>