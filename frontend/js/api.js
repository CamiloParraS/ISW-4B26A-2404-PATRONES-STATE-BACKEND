// api.js - Todas las llamadas HTTP al backend Spring Boot

const BASE = "http://localhost:8080/api/rides";

async function handleResponse(res) {
  const data = await res.json();
  if (!res.ok) throw new Error(data.error || `HTTP ${res.status}`);
  return data;
}

export const rideApi = {
  async createRide() {
    const res = await fetch(BASE, { method: "POST" });
    return handleResponse(res);
  },

  async getRide(id) {
    const res = await fetch(`${BASE}/${id}`);
    return handleResponse(res);
  },

  async performAction(id, action) {
    const res = await fetch(`${BASE}/${id}/actions`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ action }),
    });
    return handleResponse(res);
  },
};
