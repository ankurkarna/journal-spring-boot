// src/services/api.ts

import axios from 'axios';

const API = axios.create({
  baseURL: 'http://localhost:8080', // your Spring Boot backend URL
});

// Attach token to every request if present
API.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token'); // or sessionStorage
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

export default API;
