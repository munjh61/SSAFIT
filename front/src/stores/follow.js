import { computed, ref } from "vue";
import { defineStore } from "pinia";
import axios from "axios";

const serverUrl = import.meta.env.VITE_API_BASE_URL;

export const useFollowStore = defineStore("follow", () => {
  const mutual = ref([]);
  const follower = ref([]);
  const followerCnt = computed(()=>{
    return follower.value
  })
  const following = ref([]);
  const followingCnt = computed(()=>{
    return following.value
  });

  const getFollowData = async (userId) => {
    return axios({
      url: `${serverUrl}/api/public/follow/${userId}`,
    }).then((res) => {
      console.log(res.data.mutual); // 서로 팔로우
      console.log(res.data.onlyYou); // 상대만 userId를 팔로우
      console.log(res.data.onlyMe); // userId만 상대를 팔로우
        follower.value = [...res.data.mutual, ...res.data.onlyYou]
        following.value = [...res.data.mutual, ...res.data.onlyMe]

    });
  };

  return { getFollowData };
});
