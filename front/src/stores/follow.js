import { computed, ref } from "vue";
import { defineStore } from "pinia";
import axios from "axios";

const serverUrl = import.meta.env.VITE_API_BASE_URL;

export const useFollowStore = defineStore("follow", () => {
  const mutual = ref([]);   // 서로 팔로우
  const follower = ref([]); // 나를 팔로우 (나는 안함)
  const following = ref([]); // 내가 팔로우 (상대는 안함)

  // 서로 팔로우 + 나를 팔로우한 사람 수
  const followerCnt = computed(() => {
    return mutual.value.length + follower.value.length;
  });

  // 서로 팔로우 + 내가 팔로우한 사람 수
  const followingCnt = computed(() => {
    return mutual.value.length + following.value.length;
  });

  const getFollowData = async (userId) => {
    return axios({
      url: `${serverUrl}/api/public/follow/${userId}`,
    })
      .then((res) => {
        mutual.value = [...res.data.mutual];
        follower.value = [...res.data.onlyYou];
        following.value = [...res.data.onlyMe];
      })
      .catch((err) => {
        console.error("getFollowData 실패:", err.response?.data || err.message);
        mutual.value = [];
        follower.value = [];
        following.value = [];
      });
  };

  const addFollow = (userId) => {
    if (!sessionStorage.getItem("ssafit-login-token")) {
      alert("로그인 후 이용 가능합니다.");
      return Promise.resolve(false);
    }

    return axios({
      url: `${serverUrl}/api/follow/${userId}`,
      method: "POST",
      headers: {
        Authorization: `Bearer ${sessionStorage.getItem("ssafit-login-token")}`,
      },
    })
      .then(() => true)
      .catch((err) => {
        console.error("addFollow 실패:", err.response?.data || err.message);
        return false;
      });
  };

  const deleteFollow = (userId) => {
    return axios({
      url: `${serverUrl}/api/follow/${userId}`,
      method: "DELETE",
      headers: {
        Authorization: `Bearer ${sessionStorage.getItem("ssafit-login-token")}`,
      },
    })
      .then(() => true)
      .catch((err) => {
        console.error("deleteFollow 실패:", err.response?.data || err.message);
        return false;
      });
  };

  const isFollowed = (targetUserId) => {
    return axios({
      url: `${serverUrl}/api/follow/${targetUserId}`,
      method: "GET",
      headers: {
        Authorization: `Bearer ${sessionStorage.getItem("ssafit-login-token")}`,
      },
    })
      .then((res) => res.data)
      .catch((err) => {
        console.error("isFollowed 실패:", err.response?.data || err.message);
        return false;
      });
  };

  // 상태 초기화용
  const clear = () => {
    mutual.value = [];
    follower.value = [];
    following.value = [];
  };

  return {
    mutual,
    follower,
    following,
    followerCnt,
    followingCnt,
    getFollowData,
    addFollow,
    deleteFollow,
    isFollowed,
    clear,
  };
});
