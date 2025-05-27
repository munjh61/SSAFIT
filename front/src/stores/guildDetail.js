import { ref } from "vue";
import { defineStore } from "pinia";
import axios from "axios";
import { useAuthStore } from "./auth";

const serverUrl = import.meta.env.VITE_API_BASE_URL;

export const useGuildDetailStore = defineStore("guildDetail", () => {

    const guildId = ref(1)
    const guildName = ref("");
    const description = ref("");
    const owner = ref('')
    const crews = ref([])
    const candidates = ref([])
    const aStore = useAuthStore()
    const isLoggedIn = ref(false)
    const loginUser = ref('')
    const isOwner = ref(false)
    const msg = ref('')
    const isMember = ref(false);

    const start = async (inputGuildId) => {
        await aStore.me()
        isLoggedIn.value = aStore.isLoggedIn
        loginUser.value = aStore.userId
        isOwner.value = false
        await getDetail(inputGuildId);
    }

    const getDetail = async (inputGuildId) => {
        guildId.value = inputGuildId
        if (!isLoggedIn.value) {
            return await axios({
                url: `${serverUrl}/api/public/guild/${inputGuildId}`,
                method: "GET"
            })
                .then((res) => {
                    console.log(res.data)
                    let guild = res.data
                    guildName.value = guild.guildName
                    description.value = guild.description
                    owner.value = guild.userId
                    isOwner.value = false
                    crews.value = []
                })
                .catch((err) => {
                    console.log(err)
                    console.log('모임 디테일을 불러오는데 실패하였습니다.1')
                })
        }
        else {
            return await axios({
                url: `${serverUrl}/api/guild/${inputGuildId}`,
                method: "GET",
                headers: {
                    Authorization: `Bearer ${sessionStorage.getItem('ssafit-login-token')}`
                }
            })
                .then((res) => {
                    let guild = res.data.guild
                    guildName.value = guild.guildName
                    description.value = guild.description
                    owner.value = guild.userId
                    isOwner.value = guild.userId == aStore.userId
                    const all = res.data.crews
                    if (all != null) {
                        crews.value = all.filter(a => a.status === 0);
                        candidates.value = all.filter(a => a.status !== 0);
                        isMember.value = all.some(a => a.userId === aStore.userId && a.status === 0);
                    }
                })
                .catch((err) => {
                    console.log(err);
                    console.log('모임 디테일을 불러오는데 실패하였습니다.2');
                })

        }
    }

    const apply = async (inputGuildId) => {
        await aStore.me();
        if (!aStore.isLoggedIn) {
            msg.value = '로그인 해주세요'
            return;
        }
        return await axios({
            url: `${serverUrl}/api/crew/apply`,
            method: 'POST',
            data: {
                guildId: inputGuildId,
                userId: aStore.userId
            },
            headers: {
                Authorization: `Bearer ${sessionStorage.getItem('ssafit-login-token')}`
            }
        })
            .then((res) => {
                msg.value = res.data.msg
            })
            .catch((err) => {
                msg.value = err.response.data
            })
    }

    const applyManage = async (inputGuildId, targetId, manage) => {
        return await axios({
            url: `${serverUrl}/api/crew/${manage}`,
            method: 'POST',
            data: {
                guildId: inputGuildId,
                userId: targetId
            },
            headers: {
                Authorization: `Bearer ${sessionStorage.getItem('ssafit-login-token')}`
            }
        })
            .then((res) => {
                msg.value = res.data.msg;
                return res.data.success;
            })
            .catch((err) => {
                msg.value = "요청 중 오류가 발생했습니다.";
                return false;
            });
    }


    const quit = async (inputGuildId) => {
        return await axios({
            url: `${serverUrl}/api/crew/${inputGuildId}`,
            method: 'DELETE',
            headers: {
                Authorization: `Bearer ${sessionStorage.getItem('ssafit-login-token')}`
            }
        })
            .then((res) => {
                msg.value = res.data
            })
            .catch((err) => {
                msg.value = err.response.data
            })
    }

    return { guildId, guildName, description, owner, crews, candidates, isOwner, isMember, msg, start, getDetail, apply, applyManage, quit }
})