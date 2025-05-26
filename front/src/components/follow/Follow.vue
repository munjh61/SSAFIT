<template>
    <div>
        <Teleport to="body">
            <div class="modal-overlay" @click.self="closeModal">
                <div class="modal">
                    <div class="logo">
                        <img src="/src/assets/images/SSAFIT.png" alt="" />
                    </div>

                    <div class="mode-list">
                        <p>
                            <span :class="{ active: props.mode === 'follower' }" @click="changeMode('follower')">
                                팔로워 ({{ followerCnt }})
                            </span>
                            ｜
                            <span :class="{ active: props.mode === 'following' }" @click="changeMode('following')">
                                팔로잉 ({{ followingCnt }})
                            </span>
                        </p>
                    </div>

                    <div class="input-container">
                        <input type="text" v-model="search" placeholder="검색어 입력" />
                    </div>

                    <div class="main-contents">
                        <!-- 서로 팔로우 -->
                        <div v-for="mutual in filteredMutual" :key="mutual.followId">
                            <FollowCard :f-id="mutual.followId"
                                :f-user-id="mutual.followerId === props.userId ? mutual.followingId : mutual.followerId"
                                :f-name="mutual.followerId === props.userId ? mutual.followingName : mutual.followerName"
                                :is-followed="true" :is-login-user="isLoginUser" @addFollow="addFollow"
                                @delFollow="delFollow" />
                        </div>

                        <!-- 나를 팔로우 -->
                        <div v-if="props.mode === 'follower'" v-for="f in filteredFollower" :key="f.followId">
                            <FollowCard :f-id="f.followId" :f-user-id="f.followerId" :f-name="f.followerName"
                                :is-followed="false" :is-login-user="isLoginUser" @addFollow="addFollow"
                                @delFollow="delFollow" />
                        </div>

                        <!-- 내가 팔로우 -->
                        <div v-if="props.mode === 'following'" v-for="f in filteredFollowing" :key="f.followId">
                            <FollowCard :f-id="f.followId" :f-user-id="f.followingId" :f-name="f.followingName"
                                :is-followed="true" :is-login-user="isLoginUser" @addFollow="addFollow"
                                @delFollow="delFollow" />
                        </div>
                    </div>
                </div>
            </div>
        </Teleport>
    </div>
</template>


<script setup>
import { ref, computed, onMounted } from 'vue'
import { useFollowStore } from '@/stores/follow'
import FollowCard from './FollowCard.vue'
import { useAuthStore } from '@/stores/auth'

const props = defineProps({
    mode: String,
    userId: String
})

const emit = defineEmits(["close", "changeMode"])

const store = useFollowStore()
const authstore = useAuthStore()

const search = ref('')
const followerCnt = ref(0)
const followingCnt = ref(0)

const isLoginUser = computed(() => authstore.userId === props.userId)

const closeModal = () => emit('close')

const changeMode = (mode) => {
    emit('changeMode', mode)
    store.getFollowData(props.userId)
}

const addFollow = () => followingCnt.value++
const delFollow = () => followingCnt.value--

onMounted(async () => {
    await store.getFollowData(props.userId)
    followerCnt.value = store.followerCnt
    followingCnt.value = store.followingCnt
})

// computed로 검색 필터링
const query = computed(() => search.value.toLowerCase())

const filteredMutual = computed(() =>
    store.mutual.filter(f => {
        const isFollower = f.followerId === props.userId &&
            (f.followingName?.toLowerCase().includes(query.value) || f.followingId?.toLowerCase().includes(query.value))
        const isFollowing = f.followingId === props.userId &&
            (f.followerName?.toLowerCase().includes(query.value) || f.followerId?.toLowerCase().includes(query.value))
        return isFollower || isFollowing
    })
)

const filteredFollower = computed(() =>
    store.follower.filter(f =>
        f.followerName?.toLowerCase().includes(query.value) ||
        f.followerId?.toLowerCase().includes(query.value)
    )
)

const filteredFollowing = computed(() =>
    store.following.filter(f =>
        f.followingName?.toLowerCase().includes(query.value) ||
        f.followingId?.toLowerCase().includes(query.value)
    )
)
</script>

<style scoped>
/* 모달 바깥 */
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

/* 모달 본체 */
.modal {
    width: 400px;
    min-height: 60%;
    max-height: 90vh;
    background-color: #ffffff;
    border: 1px solid #4a90e2;
    border-radius: 20px;
    padding: 20px 20px 10px;
    display: flex;
    flex-direction: column; 
    align-items: center;
    overflow: hidden;
    animation: slideUp 0.3s ease;
}

/* 로고 이미지 영역 */
.logo {
    margin-top: 10px;
    margin-bottom: 10px;
    height: 100px;
}

.logo img {
    width: 200px;
    height: 100px;
    object-fit: contain;
}

/* 팔로워/팔로잉 탭 */
.mode-list {
    margin-bottom: 10px;
}

.mode-list span {
    cursor: pointer;
    margin: 0 5px;
}

.mode-list span.active {
    font-weight: bold;
    color: #4a90e2;
}

/* 메인 컨텐츠 스크롤 영역 */
.main-contents {
    width: 100%;
    flex-grow: 1;
    overflow-y: auto;
    max-height: 100%;
    padding-right: 6px;
    display: flex;
    flex-direction: column;
    align-items: center;
}

/* 스크롤바 꾸미기 */
.main-contents::-webkit-scrollbar {
    width: 6px;
}

.main-contents::-webkit-scrollbar-thumb {
    background-color: #ccc;
    border-radius: 4px;
}

.main-contents::-webkit-scrollbar-track {
    background-color: transparent;
}

.input-container {
  width: 70%;
  margin: 10px 0px
}

.input-container input {
  width: 100%;
  padding: 10px 14px;
  border: 1px solid #ccc;
  border-radius: 12px;
  background-color: #f9f9f9;
  font-size: 16px;
  box-sizing: border-box;
  outline: none;
  transition: border-color 0.2s ease, box-shadow 0.2s ease;
}

.input-container input:focus {
  border-color: #4a90e2;
  box-shadow: 0 0 0 2px rgba(76, 175, 80, 0.2);
  background-color: white;
}


</style>