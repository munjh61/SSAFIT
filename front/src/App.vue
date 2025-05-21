<script setup>
import { RouterView } from 'vue-router'
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
