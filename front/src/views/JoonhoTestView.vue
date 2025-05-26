<template>
  <HeaderBar/>
  <div>
    <button @click="openFollowModal('follower')">팔로워</button>
    <button @click="openFollowModal('following')">팔로잉</button>
  </div>

  <Follow v-if="showFollowModal" :mode="mode" @close="closeFollowModal" @changeMode="changeMode"/>
</template>

<script setup>
import { ref } from 'vue'
import Follow from '@/components/follow/Follow.vue'
import HeaderBar from '@/components/HeaderBar.vue'

const showFollowModal = ref(false)
const mode = ref('follower')

const openFollowModal = (selectedMode) => {
  mode.value = selectedMode
  showFollowModal.value = true
}

const closeFollowModal = () => {
  showFollowModal.value = false
}

const changeMode = function (q){
  mode.value = q
}
</script>

<style scoped>

</style>