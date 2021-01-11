FROM node:12-alpine

ARG NODE_PORT

EXPOSE $NODE_PORT

ARG NODE_ENV
ENV NODE_ENV $NODE_ENV

RUN mkdir -p /usr/src/app/
WORKDIR /usr/src/app/

ADD package.json yarn.lock /usr/src/app/
RUN yarn --pure-lockfile
ADD . /usr/src/app

CMD ["yarn", "start"]
