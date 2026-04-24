let activeTimer = null;

export function showToast(message, type = "error") {
  const toast = document.getElementById("error-toast");
  toast.textContent = message;
  toast.style.background =
    type === "success" ? "var(--success)" : "var(--danger)";
  toast.classList.remove("hidden");
  clearTimeout(activeTimer);
  activeTimer = setTimeout(() => toast.classList.add("hidden"), 4000);
}
