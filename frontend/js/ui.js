// ui.js - DOM rendering helpers

const STEPS = [
  { key: "REQUESTING", label: "Solicitado" },
  { key: "DRIVER_ASSIGNED", label: "Asignado" },
  { key: "DRIVER_ARRIVING", label: "En camino" },
  { key: "IN_TRIP", label: "En viaje" },
  { key: "COMPLETED", label: "Completado" },
];

const VALID_ACTIONS = {
  REQUESTING: ["assignDriver", "cancel"],
  DRIVER_ASSIGNED: ["driverArrives", "cancel"],
  DRIVER_ARRIVING: ["startTrip", "cancel"],
  IN_TRIP: ["completeTrip"],
  COMPLETED: [],
  CANCELLED: [],
};

const ACTION_LABELS = {
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

const LOG_LABELS = {
  ...ACTION_LABELS,
  createRide: "Crear viaje",
  copyId: "Copiar ID",
};

export const ui = {
  showRidePanel() {
    document.getElementById("no-ride").classList.add("hidden");
    document.getElementById("ride-panel").classList.remove("hidden");
  },

  showEmptyState() {
    document.getElementById("no-ride").classList.remove("hidden");
    document.getElementById("ride-panel").classList.add("hidden");
  },

  setRideId(id) {
    document.getElementById("ride-id").textContent = id;
  },

  updateStateBadge(stateName) {
    const badge = document.getElementById("state-badge");
    badge.textContent = BADGE_LABELS[stateName] || stateName.replace(/_/g, " ");
    badge.className = "state-badge";
    if (stateName === "CANCELLED") badge.classList.add("cancelled");
    if (stateName === "COMPLETED") badge.classList.add("completed");
    void badge.offsetWidth;
    badge.classList.add("pulse");
    badge.addEventListener(
      "animationend",
      () => badge.classList.remove("pulse"),
      {
        once: true,
      },
    );
  },

  renderTimeline(currentState) {
    const container = document.getElementById("timeline");
    container.innerHTML = "";

    if (currentState === "CANCELLED") {
      STEPS.forEach((step) => {
        const el = document.createElement("div");
        el.className = "timeline-step";
        el.innerHTML = `<div class="step-dot">✕</div><span class="step-label">${step.label}</span>`;
        container.appendChild(el);
      });
      const cancelled = document.createElement("div");
      cancelled.className = "timeline-step cancelled-branch";
      cancelled.innerHTML =
        '<div class="step-dot">✕</div><span class="step-label">Cancelado</span>';
      container.appendChild(cancelled);
      return;
    }

    const currentIdx = STEPS.findIndex((s) => s.key === currentState);
    STEPS.forEach((step, idx) => {
      const el = document.createElement("div");
      let cls = "timeline-step";
      if (idx < currentIdx) cls += " done";
      else if (idx === currentIdx) cls += " active";
      el.className = cls;
      const check = idx < currentIdx ? "✓" : idx === currentIdx ? "●" : "";
      el.innerHTML = `<div class="step-dot">${check}</div><span class="step-label">${step.label}</span>`;
      container.appendChild(el);
    });
  },

  renderActionButtons(currentState, onAction) {
    const container = document.getElementById("action-buttons");
    container.innerHTML = "";
    const valid = VALID_ACTIONS[currentState] || [];

    ALL_ACTIONS.forEach((action) => {
      const btn = document.createElement("button");
      const isCancel = action === "cancel";
      btn.className = `btn ${isCancel ? "btn-danger" : "btn-secondary"}`;
      btn.textContent = ACTION_LABELS[action];
      btn.disabled = !valid.includes(action);
      btn.addEventListener("click", () => onAction(action));
      container.appendChild(btn);
    });
  },

  setButtonsLoading(loading) {
    document.querySelectorAll(".action-grid .btn").forEach((btn) => {
      btn.disabled = loading;
    });
  },

  appendLog(action, result) {
    const list = document.getElementById("log-list");
    const now = new Date().toLocaleTimeString("es-CO");
    const actionLabel = LOG_LABELS[action] || action;
    const li = document.createElement("li");
    li.className = "log-entry";
    li.innerHTML = `
			<span class="log-time">${now}</span>
			<span class="log-action">${actionLabel}</span>
			<span class="log-result">${result}</span>
		`;
    list.prepend(li);
    while (list.children.length > 15) list.removeChild(list.lastChild);
  },

  showNewRideButton(show) {
    document.getElementById("btn-new-ride-2").classList.toggle("hidden", !show);
  },

  showError(message) {
    const toast = document.getElementById("error-toast");
    toast.textContent = message;
    toast.classList.remove("hidden");
    clearTimeout(toast._timer);
    toast._timer = setTimeout(() => toast.classList.add("hidden"), 4000);
  },
};
