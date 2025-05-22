# 1. Base image
FROM openjdk:17-jdk-slim

# 2. Workdir (container içində app üçün qovluq)
WORKDIR /app

# 3. JAR faylını konteynerə köçür
COPY build/libs/*.jar app.jar

# 4. Run tətbiq
ENTRYPOINT ["java", "-jar", "app.jar"]
