FROM openjdk

COPY src/multiplica.java /usr/src/
COPY test/* /usr/src/

WORKDIR /usr/src/

RUN javac multiplica.java
CMD ["java", "multiplica -h"]