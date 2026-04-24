// ui.js - Funciones de ayuda para el renderizado del DOM

const STEPS = [
  { key: "REQUESTING", label: "Solicitado" },
  { key: "DRIVER_ASSIGNED", label: "Asignado" },
  { key: "DRIVER_ARRIVING", label: "En camino" },
  { key: "IN_TRIP", label: "En viaje" },
  { key: "COMPLETED", label: "Completado" },
];

export const ACTION_LABELS = {
  requestRide: "Solicitar viaje",
  assignDriver: "Asignar conductor",
  driverArrives: "Conductor llega",
  startTrip: "Iniciar viaje",
  completeTrip: "Completar viaje",
  cancel: "Cancelar viaje",
};

const BADGE_LABELS = {
  REQUESTING: "SOLICITANDO",
  DRIVER_ASSIGNED: "CONDUCTOR ASIGNADO",
  DRIVER_ARRIVING: "CONDUCTOR EN CAMINO",
  IN_TRIP: "EN VIAJE",
  COMPLETED: "COMPLETADO",
  CANCELLED: "CANCELADO",
};

const ALL_ACTIONS = [
  "requestRide",
  "assignDriver",
  "driverArrives",
  "startTrip",
  "completeTrip",
  "cancel",
];

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
      navigator.clipboard.writeText(rideId).then(
        () => this.showSuccessToast("ID copiado al portapapeles"),
        () => this.showErrorToast("No se pudo copiar el ID"),
      );
    });

    const actionContainer = card.querySelector(".action-grid");
    ALL_ACTIONS.forEach((action) => {
      const btn = document.createElement("button");
      const isCancel = action === "cancel";
      btn.className = `btn ${isCancel ? "btn-danger" : "btn-secondary"}`;
      btn.textContent = ACTION_LABELS[action];
      // A propósito NO deshabilitamos los botones aquí, para dejar que el backend valide el estado.
      btn.dataset.action = action;
      btn.addEventListener("click", () => onAction(rideId, action));
      actionContainer.appendChild(btn);
    });

    container.prepend(card);
    return card;
  },

  updateRideCard(rideId, stateName) {
    const card = document.getElementById(`ride-${rideId}`);
    if (!card) return;

    // Actualizar Etiqueta (Badge)
    const badge = card.querySelector(".state-badge");
    badge.textContent = BADGE_LABELS[stateName] || stateName.replace(/_/g, " ");
    badge.className = "state-badge";
    if (stateName === "CANCELLED") badge.classList.add("cancelled");
    if (stateName === "COMPLETED") badge.classList.add("completed");

    const timeline = card.querySelector(".timeline");
    timeline.innerHTML = "";

    if (stateName === "CANCELLED") {
      STEPS.forEach((step) => {
        const el = document.createElement("div");
        el.className = "timeline-step";
        el.innerHTML = `<div class="step-dot">✕</div><span class="step-label">${step.label}</span>`;
        timeline.appendChild(el);
      });
      const cancelled = document.createElement("div");
      cancelled.className = "timeline-step cancelled-branch";
      cancelled.innerHTML =
        '<div class="step-dot">✕</div><span class="step-label">Cancelado</span>';
      timeline.appendChild(cancelled);
    } else {
      const currentIdx = STEPS.findIndex((s) => s.key === stateName);
      STEPS.forEach((step, idx) => {
        const el = document.createElement("div");
        let cls = "timeline-step";
        if (idx < currentIdx) cls += " done";
        else if (idx === currentIdx) cls += " active";
        el.className = cls;
        const check = idx < currentIdx ? "✓" : idx === currentIdx ? "●" : "";
        el.innerHTML = `<div class="step-dot">${check}</div><span class="step-label">${step.label}</span>`;
        timeline.appendChild(el);
      });
    }
  },

  setCardLoading(rideId, loading) {
    const card = document.getElementById(`ride-${rideId}`);
    if (!card) return;
    card.querySelectorAll(".action-grid .btn").forEach((btn) => {
      btn.disabled = loading;
    });
  },

  showErrorToast(message) {
    const toast = document.getElementById("error-toast");
    toast.innerHTML = `<span> ${message}</span>`;
    toast.style.background = "var(--danger)";
    this._showToast(toast);
  },

  showSuccessToast(message) {
    const toast = document.getElementById("error-toast");
    toast.innerHTML = `<span> ${message}</span>`;
    toast.style.background = "var(--success)";
    this._showToast(toast);
  },

  _showToast(toast) {
    toast.classList.remove("hidden");
    clearTimeout(toast._timer);
    toast._timer = setTimeout(() => toast.classList.add("hidden"), 4000);
  },
};
