import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import EntryInputBox from "../components/EntryInputBox";

interface Entry {
  id: string;
  title: string;
  content: string;
  date: string;
  sentiment: string;
}

// Helper to get a unique string id for React keys and API URLs
// Now uses entry.stringId provided by the backend
const getEntryId = (entry: any) => entry.stringId;

function decodeJWT(token: string | null): any {
  if (!token) return null;
  try {
    const payload = token.split(".")[1];
    return JSON.parse(atob(payload));
  } catch {
    return null;
  }
}

// Daily quotes/affirmations
const QUOTES = [
  "You are enough. You have enough. You do enough.",
  "Every day is a fresh start.",
  "Small steps every day lead to big changes.",
  "You are stronger than you think.",
  "Gratitude turns what we have into enough.",
  "Your story matters.",
  "Be gentle with yourself.",
];

// Mood options
const MOODS = [
  { emoji: "😃", label: "Happy" },
  { emoji: "😐", label: "Neutral" },
  { emoji: "😔", label: "Sad" },
  { emoji: "😰", label: "Anxious" },
  { emoji: "🤔", label: "Thoughtful" },
  { emoji: "😴", label: "Tired" },
];

function getTodayKey(key: string) {
  const today = new Date().toISOString().slice(0, 10);
  return `${key}_${today}`;
}

const API_BASE = import.meta.env.VITE_API_URL;

// Debug: Log the API URL to console
console.log("Dashboard API_BASE:", API_BASE);

const Dashboard: React.FC = () => {
  const [entries, setEntries] = useState<Entry[]>([]);
  const [activeTab, setActiveTab] = useState("entries");
  const [isLoading, setIsLoading] = useState(true);
  const [editingEntry, setEditingEntry] = useState<Entry | null>(null);
  const [editTitle, setEditTitle] = useState("");
  const [editContent, setEditContent] = useState("");
  const [editSentiment, setEditSentiment] = useState("NEUTRAL");
  const [isSaving, setIsSaving] = useState(false);
  const [greeting, setGreeting] = useState("");
  const [todayMood, setTodayMood] = useState<string | null>(null);
  const [streak, setStreak] = useState<number>(0);
  const [showConfetti, setShowConfetti] = useState(false);
  const navigate = useNavigate();

  const fetchEntries = async (token: string) => {
    try {
      const res = await axios.get(`${API_BASE}/journal`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setEntries(res.data || []);
      return res.data || [];
    } catch (err: any) {
      console.error(err);
      if (err.response?.status === 401) {
        localStorage.removeItem("token");
        navigate("/signin");
      }
      setIsLoading(false);
      return [];
    } finally {
      setIsLoading(false);
    }
  };

  // Get username from token
  const token = localStorage.getItem("token");
  const decoded = decodeJWT(token);
  const username = decoded?.sub || "User";

  // Daily quote logic
  const quoteOfTheDay = QUOTES[new Date().getDate() % QUOTES.length];

  // Mood tracker logic
  useEffect(() => {
    const mood = localStorage.getItem(getTodayKey("mood"));
    if (mood) setTodayMood(mood);
  }, []);

  const handleMoodSelect = (mood: string) => {
    setTodayMood(mood);
    localStorage.setItem(getTodayKey("mood"), mood);
  };

  // Streak logic
  useEffect(() => {
    const lastEntryDate = localStorage.getItem("lastEntryDate");
    const streakCount = parseInt(localStorage.getItem("streak") || "0", 10);
    const today = new Date().toISOString().slice(0, 10);
    if (entries.length > 0) {
      // Find the latest entry date
      const latest = entries
        .map((e) => new Date(e.date).toISOString().slice(0, 10))
        .sort()
        .pop();
      if (latest) {
        if (lastEntryDate === today) {
          setStreak(streakCount);
        } else if (
          lastEntryDate &&
          latest === today &&
          new Date(today).getTime() - new Date(lastEntryDate).getTime() ===
            86400000
        ) {
          // Consecutive day
          setStreak(streakCount + 1);
          localStorage.setItem("streak", String(streakCount + 1));
          setShowConfetti([3, 7, 30].includes(streakCount + 1));
        } else if (latest === today) {
          // New streak
          setStreak(1);
          localStorage.setItem("streak", "1");
          setShowConfetti(true);
        }
        localStorage.setItem("lastEntryDate", latest);
      }
    }
  }, [entries]);

  useEffect(() => {
    if (!token) {
      navigate("/signin");
      return;
    }
    fetchEntries(token);
    // Fetch greeting (with weather)
    axios
      .get(`${API_BASE}/user`, {
        headers: { Authorization: `Bearer ${token}` },
      })
      .then((res) => setGreeting(res.data))
      .catch(() => setGreeting(""));
  }, []);

  const handleAddEntryByType = async (
    type: "blank" | "daily" | "gratitude" | "note"
  ) => {
    const token = localStorage.getItem("token");
    if (!token) return;

    let title = "",
      content = "",
      sentiment = "NEUTRAL";
    switch (type) {
      case "daily":
        title = "Daily Log - " + new Date().toLocaleDateString();
        content = "Today I...\n\nI felt...\n\nTomorrow I want to...";
        break;
      case "gratitude":
        title = "Gratitude Entry";
        content = "I am grateful for...\n\nBecause...";
        sentiment = "HAPPY";
        break;
      case "note":
        title = "Quick Note";
        content = "Note: ";
        break;
      default:
        title = "Journal Entry";
        content = "";
    }

    try {
      setIsLoading(true);
      await axios.post(
        `${API_BASE}/journal`,
        { title, content, sentiment },
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );
      const newEntries = await fetchEntries(token);
      if (newEntries.length > 0) {
        const sorted = [...newEntries].sort(
          (a, b) => new Date(b.date).getTime() - new Date(a.date).getTime()
        );
        const newEntry = sorted[0];
        setEditingEntry(newEntry);
        setEditTitle(newEntry.title);
        setEditContent(newEntry.content);
        setEditSentiment(newEntry.sentiment);
      }
    } catch (err) {
      console.error("Quick Add Error:", err);
    } finally {
      setIsLoading(false);
    }
  };

  const handleAddEntryManual = async (
    title: string,
    content: string,
    sentiment: string
  ) => {
    const token = localStorage.getItem("token");
    if (!token) return;

    try {
      setIsLoading(true);
      await axios.post(
        `${API_BASE}/journal`,
        { title, content, sentiment },
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );
      await fetchEntries(token);
    } catch (err) {
      console.error("Manual Add Error:", err);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-gray-50 text-gray-800">
      {/* Confetti celebration for streaks */}
      {showConfetti && (
        <div className="fixed inset-0 z-50 pointer-events-none flex items-start justify-center mt-20 animate-fadeIn">
          <span className="text-5xl">🎉</span>
        </div>
      )}

      {/* Header */}
      <header className="bg-white shadow-sm p-4">
        <div className="max-w-6xl mx-auto flex justify-between items-center">
          <div className="flex items-center space-x-2">
            <span
              className="flex items-center justify-center mr-3"
              style={{ width: 32, height: 32 }}
            >
              <svg
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 32 32"
                width={32}
                height={32}
                className="block"
              >
                <rect
                  x="4"
                  y="4"
                  width="24"
                  height="24"
                  rx="5"
                  fill="#fbbf24"
                />
                <rect x="9" y="9" width="14" height="14" rx="2" fill="#fff" />
                <path
                  d="M12 13h8M12 16h8M12 19h5"
                  stroke="#6366f1"
                  strokeWidth="1.5"
                  strokeLinecap="round"
                />
                <rect
                  x="19"
                  y="19"
                  width="4"
                  height="4"
                  rx="1"
                  fill="#fbbf24"
                  stroke="#6366f1"
                  strokeWidth="1"
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
              {/* Weather and quote combined, minimal */}
              {(greeting || quoteOfTheDay) && (
                <div className="text-xs text-gray-400 mt-2">
                  {greeting}
                  {greeting && quoteOfTheDay && <span className="mx-2">·</span>}
                  {quoteOfTheDay}
                </div>
              )}
            </div>
          </div>
          <div className="flex items-center space-x-3">
            {/* Username display */}
            <span className="text-gray-600 text-sm pr-3 border-r border-gray-300">
              {username}
            </span>
            <button
              onClick={() => {
                localStorage.removeItem("token");
                navigate("/signin");
              }}
              className="text-sm bg-gray-100 hover:bg-gray-200 px-3 py-1 rounded"
            >
              Logout
            </button>
          </div>
        </div>
      </header>

      <div className="max-w-6xl mx-auto px-4 mt-4">
        <div className="w-full h-48 overflow-hidden rounded shadow-lg group">
          <img
            src="/src/assets/mountain_dash1.jpg"
            alt="Mountain Banner"
            className="w-full h-full object-cover transform transition-transform duration-500 ease-in-out group-hover:scale-105"
          />
        </div>
      </div>

      {/* Mood Tracker */}
      <div className="max-w-6xl mx-auto px-4 mt-4 flex justify-end">
        <div className="bg-white rounded-lg shadow p-3 flex items-center space-x-2">
          <span className="text-sm text-gray-500">
            How are you feeling today?
          </span>
          {MOODS.map((m) => (
            <button
              key={m.label}
              className={`text-2xl px-1 focus:outline-none ${
                todayMood === m.emoji ? "ring-2 ring-indigo-400 rounded" : ""
              }`}
              onClick={() => handleMoodSelect(m.emoji)}
              aria-label={m.label}
            >
              {m.emoji}
            </button>
          ))}
          {todayMood && <span className="ml-2 text-lg">{todayMood}</span>}
        </div>
      </div>

      {/* Streak badge */}
      {streak > 1 && (
        <div className="max-w-6xl mx-auto px-4 mt-2 flex justify-end">
          <div className="bg-green-100 text-green-700 rounded-full px-4 py-1 text-sm font-semibold shadow inline-flex items-center">
            🔥 {streak} day streak!
          </div>
        </div>
      )}

      {/* Layout */}
      <div className="max-w-6xl mx-auto grid grid-cols-1 md:grid-cols-4 gap-6 p-4">
        {/* Sidebar */}
        <div className="md:col-span-1 space-y-6">
          <div className="bg-white p-4 rounded-lg shadow-sm">
            <h2 className="text-lg font-semibold mb-2">Navigation</h2>
            {["journals", "entries", "prompts"].map((tab) => (
              <button
                key={tab}
                onClick={() => setActiveTab(tab)}
                className={`block w-full px-3 py-2 text-left rounded ${
                  activeTab === tab
                    ? "bg-indigo-50 text-indigo-700"
                    : "hover:bg-gray-50"
                }`}
              >
                {tab.charAt(0).toUpperCase() + tab.slice(1)}
              </button>
            ))}
          </div>

          <div className="bg-white p-4 rounded-lg shadow-sm">
            <h2 className="text-lg font-semibold mb-2">Quick Add</h2>
            <div className="grid grid-cols-2 gap-2">
              <button
                onClick={() => handleAddEntryByType("blank")}
                className="bg-gray-100 hover:bg-gray-200 p-2 rounded text-sm"
              >
                Blank
              </button>
              <button
                onClick={() => handleAddEntryByType("daily")}
                className="bg-blue-50 hover:bg-blue-100 p-2 rounded text-sm"
              >
                Daily
              </button>
              <button
                onClick={() => handleAddEntryByType("gratitude")}
                className="bg-green-50 hover:bg-green-100 p-2 rounded text-sm"
              >
                Gratitude
              </button>
              <button
                onClick={() => handleAddEntryByType("note")}
                className="bg-yellow-50 hover:bg-yellow-100 p-2 rounded text-sm"
              >
                Note
              </button>
            </div>
          </div>
        </div>

        {/* Main Panel */}
        <div className="md:col-span-3 space-y-6">
          <EntryInputBox onAdd={handleAddEntryManual} />

          <div className="bg-white p-4 rounded-lg shadow-sm">
            <h2 className="text-lg font-semibold mb-4">Journal Entries</h2>
            {isLoading ? (
              <div className="text-center">Loading...</div>
            ) : entries.length === 0 ? (
              <div className="text-center text-gray-500">No entries yet.</div>
            ) : (
              <div className="space-y-4">
                {entries.map((entry) => (
                  <div
                    key={getEntryId(entry)}
                    className="border border-gray-300 rounded-md p-4 bg-white shadow-sm mb-4 cursor-pointer hover:shadow-md transition relative"
                    onClick={() => {
                      setEditingEntry(entry);
                      setEditTitle(entry.title);
                      setEditContent(entry.content);
                      setEditSentiment(entry.sentiment);
                    }}
                  >
                    {/* Delete Button */}
                    <button
                      className="absolute top-2 right-2 text-red-500 hover:text-red-700 bg-white rounded-full p-1 shadow-sm border border-red-100"
                      title="Delete Entry"
                      onClick={async (e) => {
                        e.stopPropagation();
                        if (
                          !window.confirm(
                            "Are you sure you want to delete this entry?"
                          )
                        )
                          return;
                        const token = localStorage.getItem("token");
                        if (!token) return;
                        setIsLoading(true);
                        try {
                          await axios.delete(
                            `${API_BASE}/journal/id/${getEntryId(entry)}`,
                            { headers: { Authorization: `Bearer ${token}` } }
                          );
                          await fetchEntries(token);
                        } catch (err) {
                          console.error("Error deleting entry:", err);
                        } finally {
                          setIsLoading(false);
                        }
                      }}
                    >
                      Delete
                    </button>
                    <h4 className="font-semibold">{entry.title}</h4>
                    <p className="text-sm text-gray-600">{entry.content}</p>
                    <div className="flex justify-between items-center text-xs text-gray-400 mt-2">
                      <span>{new Date(entry.date).toLocaleString()}</span>
                      <span
                        className={`px-2 py-1 rounded text-xs font-medium ${
                          entry.sentiment === "HAPPY"
                            ? "bg-green-100 text-green-800"
                            : entry.sentiment === "SAD"
                            ? "bg-blue-100 text-blue-800"
                            : entry.sentiment === "ANXIOUS"
                            ? "bg-yellow-100 text-yellow-800"
                            : entry.sentiment === "CONFUSED"
                            ? "bg-orange-100 text-orange-800"
                            : "bg-gray-100 text-gray-800"
                        }`}
                      >
                        {entry.sentiment}
                      </span>
                    </div>
                  </div>
                ))}
              </div>
            )}
          </div>
        </div>
      </div>

      {/* Edit Modal */}
      {editingEntry && (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-60">
          <div className="relative bg-white rounded-lg shadow-xl w-full max-w-xl mx-4 p-8 animate-fadeIn">
            {/* Close Button */}
            <button
              className="absolute top-4 right-4 text-gray-400 hover:text-gray-700 text-2xl font-bold"
              onClick={() => setEditingEntry(null)}
              aria-label="Close"
            >
              &times;
            </button>
            <h2 className="text-2xl font-semibold mb-6 text-center">
              Edit Journal Entry
            </h2>
            <form
              onSubmit={async (e) => {
                e.preventDefault();
                if (!editingEntry) return;
                const token = localStorage.getItem("token");
                if (!token) return;
                setIsSaving(true);
                try {
                  const entryId = getEntryId(editingEntry);
                  await axios.put(
                    `${API_BASE}/journal/id/${entryId}`,
                    {
                      title: editTitle,
                      content: editContent,
                      sentiment: editSentiment,
                    },
                    {
                      headers: { Authorization: `Bearer ${token}` },
                    }
                  );
                  setEditingEntry(null);
                  await fetchEntries(token);
                } catch (err) {
                  console.error("Edit Save Error:", err);
                } finally {
                  setIsSaving(false);
                }
              }}
            >
              <div className="mb-4">
                <label className="block text-sm font-medium mb-1">Title</label>
                <input
                  className="w-full border border-gray-300 rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-indigo-200"
                  value={editTitle}
                  onChange={(e) => setEditTitle(e.target.value)}
                  required
                />
              </div>
              <div className="mb-4">
                <label className="block text-sm font-medium mb-1">
                  Content
                </label>
                <textarea
                  className="w-full border border-gray-300 rounded px-3 py-2 h-32 resize-vertical focus:outline-none focus:ring-2 focus:ring-indigo-200"
                  value={editContent}
                  onChange={(e) => setEditContent(e.target.value)}
                  required
                />
              </div>
              <div className="mb-6">
                <label className="block text-sm font-medium mb-1">
                  Sentiment
                </label>
                <select
                  className="w-full border border-gray-300 rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-indigo-200"
                  value={editSentiment}
                  onChange={(e) => setEditSentiment(e.target.value)}
                >
                  <option value="HAPPY">Happy</option>
                  <option value="SAD">Sad</option>
                  <option value="ANXIOUS">Anxious</option>
                  <option value="CONFUSED">Confused</option>
                  <option value="NEUTRAL">Neutral</option>
                </select>
              </div>
              <div className="flex justify-end space-x-3">
                <button
                  type="button"
                  className="px-4 py-2 rounded bg-gray-100 hover:bg-gray-200 text-gray-700"
                  onClick={() => setEditingEntry(null)}
                  disabled={isSaving}
                >
                  Cancel
                </button>
                <button
                  type="submit"
                  className="px-4 py-2 rounded bg-indigo-600 hover:bg-indigo-700 text-white font-semibold shadow"
                  disabled={isSaving}
                >
                  {isSaving ? "Saving..." : "Save"}
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
};

export default Dashboard;
