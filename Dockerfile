# Tomcat 9 is the last line implementing javax.servlet, and it ships Jasper,
# so the JSPs compile on first request the way they did in 1999. The -jdk17
# image has a full JDK, so the Java half builds with plain javac against
# Tomcat's own servlet-api.jar and jsp-api.jar. No Maven.
FROM tomcat:9.0-jdk17-temurin

RUN rm -rf /usr/local/tomcat/webapps/*

COPY webapp/ /usr/local/tomcat/webapps/ROOT/
COPY src/    /build/src/

RUN mkdir -p /usr/local/tomcat/webapps/ROOT/WEB-INF/classes \
 && find /build/src -name '*.java' > /build/sources.txt \
 && javac -nowarn -encoding ISO-8859-1 \
          -classpath /usr/local/tomcat/lib/servlet-api.jar:/usr/local/tomcat/lib/jsp-api.jar \
          -d /usr/local/tomcat/webapps/ROOT/WEB-INF/classes \
          @/build/sources.txt \
 && find /usr/local/tomcat/webapps/ROOT/WEB-INF/classes -name '*.class' | wc -l

EXPOSE 8080
CMD ["catalina.sh", "run"]
