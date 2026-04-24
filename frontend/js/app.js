// app.js - Event wiring and application state

import { rideApi } from "./api.js";
import { ui } from "./ui.js";

let currentRideId = null;
let currentState = null;
let loading = false;

const TERMINAL_STATES = ["COMPLETED", "CANCELLED"];

ui.showEmptyState();

function setLoading(val) {
  loading = val;
  ui.setButtonsLoading(val);
}

function applyState(stateName) {
  currentState = stateName;
  ui.updateStateBadge(stateName);
  ui.renderTimeline(stateName);
  ui.renderActionButtons(stateName, handleAction);
  ui.showNewRideButton(TERMINAL_STATES.includes(stateName));
}

async function handleNewRide() {
  setLoading(true);
  try {
    const data = await rideApi.createRide();
    currentRideId = data.id;
    ui.setRideId(data.id);
    ui.showRidePanel();
    applyState(data.stateName);
    ui.appendLog("createRide", data.message);
  } catch (err) {
    ui.showError(err.message);
  } finally {
    setLoading(false);
  }
}

async function handleAction(action) {
  if (loading || !currentRideId) return;
  setLoading(true);
  try {
    const data = await rideApi.performAction(currentRideId, action);
    applyState(data.stateName);
    ui.appendLog(action, data.message);
  } catch (err) {
    ui.showError(err.message);
  } finally {
    setLoading(false);
  }
}

function handleReset() {
  currentRideId = null;
  currentState = null;
  ui.showEmptyState();
}

document
  .getElementById("btn-new-ride")
  .addEventListener("click", handleNewRide);
document
  .getElementById("btn-new-ride-2")
  .addEventListener("click", handleReset);
document.getElementById("btn-copy-id").addEventListener("click", () => {
  if (currentRideId) {
    navigator.clipboard.writeText(currentRideId).then(
      () => ui.appendLog("copyId", "ID copiado al portapapeles"),
      () => ui.showError("No se pudo copiar el ID"),
    );
  }
});
