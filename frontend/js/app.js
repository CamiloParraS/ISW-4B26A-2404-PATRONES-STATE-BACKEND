// app.js - Conexión de eventos y estado de la aplicación

import { rideApi } from "./api.js";
import { ui, ACTION_LABELS } from "./ui.js";

const rides = {}; // Almacena los estados de los viajes por ID

function updateEmptyState() {
  const hasRides = Object.keys(rides).length > 0;
  ui.showEmptyState(!hasRides);
}

async function handleNewRide() {
  const btn = document.getElementById("btn-new-ride");
  btn.disabled = true;
  btn.textContent = "Creando...";
  try {
    const data = await rideApi.createRide();
    rides[data.id] = data.stateName;
    ui.createRideCard(data.id, handleAction);
    ui.updateRideCard(data.id, data.stateName);
    updateEmptyState();
  } catch (err) {
    ui.showErrorToast(err.message);
  } finally {
    btn.disabled = false;
    btn.textContent = "+ Nuevo Viaje";
  }
}

async function handleAction(rideId, action) {
  if (!rides[rideId]) return;

  ui.setCardLoading(rideId, true);
  try {
    const data = await rideApi.performAction(rideId, action);
    rides[rideId] = data.stateName;
    ui.updateRideCard(rideId, data.stateName);
    ui.showSuccessToast(`Acción completada: ${ACTION_LABELS[action]}`);
  } catch (err) {
    ui.updateRideCard(rideId, rides[rideId]);
    ui.showErrorToast(err.message);
  } finally {
    ui.setCardLoading(rideId, false);
  }
}

document
  .getElementById("btn-new-ride")
  .addEventListener("click", handleNewRide);
updateEmptyState();
