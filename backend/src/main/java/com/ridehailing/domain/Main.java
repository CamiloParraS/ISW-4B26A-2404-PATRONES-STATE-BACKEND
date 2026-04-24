package com.ridehailing.domain;

/**
 * Demostración independiente del patrón State sin Spring Boot.
 * Ejercita cada transición de estado y muestra cómo se manejan
 * las acciones inválidas.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("═══════════════════════════════════════════");
        System.out.println("  PATRÓN STATE — Demostración Ride-Hailing");
        System.out.println("═══════════════════════════════════════════");

        // ── Escenario A: flujo completo (camino feliz) ──
        System.out.println("\n▶ Escenario A: Flujo completo (camino feliz)");
        Ride viaje1 = new Ride();
        imprimirEstado(viaje1);

        viaje1.assignDriver();
        imprimirEstado(viaje1);

        viaje1.driverArrives();
        imprimirEstado(viaje1);

        viaje1.startTrip();
        imprimirEstado(viaje1);

        viaje1.completeTrip();
        imprimirEstado(viaje1);

        System.out.println("   Historial: " + viaje1.getTransitionHistory());

        // ── Escenario B: cancelación a mitad del flujo ──
        System.out.println("\n▶ Escenario B: Cancelación a mitad del flujo");
        Ride viaje2 = new Ride();
        viaje2.assignDriver();
        imprimirEstado(viaje2);

        viaje2.cancel();
        imprimirEstado(viaje2);

        intentarAccionInvalida("Iniciar viaje después de cancelar",
                () -> viaje2.startTrip());

        System.out.println("   Historial: " + viaje2.getTransitionHistory());

        // ── Escenario C: transiciones inválidas desde estado inicial ──
        System.out.println("\n▶ Escenario C: Transiciones inválidas desde estado inicial");
        Ride viaje3 = new Ride();

        intentarAccionInvalida("Completar viaje sin conductor asignado",
                () -> viaje3.completeTrip());

        intentarAccionInvalida("Iniciar viaje sin conductor asignado",
                () -> viaje3.startTrip());

        intentarAccionInvalida("Solicitar viaje que ya está solicitado",
                () -> viaje3.requestRide());

        // ── Escenario D: no se puede cancelar durante el viaje ──
        System.out.println("\n▶ Escenario D: No se puede cancelar durante el viaje");
        Ride viaje4 = new Ride();
        viaje4.assignDriver();
        viaje4.driverArrives();
        viaje4.startTrip();
        imprimirEstado(viaje4);

        intentarAccionInvalida("Cancelar viaje en curso",
                () -> viaje4.cancel());

        System.out.println("   Historial: " + viaje4.getTransitionHistory());
    }

    /** Imprime el estado actual del viaje en consola. */
    private static void imprimirEstado(Ride ride) {
        System.out.println("   Estado actual: " + ride.getStateName());
    }

    /**
     * Ejecuta una acción que se espera sea inválida y captura la excepción.
     * Demuestra el manejo correcto de transiciones no permitidas.
     */
    private static void intentarAccionInvalida(String descripcion, Runnable accion) {
        try {
            accion.run();
            System.out.println("   ⚠ " + descripcion + " — no lanzó excepción (inesperado)");
        } catch (IllegalStateException e) {
            System.out.println("   ✗ " + descripcion + " → " + e.getMessage());
        }
    }
}
