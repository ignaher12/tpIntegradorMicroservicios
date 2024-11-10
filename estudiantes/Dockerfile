# Usa una imagen de Java como base
FROM openjdk:17-jdk-slim

# Establece el directorio de trabajo en /app
WORKDIR /app

# Copia el archivo JAR del microservicio en el contenedor
# Asegúrate de que el archivo JAR se genere correctamente con el nombre adecuado
COPY target/EstudianteService-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto en el que el microservicio escucha (4000 para estudiantes y 4001 para inscripciones)
EXPOSE 4000 

# Ejecuta el microservicio
ENTRYPOINT ["java", "-jar", "app.jar"]
