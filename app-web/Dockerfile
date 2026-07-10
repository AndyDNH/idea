FROM node:20-alpine as build

WORKDIR /app

COPY package.json ./
RUN npm install

COPY src .
RUN npm run build

FROM nginx:alpine

COPY /dist /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]