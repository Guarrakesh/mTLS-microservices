include .env

.PHONY: up down stop start spring-build

default: up

dir=$(shell pwd)
ecr-login=$(shell aws ecr get-login --no-include-email)

refresh: down up


up-build:
	@echo "Building containers for $(PROJECT_NAME)..."
	docker-compose -f docker-compose.yml -f docker-compose.override.$(COMPOSE_OVERRIDE).yml up -d --build --remove-orphans

up:
	@echo "Starting up containers for $(PROJECT_NAME)..."
	docker-compose -f docker-compose.yml -f docker-compose.override.$(COMPOSE_OVERRIDE).yml up -d --remove-orphans

down: stop

stop:
	@echo "Stopping containers for $(PROJECT_NAME)..."
	@docker-compose -f docker-compose.yml -f docker-compose.override.$(COMPOSE_OVERRIDE).yml stop


prune:
	@echo "Removing containers for $(PROJECT_NAME)..."
	@docker-compose -f docker-compose.yml -f docker-compose.override.$(COMPOSE_OVERRIDE).yml down -v

ps:
	@docker ps --filter name='$(PROJECT_NAME)'
