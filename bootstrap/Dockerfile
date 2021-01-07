FROM phpearth/php:7.2-nginx

COPY . /usr/src/app
WORKDIR /usr/src/app

RUN apk add --no-cache composer
RUN composer install

CMD [ "php", "./index.php" ]