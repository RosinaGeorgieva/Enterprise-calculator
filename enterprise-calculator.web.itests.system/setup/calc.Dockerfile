FROM java:8-jdk
ADD ./enterprise-calculator.web-release.jar enterprise-calculator.web-release.jar
WORKDIR .
CMD ["java", "-jar", "enterprise-calculator.web-release.jar"]
ENV VCAP_SERVICES '{"xsuaa" : [{ "tags":["xsuaa"], "credentials":{"clientid":"client", "clientsecret":"secret", "url":"url", "verificationkey":"key", "identityzone":"zid", "xsappname":"appname" }}]}'
EXPOSE 8080

