import { ref } from "vue";
import { defineStore } from "pinia";
import axios from "axios";

const serverUrl = import.meta.env.VITE_API_BASE_URL

export const useAuthNewStore = defineStore('authNew', ()=>{
    const msg = ref('SSAFIT에 오신것을 환영합니다.')
    const reset = () => {
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
        msg, reset, checkUserId, checkEmail, isValidPassword, regist
    }
})