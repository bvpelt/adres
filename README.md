# Adres

Adres data

## Spring boot
- Documentation https://docs.spring.io/spring-boot/index.html
- Springdoc https://springdoc.org/

## Intellij
Change settings to use performance indicators without being root
```bash
sudo sh -c 'echo 1 > /proc/sys/kernel/perf_event_paranoid'
sudo sh -c 'echo 0 > /proc/sys/kernel/kptr_restrict'
```
## Environment

On my windows laptop start with [setup](setup.cmd)
Setup java 21 environment

### Run test with specific profile

```bash
mvn clean test -P runtime -Dspring.profiles.active=runtime
mvn clean test -P develop -Dspring.profiles.active=develop
```
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
## Converting google contacts
- export google contacts to a csv file


## Angular
Releases https://angular.dev/reference/releases 

## H2 console
Setup h2 console with spring security see https://springframework.guru/using-the-h2-database-console-in-spring-boot-with-spring-security/


Fields:
First Name,Middle Name,Last Name,Phonetic First Name,Phonetic Middle Name,Phonetic Last Name,Name Prefix,Name Suffix,Nickname,File As,Organization Name,Organization Title,Organization Department,Birthday,Notes,Photo,Labels,E-mail 1 - Label,E-mail 1 - Value,E-mail 2 - Label,E-mail 2 - Value,E-mail 3 - Label,E-mail 3 - Value,E-mail 4 - Label,E-mail 4 - Value,Phone 1 - Label,Phone 1 - Value,Phone 2 - Label,Phone 2 - Value,Phone 3 - Label,Phone 3 - Value,Address 1 - Label,Address 1 - Formatted,Address 1 - Street,Address 1 - City,Address 1 - PO Box,Address 1 - Region,Address 1 - Postal Code,Address 1 - Country,Address 1 - Extended Address,Website 1 - Label,Website 1 - Value,Event 1 - Label,Event 1 - Value,Event 2 - Label,Event 2 - Value,Custom Field 1 - Label,Custom Field 1 - Value
a1         a2          a3        a4                  a5                   a6                 a7          a8           a9      a10     a11               a12                a13                     a14      a15   a16   a17    a18              a19              a20              a21              a22              a23              a24              a25              a26             a27             a28             a29             a30             a31             a32               a33                   a34                a35              a36                a37                a38                     a39                 a40


```bash
cat ~/Downloads/contacts.csv | awk '{split($0, a, ","); printf("%s\t--%s\t--%s\t--%s\t--%s\t--%s\t--%s\t--%s\t--%s\t--%s====\n", a[1], a[3], a[33], a[34], a[35], a[36], a[37], a[38], a[39], a[40])}'
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
- [JWT Token](https://www.youtube.com/watch?v=KxqlJblhzfI)



