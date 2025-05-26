import { ref } from "vue";
import { defineStore } from "pinia";
import axios from "axios";

const serverUrl = import.meta.env.VITE_API_BASE_URL

export const useOtherStore = defineStore('other', () => {
    const otherId = ref('')
    const otherName = ref('')
    const otherMsg1 = ref('')
    const otherMsg2 = ref('')

    const you = (you) => {
        axios({
            url:`${serverUrl}/api/public/user/${you}`,
            method:"GET"
        })
        .then((res)=>{
            otherId.value = res.data.userId
            otherName.value = res.data.userName
            otherMsg1.value = res.data.statusMsg1
            otherMsg2.value = res.data.statusMsg2
        })
        .catch(err=>err)
    }

    return{otherId, otherName, otherMsg1, otherMsg2, you}
})