# ---------- Etapa 1: Build ----------
FROM amazoncorretto:17-alpine AS build
WORKDIR /app

# Copia o wrapper e configura permissões
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Instala o utilitário para converter quebras de linha do Windows
RUN apk add --no-cache dos2unix

# Corrige formato e permissões do mvnw
RUN dos2unix mvnw && chmod +x mvnw

# Baixa dependências para cachear o Maven
RUN ./mvnw dependency:go-offline || true

# Copia o código-fonte e compila o projeto
COPY src src
RUN ./mvnw clean package -DskipTests

# ---------- Etapa 2: Runtime ----------
FROM amazoncorretto:17-alpine
WORKDIR /app

# Copia o .jar gerado da etapa de build
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta e define o ponto de entrada
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
