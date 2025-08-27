# Dockerfile
# Build stage
FROM eclipse-temurin:17-jdk-jammy AS build
WORKDIR /app

# copia wrapper & pom para cache de dependencias
COPY mvnw ./
COPY .mvn .mvn
COPY pom.xml .

# cache dependencies
RUN ./mvnw -B -DskipTests dependency:go-offline


# Copia código y empaqueta
COPY src ./src
RUN ./mvnw -B clean package -DskipTests


# Runtime stage
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
ARG JAR_FILE=target/*.jar
COPY --from=build /app/target/*.jar app.jar
ENV JAVA_OPTS=""
EXPOSE 8080
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.jar"]
