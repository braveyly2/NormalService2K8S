FROM java:8
MAINTAINER liwei <liwei@gmail.com>
VOLUME /tmp
ADD target/service-customer-0.0.1.jar normal-service-customer.jar
COPY config /config
#RUN sh -c 'touch /normal-service-provider.jar'
ENV JVM_OPTS="-Xss512k -Xmx256m -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom"
ENV JAVA_OPTS=""
ENV APP_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JVM_OPTS $JAVA_OPTS -jar /normal-service-customer.jar $APP_OPTS" ]