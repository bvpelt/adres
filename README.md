# Adres

Adres data

## Building the application
### Building server side
You can build the server side using a h2 or a postgresql database. 

#### Building for h2
From the project directory
```bash
mvn -P develop -DactiveProfile=develop clean compile
```
#### Building for postgresql
From the project directory
```bash
mvn -P runtime -DactiveProfile=runtime clean compile
```

### Building client side
From the project directory
```bash
cd adresbook # The directory with client side implementation
npm install
npm run genapi
num run start
```
## Spring boot
- Documentation https://docs.spring.io/spring-boot/index.html
- Springdoc https://springdoc.org/

## Intellij
Change settings to use performance indicators without being root
```bash
sudo sh -c 'echo 1 > /proc/sys/kernel/perf_event_paranoid'
sudo sh -c 'echo 0 > /proc/sys/kernel/kptr_restrict'
```

## Git

### Remove banches

```bash
# local remove
git branch -d <branch-name>

# remote remove
git push origin --delete <branch-name>
```
### Releases

Workflow
- create a branch from the develop branch ```git checkout -b <workbranch>```
- make changes and test
- update branch ```git add .; git commit -m <message>; git push```
- merge branch to develop ```git checkout develop; git merge <workbranch>; git push```
- test and resolve merge conflicts
- merge develop branch to main ```git checkout main; git merge develop; git push```
- add tags to main branch ```git tag -m <tag message> <releasenumber>; git push origin --tags```
- checkout develop to keepon working ```git checkout develop```

## Environment

On my windows laptop start with [setup](setup.cmd)
Setup java 21 environment

### Run test with specific profile

```bash
mvn clean test -P runtime -Dspring.profiles.active=runtime
mvn clean test -P develop -Dspring.profiles.active=develop
```
# Security

| User    | Role          | Privilege       | userid | roleid | privilegeid |
|---------|---------------|-----------------|--------|--------|-------------|
| admin   | ADMIN         | ALL             |   1    |    1   |   1         |
| bvpelt  | FUNC_OPERATOR | APP_WRITE       |   3    |    2   |   3         |
| bvpelt  | TECH_OPERATOR | APP_MAINTENANCE |   3    |    3   |   2         |
| fber    | FUNC_OPERATOR | APP_WRITE       |   4    |    2   |   3         |
| user    | USER          | APP_READ        |   2    |    4   |   4         |
| develop | DEVELOPER     | ALL             |   5    |    6   |   1         |
| any     | JWT-TOKEN     | APP_READ_JWT    |        |    5   |   5         |

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

# 
# Usefull info

## Roles
Determine which roles are granted to a specific user
```sql
-- simple query
SELECT username, rolename 
FROM users, role, users_roles 
WHERE users.id = users_roles.userid AND users_roles.roleid = role.id;

-- alternative with left join
SELECT
    u.username,
    r.rolename
FROM
    users u
        LEFT JOIN
    users_roles ur ON u.id = ur.userid
        LEFT JOIN
    role r ON ur.roleid = r.id;

-- All users, with roles and privileges
SELECT
    u.username,
    r.rolename,
    p.name
FROM
    users u
        LEFT JOIN
    users_roles ur ON u.id = ur.userid
        LEFT JOIN
    role r ON ur.roleid = r.id
        LEFT JOIN
    roles_privileges rp ON r.id = rp.roleid
        LEFT JOIN
    privilege p ON rp.privilegeid=p.id;

SELECT u.username, r.rolename, p.name
FROM users u, users_roles ur, role r, roles_privileges rp, privilege p
WHERE u.id =ur.userid AND ur.roleid = r.id AND r.id = rp.roleid AND rp.privilegeid = p.id;

```

## Metrics
To enable metrics for prometheus

add to pom.xml
```xml
<dependencies>
    <dependency>
        <groupId>io.micrometer</groupId>
        <artifactId>micrometer-core</artifactId>
        <version>1.13.2</version>
    </dependency>
    <dependency>
        <groupId>io.micrometer</groupId>
        <artifactId>micrometer-registry-prometheus</artifactId>
        <version>1.12.3</version>
    </dependency>
</dependencies>
```

Enable prometheus in application.yaml
```yaml
management:
  endpoint:
    env:
      show-values: always
    configprops:
      show-values: always
    health:
      show-details: always
    metrics:
      enabled: true
    prometheus:
      enabled: true
  endpoints:
    web:
      path-mapping:
        info: app-info
        health: app-health
      exposure:
        include: 'prometheus'
        exclude:
```

Metrics data are available at http://localhost:8081/actuator/prometheus


[Prometheus config file](./docs/prometheus.yaml)

```bash
 ~/Apps/prometheus-3.1.0.linux-amd64/prometheus --config.file=docs/prometheus.yaml 
```
The result is visable at http://localhost:9090/query

Install grafana
```bash
bvpelt@pluto:~$ sudo apt-get update
Hit:1 http://nl.archive.ubuntu.com/ubuntu noble InRelease
Hit:2 http://nl.archive.ubuntu.com/ubuntu noble-updates InRelease                                                                                                                                            
Hit:3 http://nl.archive.ubuntu.com/ubuntu noble-backports InRelease                                                                                                                                          
Get:4 https://apt.grafana.com stable InRelease [7,661 B]                                                                                                                                                     
Hit:5 https://dl.google.com/linux/chrome/deb stable InRelease                                                                                                                
Get:6 https://esm.ubuntu.com/apps/ubuntu noble-apps-security InRelease [7,547 B]                                                           
Get:7 https://esm.ubuntu.com/apps/ubuntu noble-apps-updates InRelease [7,468 B]               
Get:8 https://esm.ubuntu.com/infra/ubuntu noble-infra-security InRelease [7,462 B]            
Hit:9 http://security.ubuntu.com/ubuntu noble-security InRelease                              
Get:10 https://esm.ubuntu.com/infra/ubuntu noble-infra-updates InRelease [7,461 B]
Get:11 https://apt.grafana.com stable/main amd64 Packages [338 kB]       
Hit:12 https://ftp.postgresql.org/pub/pgadmin/pgadmin4/apt/noble pgadmin4 InRelease
Fetched 375 kB in 1s (552 kB/s)      
Reading package lists... Done
bvpelt@pluto:~$ sudo apt-get install grafana
Reading package lists... Done
Building dependency tree... Done
Reading state information... Done
The following NEW packages will be installed:
  grafana
0 upgraded, 1 newly installed, 0 to remove and 3 not upgraded.
Need to get 127 MB of archives.
After this operation, 470 MB of additional disk space will be used.
Get:1 https://apt.grafana.com stable/main amd64 grafana amd64 11.4.0 [127 MB]
Fetched 127 MB in 13s (9,658 kB/s)                                                                                                                                                                           
Selecting previously unselected package grafana.
(Reading database ... 239591 files and directories currently installed.)
Preparing to unpack .../grafana_11.4.0_amd64.deb ...
Unpacking grafana (11.4.0) ...
Setting up grafana (11.4.0) ...
info: Selecting UID from range 100 to 999 ...

info: Adding system user `grafana' (UID 123) ...
info: Adding new user `grafana' (UID 123) with group `grafana' ...
info: Not creating home directory `/usr/share/grafana'.
### NOT starting on installation, please execute the following statements to configure grafana to start automatically using systemd
 sudo /bin/systemctl daemon-reload
 sudo /bin/systemctl enable grafana-server
### You can start grafana-server by executing
 sudo /bin/systemctl start grafana-server
```
The result is visable at http://localhost:3000

Graphana dashboard

Start dashboard ```sudo /bin/systemctl start grafana-server```

Stop dashboard ```sudo /bin/systemctl stop grafana-server```

[Grafana configuration file](./docs/grafana-springboot.json)
[Grafana example queries](https://signoz.io/guides/a-regex-in-query-in-grafana/)

- [docker image](https://hub.docker.com/r/prom/prometheus)
- [prometheus docs](https://prometheus.io/docs/introduction/overview/)
- [prometheus config](https://github.com/prometheus/prometheus/blob/main/documentation/examples/prometheus.yml)

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
- [JWT Documentation](https://github.com/jwtk/jjwt )
- [Metrics](https://piotrminkowski.wordpress.com/2018/05/11/exporting-metrics-to-influxdb-and-prometheus-using-spring-boot-actuator/)



