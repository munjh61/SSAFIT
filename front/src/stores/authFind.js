import { ref } from "vue";
import { defineStore } from "pinia";
import axios from "axios";

const serverUrl = import.meta.env.VITE_API_BASE_URL

export const useAuthFindStore = defineStore('authFind', ()=>{
    const msg = ref('인증코드 발송은 5분마다 한번 가능합니다.')
    const inputEmail = ref('')
    
    const reset = function(){
        msg.value = '인증코드 발송은 5분마다 한번 가능합니다.'
        inputEmail.value = ''
    }

    const send = function (address) {
        return axios({
            url: `${serverUrl}/api/public/user/email/request`,
            method: "POST",
            data:{
                address,
                type : "find"
            }
        })
        .then((res)=>{
            msg.value = res.data
            inputEmail.value = address
            return true;
        })
        .catch((err)=>{
            msg.value = err.response.data
            return false;
        })
    }

    const verify = function (code){
        return axios({
            url:`${serverUrl}/api/public/user/email/verify`,
            method:"POST",
            data:{
                address : inputEmail.value,
                code,
            }
        })
        .then((res)=>{
            msg.value = res.data
            return true
        })
        .catch((err)=>{
            msg.value = err.response.data
            return false
        })
    }
    
    const findId = function () {
        return axios({
            url:`${serverUrl}/api/public/user/find/${inputEmail.value}`,
            method:'GET',
        })
        .then((res)=>{
            return res.data
        })
        .catch((err)=>{
            return err.response.data
        })
    }

    const changePw = function (userId, email, password){
        return axios({
            url:`${serverUrl}/api/public/user`,
            method:"PUT",
            data:{
                userId,
                email,
                password
            }
        })
        .then((res)=>{
            msg.value = res.data
            alert(res.data)
            reset()
            return true
        })
        .catch((err)=>{
            msg.value = err.response.data
            return false
        })
    }

    return {
        msg, inputEmail, send, reset, verify, findId, changePw
    }
})