FROM java:8-alpine
MAINTAINER Your Name <you@example.com>

ADD target/uberjar/luminus-vending-machine.jar /luminus-vending-machine/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/luminus-vending-machine/app.jar"]
