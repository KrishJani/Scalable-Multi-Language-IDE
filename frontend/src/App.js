import React, { useState } from "react";
import "./App.css";

function App() {
  const [code, setCode] = useState("print('Hello from the IDE!')");
  const [output, setOutput] = useState("");
  const [error, setError] = useState("");

  const handleRun = async () => {
    setOutput("");
    setError("");
    try {
      const response = await fetch("http://localhost:8080/api/execute", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ code }),
      });
      const result = await response.json();
      setOutput(result.output || "");
      setError(result.error || "");
    } catch (err) {
      setError("Could not connect to backend.");
    }
  };

  return (
    <div className="app">
      <h1>Java IDE</h1>
      <textarea
        value={code}
        onChange={(e) => setCode(e.target.value)}
        rows="10"
        cols="80"
      />
      <br />
      <button onClick={handleRun}>Run Code</button>
      <div className="result">
        <h3>Output:</h3>
        <pre>{output}</pre>
        {error && (
          <>
            <h3>Error:</h3>
            <pre className="error">{error}</pre>
          </>
        )}
      </div>
    </div>
  );
}

export default App;
