import { renderTimeline } from "./timeline.js";
import { showToast } from "./toast.js";

const BADGE_LABELS = {
  REQUESTING: "SOLICITANDO",
  DRIVER_ASSIGNED: "CONDUCTOR ASIGNADO",
  DRIVER_ARRIVING: "CONDUCTOR EN CAMINO",
  IN_TRIP: "EN VIAJE",
  COMPLETED: "COMPLETADO",
  CANCELLED: "CANCELADO",
};

export const ACTION_LABELS = {
  assignDriver: "Asignar conductor",
  driverArrives: "Conductor llega",
  startTrip: "Iniciar viaje",
  completeTrip: "Completar viaje",
  cancel: "Cancelar viaje",
};

const ALL_ACTIONS = Object.keys(ACTION_LABELS);

export const ui = {
  showEmptyState(show) {
    document.getElementById("no-ride").classList.toggle("hidden", !show);
  },

  createRideCard(rideId, onAction) {
    const template = document.getElementById("ride-card-template");
    const container = document.getElementById("rides-container");

    const clone = template.content.cloneNode(true);
    const card = clone.querySelector(".ride-card");
    card.id = `ride-${rideId}`;

    card.querySelector(".ride-id").textContent = rideId;

    card.querySelector(".btn-copy-id").addEventListener("click", () => {
      navigator.clipboard
        .writeText(rideId)
        .then(() => showToast("ID copiado al portapapeles", "success"))
        .catch(() => showToast("No se pudo copiar el ID", "error"));
    });

    this._appendActionButtons(
      card.querySelector(".action-grid"),
      rideId,
      onAction,
    );
    container.prepend(card);
  },

  _appendActionButtons(grid, rideId, onAction) {
    ALL_ACTIONS.forEach((action) => {
      const btn = document.createElement("button");
      btn.className = `btn ${action === "cancel" ? "btn-danger" : "btn-secondary"}`;
      btn.textContent = ACTION_LABELS[action];
      btn.dataset.action = action;
      btn.addEventListener("click", () => onAction(rideId, action));
      grid.appendChild(btn);
    });
  },

  updateRideCard(rideId, stateName) {
    const card = document.getElementById(`ride-${rideId}`);
    if (!card) return;

    this._updateBadge(card.querySelector(".state-badge"), stateName);
    renderTimeline(card.querySelector(".timeline"), stateName);
  },

  _updateBadge(badge, stateName) {
    badge.textContent = BADGE_LABELS[stateName] ?? stateName.replace(/_/g, " ");
    badge.className = "state-badge";
    if (stateName === "CANCELLED") badge.classList.add("cancelled");
    if (stateName === "COMPLETED") badge.classList.add("completed");
  },

  setCardLoading(rideId, loading) {
    const card = document.getElementById(`ride-${rideId}`);
    if (!card) return;
    card.querySelectorAll(".action-grid .btn").forEach((btn) => {
      btn.disabled = loading;
    });
  },

  showErrorToast(message) {
    showToast(message, "error");
  },

  showSuccessToast(message) {
    showToast(message, "success");
  },
};
