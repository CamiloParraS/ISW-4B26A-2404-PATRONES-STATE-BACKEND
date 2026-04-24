const STEPS = [
  { key: "REQUESTING", label: "Solicitado" },
  { key: "DRIVER_ASSIGNED", label: "Asignado" },
  { key: "DRIVER_ARRIVING", label: "En camino" },
  { key: "IN_TRIP", label: "En viaje" },
  { key: "COMPLETED", label: "Completado" },
];

function createStep(label, dotContent, extraClass) {
  const element = document.createElement("div");
  element.className = `timeline-step ${extraClass}`.trim();
  element.innerHTML = `<div class="step-dot">${dotContent}</div>
                    <span class="step-label">${label}</span>`;
  return element;
}

export function renderTimeline(container, stateName) {
  container.innerHTML = "";

  if (stateName === "CANCELLED") {
    STEPS.forEach((step) =>
      container.appendChild(createStep(step.label, "✕", "")),
    );
    container.appendChild(createStep("Cancelado", "✕", "cancelled-branch"));
    return;
  }

  const currentIdx = STEPS.findIndex((step) => step.key === stateName);
  STEPS.forEach((step, idx) => {
    const isPast = idx < currentIdx;
    const isActive = idx === currentIdx;
    const className = isPast ? "done" : isActive ? "active" : "";
    const dot = isPast ? "✓" : isActive ? "●" : "";
    container.appendChild(createStep(step.label, dot, className));
  });
}
