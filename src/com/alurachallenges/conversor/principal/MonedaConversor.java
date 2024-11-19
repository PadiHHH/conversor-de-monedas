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
            scanner.nextLine(); // Limpia el buffer

            if (opcion == 8) {
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
                7) Conversión personalizada
                8) Salir
                
                Códigos comunes de monedas:
                USD (Dólar) | EUR (Euro) | GBP (Libra) | JPY (Yen)
                MXN (Peso Mexicano) | CHF (Franco Suizo) | CLP (Peso Chileno)
                
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
            case 7 -> {
                monedaBase = solicitarCodigoMoneda("origen");
                monedaTarget = solicitarCodigoMoneda("destino");
            }
            default -> {
                System.out.println("Opción no válida");
                return;
            }
        }

        conversion(monedaBase, monedaTarget);
    }

    private static String solicitarCodigoMoneda(String tipo) {
        while (true) {
            System.out.printf("Ingrese el código de la moneda de %s (ejemplo: USD, EUR, GBP): ", tipo);
            String codigo = scanner.nextLine().toUpperCase().trim();

            if (codigo.length() == 3 && codigo.matches("[A-Z]{3}")) {
                return codigo;
            } else {
                System.out.println("Código de moneda inválido. Debe ser un código de 3 letras.");
            }
        }
    }

    private static void conversion(String monedaBase, String monedaTarget) {
        try {
            System.out.println("Ingrese el valor que deseas convertir: ");
            double monto = scanner.nextDouble();
            scanner.nextLine(); // Limpia el buffer

            if (monto <= 0) {
                System.out.println("El monto debe ser mayor que cero");
                return;
            }

            MonedaRecord resultado = monedaAPI.obtenerConversion(monedaBase, monedaTarget, monto);
            mostrarResultado(resultado, monto);

        } catch (IllegalArgumentException e) {
            System.out.println("\n¡Error! " + e.getMessage());
            System.out.println("""
                Códigos de moneda comunes:
                - USD: Dólar estadounidense    - EUR: Euro
                - GBP: Libra esterlina        - JPY: Yen japonés
                - ARS: Peso argentino         - BRL: Real brasileño
                - COP: Peso colombiano        - MXN: Peso mexicano
                - CLP: Peso chileno           - PEN: Sol peruano
                """);
        } catch (RuntimeException e) {
            System.out.println("\n¡Error del servicio! " + e.getMessage());
            System.out.println("Por favor, intente nuevamente más tarde.");
        } catch (Exception e) {
            System.out.println("\n¡Error inesperado! " + e.getMessage());
            System.out.println("Por favor, contacte al soporte técnico.");
        }
    }

    private static void mostrarResultado(MonedaRecord conversion, double monto) {
        System.out.printf("%n=== Resultado de la Conversión ===%n");
        System.out.printf("Monto original: %.2f %s%n", monto, conversion.base_code());
        System.out.printf("Tasa de cambio: %.4f%n", conversion.conversion_rate());
        System.out.printf("Monto convertido: %.2f %s%n%n",
                conversion.conversion_result(),
                conversion.target_code());
    }
}