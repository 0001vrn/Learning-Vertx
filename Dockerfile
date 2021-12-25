FROM eclipse-temurin:17

ENV VERTICLE_FILE learning-vertx-app.jar

# Set the location of the verticles
ENV VERTICLE_HOME /usr/verticles

RUN mkdir $VERTICLE_HOME
RUN mkdir $VERTICLE_HOME/www

EXPOSE 8080

COPY build/libs/$VERTICLE_FILE $VERTICLE_HOME
COPY build/resources/main/webroot $VERTICLE_HOME/www

WORKDIR $VERTICLE_HOME

ENTRYPOINT ["sh", "-c"]

CMD ["exec java -jar $VERTICLE_FILE"]
