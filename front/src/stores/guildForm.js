import { ref } from "vue";
import { defineStore } from "pinia";
import axios from "axios";
import { useAuthStore } from "./auth";

const serverUrl = import.meta.env.VITE_API_BASE_URL;

export const useGuildStore = defineStore("guild", () => {
    const authStore = useAuthStore();
    const searchKeyword = ref('')
    const guildName = ref("");
    const description = ref("");
    const msg = ref("");
    const guilds = ref([]);
    const regist = async () => {
        await authStore.me();
        if (!authStore.isLoggedIn) {
            msg.value = '로그인 해주세요'
            return;
        }
        return await axios({
            url: `${serverUrl}/api/guild`,
            method: "POST",
            headers: {
                Authorization: `Bearer ${sessionStorage.getItem("ssafit-login-token")}`,
            },
            data: {
                userId: authStore.userId,
                guildName: guildName.value,
                description: description.value,
            },
        })
            .then((res) => {
                msg.value = res.data;
                return true
            })
            .catch((err) => {
                msg.value = err.response.data;
                return false
            });
    };

    const getList = async (q) => {
        await axios({
            url: `${serverUrl}/api/public/guild?q=${q}`,
            method: "GET",
        })
            .then((res) => {
                guilds.value = [...res.data];
            })
            .catch((err) => {
                console.log(err.response.data);
            });
    };

    const deleteGuild = async (guildId) => {
        return await axios({
            url: `${serverUrl}/api/guild/${guildId}`,
            method: "DELETE",
            headers: {
                Authorization: `Bearer ${sessionStorage.getItem('ssafit-login-token')}`
            }
        })
            .then(getList(searchKeyword.value))
    }

    const reset = () => {
        guildName.value = "";
        description.value = "";
    };

    return { searchKeyword, guildName, description, msg, guilds, regist, getList, deleteGuild, reset };
});
