let BACKEND_URL = "";

export async function initBackend() {
  BACKEND_URL = await window.backendAPI.getBackendUrl();
  console.log("Backend URL Loaded:", BACKEND_URL);
}

export function getData() {
  return fetch(`${BACKEND_URL}/api/data`)
    .then(res => res.json());
}
