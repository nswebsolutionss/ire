import { defineConfig } from 'vite'
import reactRefresh from '@vitejs/plugin-react-refresh'
import svgrPlugin from 'vite-plugin-svgr'
import path from 'path';


// see all documentation here https://vitejs.dev/config/
export default defineConfig({
  // This changes the out put dir from dist to build change as your need
  // comment this out if that isn't relevant for your project
  build: {
    outDir: 'build',
  },
  resolve: {
    alias: {
      src: "/src",
      // components: "/src/components",
      '@dwellio/components': path.resolve(__dirname, 'src/components'),
      '@dwellio/pages': path.resolve(__dirname, 'src/pages'),
      assets: "/src/assets"

    },
  },
  plugins: [
    reactRefresh(),
    svgrPlugin({
      svgrOptions: {
        icon: true,
        // ...svgr options (https://react-svgr.com/docs/options/)
      },
    }),
  ],
})