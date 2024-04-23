import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vitejs.dev/config/
export default defineConfig({
  server:{
    // https: {
    //   key:'./influgenerator-privateKey.key',
    //   cert:'./influgenerator.crt',
    // }

    http: true,
  },
  plugins: [react()],
})
