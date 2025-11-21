const { app, BrowserWindow } = require("electron");
const fs = require("fs");
const path = require("path");

let backendPort = null;

function readBackendPort() {
  try {
    const portFile = path.join(__dirname, "../app-backend/backend-port.txt");
    backendPort = fs.readFileSync(portFile, "utf-8").trim();
    console.log("Backend running on port:", backendPort);
  } catch (err) {
    console.error("Failed to read backend-port.txt", err);
  }
}

function createWindow() {
  readBackendPort();

  const win = new BrowserWindow({
    width: 1200,
    height: 800,
    webPreferences: {


      preload: path.join(__dirname, "preload.js")
    }
  });

  win.loadURL("http://localhost:3000"); // your React dev server
}

app.on("ready", createWindow);
