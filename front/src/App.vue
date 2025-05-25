<script setup>
import { onMounted } from 'vue';
import { RouterView } from 'vue-router'
import { useAuthStore } from './stores/auth';

const authStore = useAuthStore()

onMounted(()=>{
  authStore.me()
})
</script>

<template>
  <header>
    <div id="app">
      <RouterView />
    </div>
  </header>
</template>

<style scoped>
* {
  box-sizing: border-box;
}
/* .container{
  max-width: 2000px;
  margin: 0 auto;
  padding: 0 16px;
} */
body {
  margin: 0;
  padding: 0;
  font-family: 'Noto Sans KR', sans-serif;
  background-color: #fff;
  color: #000;
}
a {
  text-decoration: none;
  color: inherit;
}
button {
  cursor: pointer;
}
</style>
