import { ref } from "vue";
import { defineStore } from "pinia";
import axios from "axios";

const serverUrl = import.meta.env.VITE_API_BASE_URL

export const useAuthStore = defineStore('auth', () => {
    const msg = ref('')
    const isLoggedIn = ref(false)
    const logout = function () {
        sessionStorage.removeItem('ssafit-login-token')
        isLoggedIn.value = false
    }
    const login = function (userId, password) {
        return axios({
            url: `${serverUrl}/api/auth/login`,
            method: "POST",
            data: {
                userId,
                password
            }
        })
            .then((res) => {
                let token = res.data;
                sessionStorage.setItem("ssafit-login-token", token);
                msg.value = "로그인 성공"
                me()
                return true;
            })
            .catch((err) => {
                msg.value = err.response.data
                return false;
            })
    }
    const userId = ref('')
    const userName = ref('')

    const me = async function () {
        if (!sessionStorage.getItem('ssafit-login-token'))
            return
        await axios({
            url: `${serverUrl}/api/user/me`,
            method: "GET",
            headers: { Authorization: `Bearer ${sessionStorage.getItem("ssafit-login-token")}` }
        })
            .then((res) => {
                isLoggedIn.value = true
                userId.value = res.data.userId
                userName.value = res.data.userName
            })
    }
    const resetMsg = () => {
        msg.value = ref('')
    }

    return {
        isLoggedIn, msg, userId, userName, login, logout, resetMsg, me
    }
})