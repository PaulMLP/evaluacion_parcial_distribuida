# Generar la imagen

#   docker:
#       docker build -t paulmlp/app-productos .
#       docker login
#       docker push paulmlp/app-productos
FROM eclipse-temurin:21-jre-alpine

RUN mkdir /app
WORKDIR /app

COPY  build/quarkus-app/app ./app
COPY  build/quarkus-app/lib ./lib
COPY  build/quarkus-app/quarkus ./quarkus
COPY  build/quarkus-app/quarkus-run.jar app.jar

CMD ["java", "-jar", "app.jar"]