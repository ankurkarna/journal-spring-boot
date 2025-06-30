// src/pages/Home.tsx

import React from "react";
import { Link } from "react-router-dom";
import "./Home.css";

const Home = () => {
  return (
    <div className="min-h-screen bg-gradient-to-tr from-indigo-100 to-white flex flex-col">
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
      {/* Hero Section */}
      <section className="flex flex-col items-center justify-center flex-1 py-12 px-4">
        <div className="bg-gradient-to-tr from-yellow-200 to-yellow-100 rounded-2xl shadow-lg p-8 flex flex-col items-center w-full max-w-xl mb-8">
          <div className="bg-white rounded-full p-4 shadow mb-4">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 48 48"
              className="w-16 h-16 text-indigo-500"
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
          </div>
          <h1 className="text-4xl font-extrabold text-gray-800 mb-2 text-center">
            Welcome to Crynza
          </h1>
          <div className="text-lg text-gray-500 font-medium mb-4 text-center">
            Your mind. Your words. Your space.
          </div>
          <p className="text-gray-600 text-center max-w-lg mb-2">
            Crynza is your private, modern journaling companion. Reflect,
            record, and revisit your thoughts. Track your mood, analyze your
            mental health patterns, and stay mindful—all in one beautiful space.
          </p>
        </div>
        {/* Features Grid */}
        <div className="grid grid-cols-1 md:grid-cols-2 gap-8 max-w-3xl w-full mb-10">
          <div className="flex items-start space-x-4">
            <span className="bg-indigo-100 text-indigo-600 rounded-full p-3">
              <svg
                className="w-6 h-6"
                fill="none"
                stroke="currentColor"
                strokeWidth="2"
                viewBox="0 0 24 24"
              >
                <path
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  d="M8 6h13M8 12h13M8 18h13M3 6h.01M3 12h.01M3 18h.01"
                />
              </svg>
            </span>
            <div>
              <div className="font-semibold text-gray-800">
                Create daily journal entries
              </div>
              <div className="text-gray-500 text-sm">
                Capture your thoughts, feelings, and moments with ease.
              </div>
            </div>
          </div>
          <div className="flex items-start space-x-4">
            <span className="bg-green-100 text-green-600 rounded-full p-3">
              <svg
                className="w-6 h-6"
                fill="none"
                stroke="currentColor"
                strokeWidth="2"
                viewBox="0 0 24 24"
              >
                <path
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  d="M12 8c-1.657 0-3 1.343-3 3s1.343 3 3 3 3-1.343 3-3-1.343-3-3-3zm0 0V4m0 8v8m8-8h-8"
                />
              </svg>
            </span>
            <div>
              <div className="font-semibold text-gray-800">
                Track weekly sentiments with AI
              </div>
              <div className="text-gray-500 text-sm">
                Get insights into your mood and mental health trends.
              </div>
            </div>
          </div>
          <div className="flex items-start space-x-4">
            <span className="bg-yellow-100 text-yellow-600 rounded-full p-3">
              <svg
                className="w-6 h-6"
                fill="none"
                stroke="currentColor"
                strokeWidth="2"
                viewBox="0 0 24 24"
              >
                <path
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  d="M12 11c0-1.104.896-2 2-2s2 .896 2 2-.896 2-2 2-2-.896-2-2-2zm0 0V7m0 8v4m8-8h-8"
                />
              </svg>
            </span>
            <div>
              <div className="font-semibold text-gray-800">
                100% private and secure with JWT Auth
              </div>
              <div className="text-gray-500 text-sm">
                Your data is encrypted and only accessible by you.
              </div>
            </div>
          </div>
          <div className="flex items-start space-x-4">
            <span className="bg-purple-100 text-purple-600 rounded-full p-3">
              <svg
                className="w-6 h-6"
                fill="none"
                stroke="currentColor"
                strokeWidth="2"
                viewBox="0 0 24 24"
              >
                <path
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  d="M8 7V3m8 4V3m-9 8h10m-7 4h4"
                />
              </svg>
            </span>
            <div>
              <div className="font-semibold text-gray-800">
                Search and view past reflections
              </div>
              <div className="text-gray-500 text-sm">
                Easily find and revisit your previous entries.
              </div>
            </div>
          </div>
        </div>
        {/* Call to Action Buttons */}
        <div className="flex space-x-4 justify-center">
          <a
            href="/signup"
            className="px-6 py-3 rounded-lg bg-indigo-600 text-white font-semibold shadow hover:bg-indigo-700 transition"
          >
            Get Started
          </a>
          <a
            href="/signin"
            className="px-6 py-3 rounded-lg bg-gray-200 text-gray-800 font-semibold shadow hover:bg-gray-300 transition"
          >
            Sign In
          </a>
        </div>
      </section>
    </div>
  );
};

export default Home;
