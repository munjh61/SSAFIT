import { ref } from "vue";
import { defineStore } from "pinia";
import axios from "axios";

const serverUrl = import.meta.env.VITE_API_BASE_URL

export const useAuthFindStore = defineStore('authFind', ()=>{
    const verifiedEmail = ref('')

    const sendFindId = () => {
        return axios({
            url: `${serverUrl}/api/public/user/email/request}`,
            method: "POST",
            data:{
                address
            }
        })
    }

    const sendResetPw = () => {
        return axios({
            url: `${serverUrl}/api/public/user/email/request}`,
            method: "POST",
            data:{
                address,
                userId,
            }
        })
    }

    return {
        verifiedEmail,        
    }
})