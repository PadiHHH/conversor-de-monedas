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
                ARS (Peso Argentino) | BRL (Real) | COP (Peso Colombiano)
                
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
        } catch (NumberFormatException e) {
            System.out.println("Ingrese un número válido");
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
            if (e.getMessage().contains("404")) {
                System.out.println("Es posible que uno de los códigos de moneda ingresados no sea válido.");
            }
        } catch (Exception e) {
            System.out.println("Error al realizar la conversión: " + e.getMessage());
        }
    }

    private static void mostrarResultado(MonedaRecord conversion, double monto) {
        System.out.printf("%nTasa de cambio: %.4f%n", conversion.conversion_rate());
        System.out.printf("%.2f %s = %.2f %s%n%n",
                monto,
                conversion.base_code(),
                conversion.conversion_result(),
                conversion.target_code());
    }
}