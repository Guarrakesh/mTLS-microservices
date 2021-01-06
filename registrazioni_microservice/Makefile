include spring.env
include db.env

.PHONY: spring-build run

default: run

dir=$(shell pwd)
ecr-login=$(shell aws ecr get-login --no-include-email)

refresh: spring-build run

spring-build:
	@echo "Building application for $(PROJECT_NAME)"
	cd spring && ./mvnw clean
	cd spring && ./mvnw package
	cd spring && mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)


run:
	@echo "Running application..."
	cd spring && java -cp target/dependency/BOOT-INF/lib/* prenotazioni.Application
