FROM maven:3.6.3-openjdk-15
ENV APP_ROOT=/opt/app

WORKDIR ${APP_ROOT}

COPY pom.xml ${APP_ROOT}/
COPY SearchResults/pom.xml ${APP_ROOT}/SearchResults/
COPY HotspotOptimizerLibrary/pom.xml ${APP_ROOT}/HotspotOptimizerLibrary/
COPY SearchService/pom.xml ${APP_ROOT}/SearchService/

RUN mvn install -DskipTests

COPY SearchResults/src/ ${APP_ROOT}/SearchResults/src
COPY HotspotOptimizerLibrary/src/ ${APP_ROOT}/SearchResults/src
COPY SearchService/src/ ${APP_ROOT}/SearchService/src
RUN mvn compile package -DskipTests

RUN cd ${APP_ROOT}/SearchService && mvn package spring-boot:repackage -DskipTests

WORKDIR ${APP_ROOT}/SearchService/target

CMD ["java", "-jar", "SearchService-1.0.0-SNAPSHOT.jar" ]