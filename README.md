# Push Notifications Service

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/fcdce6dab8964c078d86a638f02d0294)](https://www.codacy.com/app/leetz-spy/push-notifications-service?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=btrajkovski/push-notifications-service&amp;utm_campaign=badger)

Mobile push notifications made easy. Single service for multiple OS and devices. 

## 1. Live version

[btrajkovski.noip.me](http://btrajkovski.noip.me/#/login)

## 2. Description
Backend for push notifications service that works with APNS and GCM. 
Frontend part is available here [push-notifications-frontend](https://github.com/btrajkovski/push-notifications-frontend).
Final version soon

## 3. Features
* Support for both Android and iOS devices from one place
* Multiple applications for same user
* Support for unlimited number of devices, push notifications and applications
* Scheduled notifications
* Public API for registering devices and sending notifications
* List all registered devices

## 4. How to run
### Local
* Replace `src/main/resources/social.properties` file with your personal credentials from facebook, google and twitter oauth credentials.
* Create `push-notifications` schema in MySql, add new local user with `push:push` credentials and give access to `push-notifications` schema.
* Execute this command `mvn spring-boot:run`
* Spring Boot will do the rest
