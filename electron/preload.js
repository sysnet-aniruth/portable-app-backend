const { contextBridge } = require("electron");
const fs = require("fs");
const path = require("path");

contextBridge.exposeInMainWorld("backend", {
  getPort: () => {
    const filePath = path.join(__dirname, "../app-backend/backend-port.txt");
    return fs.readFileSync(filePath, "utf-8").trim();
  }
});
