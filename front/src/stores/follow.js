import { computed, ref } from "vue";
import { defineStore } from "pinia";
import axios from "axios";
const serverUrl = import.meta.env.VITE_API_BASE_URL;

export const useFollowStore = defineStore("follow", () => {
  const mutual = ref([]);
  const follower = ref([]);
  const followerCnt = computed(() => {
    return mutual.value.length + follower.value.length;
  });
  const following = ref([]);
  const followingCnt = computed(() => {
    return mutual.value.length + following.value.length;
  });

  const getFollowData = async (userId) => {
    return axios({
      url: `${serverUrl}/api/public/follow/${userId}`,
    }).then((res) => {
      mutual.value = [...res.data.mutual]; // 서로 팔로우
      follower.value = [...res.data.onlyYou]; // 상대만 userId를 팔로우
      following.value = [...res.data.onlyMe]; // userId만 상대를 팔로우
    });
  };

  const addFollow = (userId) =>{
    if(sessionStorage.getItem('ssafit-login-token')==null){
      alert("로그인 후 이용 가능합니다.")
    }
    return axios({
      url:`${serverUrl}/api/follow/${userId}`,
      method:"POST",
      headers:{
        Authorization: `Bearer ${sessionStorage.getItem('ssafit-login-token')}`
      }
    })
    .then((res)=>{
      //console.log(res.data)
      return true
    })
    .catch((err)=>{
      //console.log(err.response.data)
      return false
    })
  }

  const deleteFollow = (userId) =>{
    return axios({
      url:`${serverUrl}/api/follow/${userId}`,
      method:"DELETE",
      headers:{
        Authorization: `Bearer ${sessionStorage.getItem('ssafit-login-token')}`
      }
    })
    .then((res)=>{
      //console.log(res.data)
      return true
    })
    .catch((err)=>{
      //console.log(err.response.data)
      return false
    })
  }


  return { mutual, follower, followerCnt, following, followingCnt, getFollowData, addFollow, deleteFollow };
});
