# Adres

Adres data

## Environment

On my windows laptop start with [setup](setup.cmd)
Setup java 21 environment

## Basic Authentication

See https://docs.spring.io/spring-security/site/apidocs/org/springframework/security/web/authentication/www/BasicAuthenticationFilter.html
In summary, this filter is responsible for processing any request that has a HTTP request header of Authorization with
an authentication scheme of Basic and a Base64-encoded username:password token. For example, to authenticate user "
Aladdin" with password "open sesame" the following header would be presented:

Authorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==

```bash

curl -v -X 'POST'   'http://localhost:8080/adresses?override=false' \
-u bvpelt:12345 \
-H 'accept: application/json' \
-H 'x-api-key: f0583805-03f6-4c7f-8e40-f83f55b7c077' \
-H 'Content-Type: application/json' \
-d '{
  "street": "Kerkewijk",
  "housenumber": "125",
  "zipcode": "3904 JB",
  "city": "Veenendaal"
}'

```

## References

- [Spring security](https://www.baeldung.com/security-spring)
- [Maven profiles](https://www.baeldung.com/maven-profiles)
- [Baeldung pageing and sorting](https://www.baeldung.com/spring-data-jpa-pagination-sorting)
- [Pageing and sorting example](https://howtodoinjava.com/spring-data/pagination-sorting-example/)
- [Adding hal links](https://tech.asimio.net/2020/04/16/Adding-HAL-pagination-links-to-RESTful-applications-using-Spring-HATEOAS.html)
- [Automatic expand and use maven properties](https://docs.spring.io/spring-boot/docs/2.1.17.RELEASE/reference/html/howto-properties-and-configuration.html)
- [Spring Security](https://docs.spring.io/spring-security/reference/servlet/authorization/authorize-http-requests.html)
- [Spring Security user authentication](https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/dao-authentication-provider.html)
- [Password encoders](https://www.baeldung.com/spring-security-5-default-password-encoder)
- [Spring security tutorial](https://www.javainuse.com/boot3/sec)



