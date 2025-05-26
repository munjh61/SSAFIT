import { ref } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios'

// 서버 URL 설정
const serverUrl = import.meta.env.VITE_API_BASE_URL

export const useSearchStore = defineStore('search', () => {
  const searchResults = ref([])
  const isLoading = ref(false)
  const error = ref(null)

  const searchBoards = async ({ keyword, field }) => {
    console.log('=== 검색 시작 ===')
    console.log('검색어:', keyword)
    console.log('검색 필드:', field)
    console.log('서버 URL:', serverUrl)
    
    isLoading.value = true
    error.value = null
    
    try {
      const response = await axios.get(`${serverUrl}/api/public/board/search`, {
        params: {
          keyword,
          field
        }
      })
      
      console.log('서버 응답:', response.data)
      
      if (Array.isArray(response.data)) {
        // 응답이 배열인 경우 (기존 방식)
        searchResults.value = response.data
      } else if (response.data.boards && response.data.images) {
        // 응답이 boards와 images를 포함하는 객체인 경우
        const { boards, images } = response.data
        searchResults.value = boards.map(board => ({
          ...board,
          images: images[board.boardId] || []
        }))
      } else {
        // 예상치 못한 응답 형식
        console.error('예상치 못한 응답 형식:', response.data)
        searchResults.value = []
      }
      
      console.log('검색 결과:', searchResults.value)
    } catch (err) {
      console.error('=== 검색 중 오류 발생 ===')
      console.error('오류 객체:', err)
      console.error('오류 응답:', err.response)
      console.error('오류 메시지:', err.message)
      if (err.response) {
        console.error('서버 응답 상태:', err.response.status)
        console.error('서버 응답 데이터:', err.response.data)
      }
      
      error.value = err.response?.data?.message || '검색 중 오류가 발생했습니다.'
      searchResults.value = []
    } finally {
      isLoading.value = false
      console.log('=== 검색 종료 ===')
      console.log('최종 상태:', {
        결과수: searchResults.value.length,
        에러: error.value,
        로딩상태: isLoading.value
      })
    }
  }

  return {
    searchResults,
    isLoading,
    error,
    searchBoards
  }
}) 