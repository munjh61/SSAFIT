import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'
import './assets/css/global.css'  // 글로벌 CSS import

const app = createApp(App)

app.use(createPinia())
app.use(router)

app.mount('#app')
