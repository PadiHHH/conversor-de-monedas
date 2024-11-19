<img src="https://img.shields.io/badge/STATUS-EN%20DESAROLLO-green">

## Descripción del proyecto

Challenge para obtener el Certificado Java Orientado a Objetos por Alura LATAM y Oracle ONE.
Consiste en crear una aplicación que permita la conversión de monedas mediante el consumo de una API.

## Tecnologías utilizadas

- Java 17
- IntelliJ IDEA
- [ExchangeRate API](https://www.exchangerate-api.com/) - API para tasas de cambio
- Gson - Para el manejo de JSON
- Maven - Gestión de dependencias

## Características

- Conversión entre monedas predefinidas (USD, ARS, BRL, COP)
- Opción de conversión personalizada con cualquier código de moneda válido
- Tasas de cambio en tiempo real
- Interfaz de consola intuitiva
- Manejo de errores
- Validación de entrada de usuario


## Prerrequisitos

- Java JDK 17 o superior
- Maven
- Una API key de ExchangeRate-API

## Configuración

1. Clona el repositorio:
```bash
git clone https://github.com/tu-usuario/conversor-de-monedas.git
```

2. Navega al directorio del proyecto:
```bash
cd conversor-de-monedas
```

3. Actualiza la API key en `MonedaAPI.java`:
```java
private final String apiKey = "tu-api-key";
```

4. Compila el proyecto:
```bash
mvn clean install
```

## Estructura del Proyecto

```
conversor-de-monedas/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── alurachallenges/
│                   └── conversor/
│                       ├── modelos/
│                       │   └── MonedaRecord.java
│                       └── principal/
│                           ├── MonedaAPI.java
│                           └── MonedaConversor.java
├── pom.xml
└── README.md
```

## Uso

El programa ofrece las siguientes opciones de conversión:

1. Dólar → Peso Argentino
2. Peso Argentino → Dólar
3. Dólar → Real Brasileño
4. Real Brasileño → Dólar
5. Dólar → Peso Colombiano
6. Peso Colombiano → Dólar
7. Conversión personalizada
8. Salir

Para usar la conversión personalizada, necesitarás conocer los códigos de moneda de 3 letras (por ejemplo: USD, EUR, GBP).

### Ejemplo de Uso

```java
// Ejemplo de conversión de 100 USD a EUR
1. Selecciona la opción 7 (Conversión personalizada)
2. Ingresa "USD" como moneda de origen
3. Ingresa "EUR" como moneda de destino
4. Ingresa "100" como monto
```
