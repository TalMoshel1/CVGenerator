
FROM fedora:latest

# Update package lists and install necessary packages
RUN dnf update -y && dnf install -y wget java-devel

# Copy the Chrome RPM package
COPY google-chrome-stable_current_x86_64.rpm /tmp/chrome.rpm

# Install Chrome using yum
RUN yum install -y /tmp/chrome.rpm

# Clean up
RUN rm /tmp/chrome.rpm

# WORKDIR /app && COPY target/basic-0.0.1-SNAPSHOT.jar .

WORKDIR /app
COPY target/basic-0.0.1-SNAPSHOT.jar .

EXPOSE 8080

CMD ["java", "-jar", "basic-0.0.1-SNAPSHOT.jar"]