
FROM nginx:alpine

LABEL maintainer="Bart van Pelt <brtvnplt@gmail.com>"cd

# Install bash and curl
RUN apk update && apk add bash curl

# Create the directory
RUN mkdir -p /usr/share/nginx/html

COPY dist/adresbook/browser/  /usr/share/nginx/html

# Replace placeholder with environment variable
RUN envsubst '\$API_BASE_URL' < /usr/share/nginx/html/index.html > /usr/share/nginx/html/temp.html && \
    mv /usr/share/nginx/html/temp.html /usr/share/nginx/html/index.html

COPY nginx.conf /etc/nginx/nginx.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
