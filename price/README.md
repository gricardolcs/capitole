
# Price Management API

## Descripción

Esta aplicación Spring Boot proporciona un servicio REST para consultar precios de productos en cadenas comerciales. La aplicación gestiona una tabla de precios con diferentes tarifas que se aplican a productos específicos en rangos de fechas determinados.

## Características

- **API REST**: Endpoint para consultar precios con parámetros de entrada específicos
- **Base de datos en memoria**: Utiliza H2 para almacenamiento temporal
- **Manejo de prioridades**: Cuando múltiples tarifas coinciden en un rango de fechas, se aplica la de mayor prioridad
- **Validación completa**: Manejo de errores y validación de parámetros
- **Documentación OpenAPI**: Swagger UI disponible para probar la API
- **Tests completos**: Suite de tests de integración para validar todos los casos de uso

## Estructura de la Base de Datos

### Tabla PRICES

| Campo | Tipo | Descripción |
|-------|------|-------------|
| BRAND_ID | Integer | Foreign key de la cadena del grupo (1 = ZARA) |
| START_DATE | LocalDateTime | Fecha de inicio del rango de aplicación |
| END_DATE | LocalDateTime | Fecha de fin del rango de aplicación |
| PRICE_LIST | Integer | Identificador de la tarifa de precios |
| PRODUCT_ID | Integer | Identificador del producto |
| PRIORITY | Integer | Prioridad para desambiguar tarifas (mayor valor = mayor prioridad) |
| PRICE | Double | Precio final de venta |
| CURR | String | ISO de la moneda |

### Datos de Prueba

```sql
INSERT INTO PRICE (brand_id, start_date, end_date, price_list, product_id, priority, price, curr) VALUES
(1, '2020-06-14T00:00:00', '2020-12-31T23:59:59', 1, 35455, 0, 35.50, 'EUR'),
(1, '2020-06-14T15:00:00', '2020-06-14T18:30:00', 2, 35455, 1, 25.45, 'EUR'),
(1, '2020-06-15T00:00:00', '2020-06-15T11:00:00', 3, 35455, 1, 30.50, 'EUR'),
(1, '2020-06-15T16:00:00', '2020-12-31T23:59:59', 4, 35455, 1, 38.95, 'EUR');
```

## API Endpoints

### GET /api/prices

Obtiene el precio aplicable de un producto para una marca específica en una fecha determinada.

#### Parámetros de Entrada

- `date` (String, requerido): Fecha de aplicación en formato `yyyy-MM-dd-HH.mm.ss`
- `productId` (Integer, requerido): Identificador del producto
- `brandId` (Integer, requerido): Identificador de la marca/cadena

#### Ejemplo de Petición

```bash
GET /api/prices?date=2020-06-14-16.00.00&productId=35455&brandId=1
```

#### Respuesta Exitosa (200 OK)

```json
{
  "productId": 35455,
  "brandId": 1,
  "priceList": 2,
  "startDate": "2020-06-14-15.00.00",
  "endDate": "2020-06-14-18.30.00",
  "price": 25.45,
  "curr": "EUR"
}
```

#### Respuestas de Error

- **400 Bad Request**: Parámetros inválidos o formato de fecha incorrecto
- **404 Not Found**: No se encontró precio para los parámetros especificados
- **500 Internal Server Error**: Error interno del servidor

## Casos de Prueba

La aplicación incluye 5 tests principales que validan los siguientes escenarios:

1. **Test 1**: Petición a las 10:00 del día 14 de junio de 2020 → Precio: 35.50€
2. **Test 2**: Petición a las 16:00 del día 14 de junio de 2020 → Precio: 25.45€ (mayor prioridad)
3. **Test 3**: Petición a las 21:00 del día 14 de junio de 2020 → Precio: 35.50€
4. **Test 4**: Petición a las 10:00 del día 15 de junio de 2020 → Precio: 30.50€
5. **Test 5**: Petición a las 21:00 del día 16 de junio de 2020 → Precio: 38.95€

## Cómo Ejecutar

### Prerrequisitos

- Java 17 o superior
- Maven 3.6 o superior

### Ejecutar la Aplicación

```bash
mvn spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8080`

### Ejecutar Tests

```bash
mvn test
```

### Acceder a la Documentación

- **Swagger UI**: `http://localhost:8080/swagger-ui/index.html`
- **Consola H2**: `http://localhost:8080/h2-console` (solo en desarrollo)

## Arquitectura

La aplicación sigue el patrón de arquitectura por capas:

- **Controller**: Maneja las peticiones HTTP y las respuestas
- **Service**: Contiene la lógica de negocio
- **Repository**: Acceso a datos con Spring Data JPA
- **Model**: Entidades JPA
- **DTO**: Objetos de transferencia de datos
- **Exception**: Manejo centralizado de excepciones

## Docker
Para ejecutar la aplicación en un contenedor Docker, sigue estos pasos:
```bash
./mvnw clean package
docker build -t price-app .
docker run -p 8080:8080 price-app
```


## Tecnologías Utilizadas

- Spring Boot 3.5.3
- Spring Data JPA
- Spring Web
- H2 Database
- Lombok
- Docker
- OpenAPI/Swagger
- JUnit 5
- Maven

## Notas Técnicas

- La aplicación utiliza H2 como base de datos en memoria
- Los datos se inicializan automáticamente al arrancar la aplicación
- La consulta de precios utiliza prioridades para resolver conflictos
- Se incluye logging detallado para debugging
- Los tests utilizan un perfil separado para evitar interferencias
