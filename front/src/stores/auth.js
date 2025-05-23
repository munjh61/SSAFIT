import { ref } from "vue";
import { defineStore } from "pinia";
import axios from "axios";

const serverUrl = import.meta.env.VITE_API_BASE_URL

export const useAuthStore = defineStore('auth', ()=>{
    const msg = ref('')

    const login = function(userId, password){
        return axios({
            url: `${serverUrl}/api/auth/login`,
            method: "POST",
            data:{
                userId,
                password
            }
        })
        .then((res)=>{
            let token = res.data;
            sessionStorage.setItem("ssafit-login-token", token);
            msg.value = "로그인 성공"
            return true;
        })
        .catch((err)=>{
            msg.value = err.response.data
            return false;
        })
    }

    const resetMsg = () => {
        msg.value = ref('')
    }

    return {
        login, msg, resetMsg
    }
})