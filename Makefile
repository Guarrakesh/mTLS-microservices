include .env

.PHONY: up down stop start spring-build

default: up

dir=$(shell pwd)
ecr-login=$(shell aws ecr get-login --no-include-email)

reload: down up-build


up-build:
	@echo "Building containers for $(PROJECT_NAME)..."
	docker-compose -f docker-compose.yml -f docker-compose.override.$(COMPOSE_OVERRIDE).yml up -d --build --remove-orphans
	@echo "Starting up Lemur containers..."
	cd lemur && export PROJECT_NAME=$(PROJECT_NAME) &&  docker-compose up -d --remove-orphans --build
	cd bootstrap && export PROJECT_NAME=$(PROJECT_NAME) && yarn docker:prod
up:
	@echo "Starting up containers for $(PROJECT_NAME)..."
	docker-compose -f docker-compose.yml -f docker-compose.override.$(COMPOSE_OVERRIDE).yml up -d --remove-orphans
	@echo "Starting up Lemur containers..."
	cd lemur && export PROJECT_NAME=$(PROJECT_NAME) && docker-compose up -d --remove-orphans
	cd bootstrap && export PROJECT_NAME=$(PROJECT_NAME) && yarn docker:prod
down: stop

stop:
	@echo "Stopping containers for $(PROJECT_NAME)..."
	@docker-compose -f docker-compose.yml -f docker-compose.override.$(COMPOSE_OVERRIDE).yml stop
	@echo "Stopping containers for Lemur..."
	cd lemur && export PROJECT_NAME=$(PROJECT_NAME) && docker-compose stop
	cd bootstrap && export PROJECT_NAME=$(PROJECT_NAME) && yarn stop

prune:
	@echo "Removing containers for $(PROJECT_NAME)..."
	@docker-compose -f docker-compose.yml -f docker-compose.override.$(COMPOSE_OVERRIDE).yml down -v
	cd bootstrap && export PROJECT_NAME=$(PROJECT_NAME) && docker-compose -f docker-compose.yml down -v

ps:
	@docker ps --filter name='$(PROJECT_NAME)'
