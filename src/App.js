import { useEffect } from "react";

function App() {
  useEffect(() => {
    const port = window.backend.getPort();
    const url = `http://127.0.0.1:${port}/api/your-endpoint`;

    fetch(url)
      .then(res => res.json())
      .then(data => {
        console.log("Data:", data);
      })
      .catch(err => console.error("Error:", err));
  }, []);

  return <div>App Loaded</div>;
}

export default App;
