
FROM nginx:alpine

MAINTAINER Bart van Pelt <brtvnplt@gmail.com>

RUN mkdir -p /usr/share/nginx/html # Create the directory

RUN ls -altr /usr/share/nginx/html

COPY dist/adresbook/browser/  /usr/share/nginx/html

RUN ls -altr /usr/share/nginx/html

COPY nginx.conf /etc/nginx/nginx.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
