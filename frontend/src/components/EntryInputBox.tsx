import React, { useState } from 'react';

interface Props {
  onAdd: (title: string, content: string, sentiment: string) => void;
}

const EntryInputBox: React.FC<Props> = ({ onAdd }) => {
  const [titleVisible, setTitleVisible] = useState(false);
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [sentiment, setSentiment] = useState('NEUTRAL');

  const handleSubmit = () => {
    if (!content.trim()) return;
    onAdd(title || 'Untitled Entry', content, sentiment);
    setTitle('');
    setContent('');
    setSentiment('NEUTRAL');
    setTitleVisible(false);
  };

  return (
    <div className="bg-white p-4 rounded-lg shadow-sm space-y-3">
      {titleVisible && (
        <input
          type="text"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          placeholder="Title"
          className="w-full p-2 border border-gray-300 rounded"
        />
      )}

      <textarea
        value={content}
        onChange={(e) => {
          setContent(e.target.value);
          if (!titleVisible) setTitleVisible(true);
        }}
        placeholder="Write something..."
        rows={4}
        className="w-full p-2 border border-gray-300 rounded"
      />

      <div className="flex justify-between items-center">
        <select
          value={sentiment}
          onChange={(e) => setSentiment(e.target.value)}
          className="p-2 border border-gray-300 rounded text-sm"
        >
          <option value="HAPPY">😊 Happy</option>
          <option value="SAD">😢 Sad</option>
          <option value="ANXIOUS">😰 Anxious</option>
          <option value="CONFUSED">😕 Confused</option>
          <option value="NEUTRAL">😐 Neutral</option>
        </select>

        <button
          onClick={handleSubmit}
          className="text-sm bg-green-500 hover:bg-gray-200 px-3 py-1 rounded"
        >
          Add Entry
        </button>
      </div>
    </div>
  );
};

export default EntryInputBox;
