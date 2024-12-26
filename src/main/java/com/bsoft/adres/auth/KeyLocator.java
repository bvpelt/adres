package com.bsoft.adres.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.LocatorAdapter;
import io.jsonwebtoken.ProtectedHeader;
import io.jsonwebtoken.io.Decoders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

@Service
public class KeyLocator extends LocatorAdapter<Key> {

    @Value("${application.key}")
    private String SECRET_KEY;

    @Override
    public Key locate(ProtectedHeader header) { // a JwsHeader or JweHeader

        Key key = null;

        key = getSigninKey();

        return key;
    }

    private Key getSigninKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return new SecretKeySpec(keyBytes, "HmacSHA256");
    }
}
