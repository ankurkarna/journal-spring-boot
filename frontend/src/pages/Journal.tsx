// Example: Fetch user journals
import React, { useEffect, useState } from "react";
import API from "../services/api";
import { Link } from "react-router-dom";

interface Entry {
  id: string;
  title: string;
  content: string;
  date: string;
  sentiment: string;
}

const Journal: React.FC = () => {
  const [entries, setEntries] = useState<Entry[]>([]);

  useEffect(() => {
    API.get("/journal")
      .then((res) => setEntries(res.data))
      .catch((err) => console.error("Error fetching journals:", err));
  }, []);

  return (
    <div>
      {/* Header */}
      <header className="bg-white shadow-sm p-4 mb-4">
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
            <Link
              to="/"
              className="text-4xl font-bold text-gray-700 hover:text-indigo-600 transition-colors"
            >
              Crynza
            </Link>
            <div className="text-xs text-gray-500 font-medium mt-1">
              Your mind. Your words. Your space.
            </div>
          </div>
        </div>
      </header>
      <h2>My Journal Entries</h2>
      <ul>
        {entries.map((entry) => (
          <li key={entry.id}>
            <strong>{entry.title}</strong>
            <p>{entry.content}</p>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default Journal;
