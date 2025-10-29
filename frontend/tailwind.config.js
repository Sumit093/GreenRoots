/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{js,jsx,ts,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        primary: '#4CAF50',
        secondary: '#8BC34A',
        accent: '#CDDC39',
        dark: '#212121',
        light: '#F5F5F5',
      },
    },
  },
  plugins: [],
};
