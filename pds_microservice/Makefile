include spring.env
include db.env

.PHONY: run spring-build

default: run

export DEPENDENCY := target/dependency
dir=$(shell pwd)
ecr-login=$(shell aws ecr get-login --no-include-email)

refresh: spring-build run

spring-build:
	@echo "Building application for $(PROJECT_NAME)"
	cd spring && ./mvnw clean
	cd spring && ./mvnw package
	cd spring && mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)



run:
	@echo "Running application in $(DEPENDENCY)..."
	export $(cat db.env | xargs) && export $(cat spring.env | xargs) && cd spring && ./mvnw spring-boot:run