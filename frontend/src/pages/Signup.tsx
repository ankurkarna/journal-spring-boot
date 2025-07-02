import React, { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import axios from "axios";

const Signup: React.FC = () => {
  const [userName, setUserName] = useState("");
  const [password, setPassword] = useState("");
  const [email, setEmail] = useState("");
  const [isLoading, setIsLoading] = useState(false);

  const navigate = useNavigate();

  const API_BASE = import.meta.env.VITE_API_URL;

  // Debug: Log the API URL to console
  console.log("API_BASE:", API_BASE);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setIsLoading(true);

    try {
      // Only send the fields that the backend expects
      const payload = {
        userName,
        password,
        email: email || null, // Make email optional
      };

      console.log("Sending signup request to:", `${API_BASE}/public/signup`);
      console.log("Payload:", payload);

      const response = await axios.post(`${API_BASE}/public/signup`, payload, {
        headers: {
          "Content-Type": "application/json",
        },
      });

      console.log("Signup response:", response.data);
      alert("Account created successfully! Please sign in.");
      navigate("/signin");
    } catch (err: any) {
      console.error("Signup error:", err);

      if (err.response) {
        console.error("Response data:", err.response.data);
        console.error("Response status:", err.response.status);
        alert(`Signup failed: ${err.response.data || "Unknown error"}`);
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
      <div className="min-h-screen bg-gradient-to-tr from-indigo-100 to-white flex items-center justify-center px-4">
        {/* Debug section - remove this after fixing */}
        {!API_BASE && (
          <div className="absolute top-20 left-1/2 transform -translate-x-1/2 bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded">
            <strong>Debug:</strong> VITE_API_URL is not set. Please check your
            environment variables.
            <br />
            Current value: {API_BASE || "undefined"}
          </div>
        )}

        <div className="bg-white shadow-lg rounded-xl p-8 w-full max-w-md space-y-6">
          <h2 className="text-2xl font-bold text-center text-gray-800">
            Create Account
          </h2>
          <form onSubmit={handleSubmit} className="space-y-4">
            <input
              type="text"
              required
              placeholder="Username"
              value={userName}
              onChange={(e) => setUserName(e.target.value)}
              className="w-full px-4 py-2 border border-gray-300 rounded-md bg-white text-gray-800 placeholder-gray-500 focus:outline-none focus:ring-2 focus:ring-indigo-400"
            />
            <input
              type="password"
              required
              placeholder="Password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              className="w-full px-4 py-2 border border-gray-300 rounded-md bg-white text-gray-800 placeholder-gray-500 focus:outline-none focus:ring-2 focus:ring-indigo-400"
            />
            <input
              type="email"
              placeholder="Email (optional)"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              className="w-full px-4 py-2 border border-gray-300 rounded-md bg-white text-gray-800 placeholder-gray-500 focus:outline-none focus:ring-2 focus:ring-indigo-400"
            />
            <button
              type="submit"
              disabled={isLoading}
              className="w-full bg-indigo-600 text-white py-2 rounded-md hover:bg-indigo-700 transition disabled:opacity-50 disabled:cursor-not-allowed"
            >
              {isLoading ? "Creating Account..." : "Sign Up"}
            </button>
          </form>
          <p className="text-sm text-center text-gray-600">
            Already have an account?{" "}
            <Link to="/signin" className="text-indigo-600 hover:underline">
              Sign In
            </Link>
          </p>
        </div>
      </div>
    </div>
  );
};

export default Signup;
