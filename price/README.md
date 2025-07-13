





Compila el proyecto:
./mvnw clean package
Construye la imagen Docker:
docker build -t price-app .
Ejecuta el contenedor:
docker run -p 8080:8080 price-app
Esto te permitirá ejecutar tu aplicación Spring Boot en un contenedor Docker.