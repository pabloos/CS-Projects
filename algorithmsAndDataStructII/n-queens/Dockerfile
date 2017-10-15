FROM openjdk

COPY src/reinas.java /usr/src/
WORKDIR /usr/src/

RUN javac reinas.java
CMD ["java", "reinas -h"]

RUN echo run the container now. For example: docker run -it --rm reinas java reinas -g 8