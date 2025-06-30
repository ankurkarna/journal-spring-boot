///** @type {import('tailwindcss').Config} */
//module.exports = {
//darkMode: false,
//  content: [
//    "./index.html",                  // ✅ Do not use /frontend/index.html
//    "./src/**/*.{js,ts,jsx,tsx}"     // ✅ Tailwind scans components here
//  ],
//  theme: {
//    extend: {},
//  },
//  plugins: [],
//};

// tailwind.config.js
export default {
  darkMode: 'class', // ← this is key!
  content: [
    './index.html',
    './src/**/*.{js,ts,jsx,tsx}',
  ],
  theme: {
    extend: {},
  },
  plugins: [],
}
