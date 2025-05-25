import { ref } from "vue";
import { defineStore } from "pinia";
import axios from "axios";

const serverUrl = import.meta.env.VITE_API_BASE_URL

export const useAuthNewStore = defineStore('authNew', ()=>{
    const msg = ref('SSAFIT에 오신것을 환영합니다.')
    const inputEmail = ref('')
    const reset = () => {
        inputEmail.value = ''
        msg.value = 'SSAFIT에 오신것을 환영합니다.'
    }
    
    const checkUserId =  async (userId) =>{
        return axios({
            url:`${serverUrl}/api/public/user/id/${userId}`,
            method:"GET"
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

    const isValidPassword = (password) => {
        const regex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{8,20}$/;
        if(regex.test(password)){
            msg.value = ""
            return true;
        }
        return false;
    };

    const checkEmail = async (email) => {
        return axios({
            url:`${serverUrl}/api/public/email/${email}`,
            method:'GET'
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

    const send = function (address) {
        return axios({
            url: `${serverUrl}/api/public/user/email/request`,
            method: "POST",
            data:{
                address,
                type:"new"
            }
        })
        .then((res)=>{
            msg.value = res.data
            inputEmail.value = address
            return true;
        })
        .catch((err)=>{
            console.log(err)
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

    const regist = function(userId, password, email, userName){
        return axios({
            url:`${serverUrl}/api/public/user`,
            method:"POST",
            data:{
                userId,
                password,
                email,
                userName
            }
        })
        .then((res)=>{
            msg.value = res.data
            return true
        })
        .catch((err)=>{
            msg.value = err.response.data
        })
    }

    return {
        msg, inputEmail, reset, checkUserId, checkEmail, isValidPassword, regist, send, verify
    }
})