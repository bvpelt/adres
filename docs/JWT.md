# JWT Tokens

**JWT** Json Web Token

See 
- https://www.youtube.com/watch?v=KxqlJblhzfI
- https://www.javainuse.com/boot3/sec/7
- https://jwt.io/ Tool to visualize the token
- https://www.youtube.com/watch?v=Y2H3DXDeS3Q concept of JWT

## Overview

The proces
![image](images/jwt-token-processing.png) 

The JWT token consists of:
- header 
  - type of token
  - algorithm for signing
- payload
  - claims with number of fields
    - iss issuer field, who issued the token
    - sub subject for instance the userid
    - exp expiry the unix timestamp when the token expires
    - iat issued att the unix timestamp of the moment the token was issued
    - additional claims for instance
      - orgId
      - email
      - role
- signature used to verify the header and the payload using a secret. f(secret, header, payload) = token

Anyone can view and alter json web token for instance by using https://jwt.io. The check on the signature makes sure the token is not changed.
That is why on should never store sensative information in json web tokens.

JWT tokens are stateless, the token can contain usesfull information.

Problems:
- If one steals a JWT token, one can impersonate the original user.
- The content of a token might change for instance the roles, email. If the token has a long lifetime that does not change. Solution: use a short lifecycle (2-5 minutes). If the token expires, the user needs to refresh the token.

## Generate a signing key
Use one of the [tools](#Tools) described below to generate a signing key.
For JWT tokens the minimum keylength is 256 bits (32 bytes).


## IntelliJ testsetup with profiles

See
![image](./images/Intellij-testconfig.png)

## Maven test run

```bash
(export SPRING_PROFILES_ACTIVE=develop && mvn clean test)
```

## Required depedencies

```xml
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-api</artifactId>
        <version>0.12.3</version>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-impl</artifactId>
        <version>0.12.3</version>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-jackson</artifactId>
        <version>0.12.3</version>
    </dependency>
```

## Tools

- Key generators: 
  - https://allkeysgenerator.com/random (does not exist)
  - https://jwtsecret.com/generate 
- OAuth tools: https://oauth.tools/


1:25
