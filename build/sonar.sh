#!/bin/bash
mvn -DdryRun=true -Dsonar.organization=jooby -Dsonar.host.url=https://sonarcloud.io -Dsonar.jacoco.reportPaths=modules/coverage-report/target/jacoco.exec -Dsonar.login=$SONAR_TOKEN -Dsonar.branch=master clean install coveralls:report sonar:sonar -P coverage
