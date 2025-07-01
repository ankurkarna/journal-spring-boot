import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

const Signin: React.FC = () => {
  const navigate = useNavigate();
  const [credentials, setCredentials] = useState({
    userName: "",
    password: "",
  });

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
    try {
      const res = await fetch("http://localhost:8080/public/signin", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(credentials),
      });

      const data = await res.text();

      if (res.ok) {
        const token = data.replace("JWT Token: ", "").trim();
        localStorage.setItem("token", token);
        //         alert("Signin successful");
        navigate("/dashboard");
      } else {
        alert("Invalid credentials");
      }
    } catch (err) {
      console.error(err);
      alert("Error during signin");
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
            className="bg-green-600 text-white py-2 px-4 rounded w-full hover:bg-green-700"
          >
            Sign In
          </button>
        </form>
      </div>
    </div>
  );
};

export default Signin;
