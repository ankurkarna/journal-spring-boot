import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

const Signin: React.FC = () => {
  const navigate = useNavigate();
  const [credentials, setCredentials] = useState({
    userName: "",
    password: "",
  });
  const [isLoading, setIsLoading] = useState(false);

  const API_BASE = import.meta.env.VITE_API_URL;

  // Debug: Log the API URL to console
  console.log("Signin API_BASE:", API_BASE);

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (token) {
      navigate("/dashboard", { replace: true });
    }
  }, []);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setCredentials((prev) => ({
      ...prev,
      [e.target.name]: e.target.value,
    }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setIsLoading(true);

    try {
      console.log("Sending signin request to:", `${API_BASE}/public/signin`);
      console.log("Credentials:", {
        userName: credentials.userName,
        password: "***",
      });

      const res = await axios.post(`${API_BASE}/public/signin`, credentials);

      console.log("Signin response:", res.data);

      if (res.status === 200) {
        const data = res.data;
        const token = data.replace("JWT Token: ", "").trim();
        localStorage.setItem("token", token);
        console.log("Token stored successfully");
        navigate("/dashboard");
      } else {
        alert("Invalid credentials");
      }
    } catch (err: any) {
      console.error("Signin error:", err);

      if (err.response) {
        console.error("Response data:", err.response.data);
        console.error("Response status:", err.response.status);
        alert(`Signin failed: ${err.response.data || "Invalid credentials"}`);
      } else if (err.request) {
        console.error("No response received:", err.request);
        alert(
          "No response from server. Please check your internet connection and try again."
        );
      } else {
        console.error("Error setting up request:", err.message);
        alert("Error: " + err.message);
      }
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div>
      {/* Header */}
      <header className="bg-white shadow-sm p-4">
        <div className="max-w-4xl mx-auto flex items-center space-x-2">
          <span className="w-8 h-8 flex items-center justify-center bg-white rounded-full">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 48 48"
              className="w-8 h-8"
            >
              <rect x="8" y="8" width="32" height="32" rx="6" fill="#fbbf24" />
              <rect x="14" y="14" width="20" height="20" rx="2" fill="#fff" />
              <path
                d="M18 18h12M18 22h12M18 26h8"
                stroke="#6366f1"
                strokeWidth="2"
                strokeLinecap="round"
              />
              <rect
                x="28"
                y="28"
                width="6"
                height="6"
                rx="1"
                fill="#fbbf24"
                stroke="#6366f1"
                strokeWidth="1.5"
              />
            </svg>
          </span>
          <div>
            <a
              href="/"
              className="text-4xl font-bold text-gray-700 hover:text-indigo-600 transition-colors"
            >
              Crynza
            </a>
            <div className="text-xs text-gray-500 font-medium mt-1">
              Your mind. Your words. Your space.
            </div>
          </div>
        </div>
      </header>
      <div className="flex items-center justify-center min-h-screen bg-gray-100">
        {/* Debug section - remove this after fixing */}
        {!API_BASE && (
          <div className="absolute top-20 left-1/2 transform -translate-x-1/2 bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded">
            <strong>Debug:</strong> VITE_API_URL is not set. Please check your
            environment variables.
            <br />
            Current value: {API_BASE || "undefined"}
          </div>
        )}

        <form
          onSubmit={handleSubmit}
          className="bg-white p-6 rounded shadow-md w-96"
        >
          <h2 className="text-xl font-semibold mb-4">Sign In</h2>

          <input
            type="text"
            name="userName"
            placeholder="Username"
            value={credentials.userName}
            onChange={handleChange}
            className="w-full mb-4 p-2 border rounded"
            required
          />
          <input
            type="password"
            name="password"
            placeholder="Password"
            value={credentials.password}
            onChange={handleChange}
            className="w-full mb-4 p-2 border rounded"
            required
          />
          <button
            type="submit"
            disabled={isLoading}
            className="bg-green-600 text-white py-2 px-4 rounded w-full hover:bg-green-700 disabled:opacity-50 disabled:cursor-not-allowed"
          >
            {isLoading ? "Signing In..." : "Sign In"}
          </button>
        </form>
      </div>
    </div>
  );
};

export default Signin;
