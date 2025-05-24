import { ref } from "vue";
import { defineStore } from "pinia";
import axios from "axios";

const serverUrl = import.meta.env.VITE_API_BASE_URL

export const useAuthFindStore = defineStore('authFind', ()=>{
    const verifiedEmail = ref('')

    const send = function (address) {
        return axios({
            url: `${serverUrl}/api/public/user/email/request`,
            method: "POST",
            data:{
                address
            }
        })
        .then((res)=>{
            console.log(res)
            return true;
        })
        .catch((err)=>{
            console.log(err.response.data)
            return false;
        })
    }

    const resetVerifiedEmail = function(){
        verifiedEmail.value = ''
    }
    
    return {
        verifiedEmail, send, resetVerifiedEmail
    }
})