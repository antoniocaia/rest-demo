FROM node:14.16.1

EXPOSE 3000

RUN mkdir webapp

COPY src/main/webapp webapp

WORKDIR webapp

RUN npm install

ENTRYPOINT exec npm run dev

