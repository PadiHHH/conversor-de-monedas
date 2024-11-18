package com.alurachallenges.conversor.principal;

import com.alurachallenges.conversor.modelos.MonedaRecord;
import java.util.Scanner;

public class MonedaConversor {
    private static final Scanner scanner = new Scanner(System.in);
    private static final MonedaAPI monedaAPI = new MonedaAPI();

    public static void main(String[] args) {
        while (true) {
            menu();
            int opcion = scanner.nextInt();

            if (opcion == 7) {
                System.out.println("¡Gracias por usar nuestro conversor de monedas!");
                break;
            }

            menuOpcion(opcion);
        }
    }

    private static void menu() {
        System.out.println("""
                *********************************************************
                1) Dólar           ---> Peso Argentino
                2) Peso Argentino  ---> Dólar
                3) Dólar           ---> Real Brasileño
                4) Real Brasileño  ---> Dólar
                5) Dólar           ---> Peso Colombiano
                6) Peso Colombiano ---> Dólar
                7) Salir
                Elija una opción válida:
                *********************************************************
                """);
    }

    private static void menuOpcion(int opcion) {
        String monedaBase = "";
        String monedaTarget = "";

        switch (opcion) {
            case 1 -> {
                monedaBase = "USD";
                monedaTarget = "ARS";
            }
            case 2 -> {
                monedaBase = "ARS";
                monedaTarget = "USD";
            }
            case 3 -> {
                monedaBase = "USD";
                monedaTarget = "BRL";
            }
            case 4 -> {
                monedaBase = "BRL";
                monedaTarget = "USD";
            }
            case 5 -> {
                monedaBase = "USD";
                monedaTarget = "COP";
            }
            case 6 -> {
                monedaBase = "COP";
                monedaTarget = "USD";
            }
            default -> {
                System.out.println("Opción no válida");
                return;
            }
        }

        conversion(monedaBase, monedaTarget);
    }

    private static void conversion(String monedaBase, String monedaTarget) {
        try {
            System.out.println("Ingrese el valor que deseas convertir: ");
            double monto = scanner.nextDouble();

            if (monto <= 0) {
                System.out.println("El monto debe ser mayor que cero");
                return;
            }

            MonedaRecord resultado = monedaAPI.obtenerConversion(monedaBase, monedaTarget, monto);
            mostrarResultado(resultado, monto);
        } catch (NumberFormatException e) {
            System.out.println("Ingrese un número válido");
        } catch (Exception e) {
            System.out.println("Error al realizar la conversión: " + e.getMessage());
        }
    }

    private static void mostrarResultado(MonedaRecord conversion, double monto) {
        System.out.printf("La tasa de cambio es: " + conversion.conversion_rate() + "%n Por lo tanto el valor "
                          + monto + " [" + conversion.base_code() + "] corresponde al valor final de "
                          + conversion.conversion_result() + " [" + conversion.target_code() + "]%n");
    }
}