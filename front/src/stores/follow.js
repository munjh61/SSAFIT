import { ref } from "vue";
import { defineStore } from "pinia";
import axios from "axios";

const serverUrl = import.meta.env.VITE_API_BASE_URL

export const useFollowStore = defineStore('follow', () => {
    const getFollowData = async (userId) => {
        
    }

    return {}
})