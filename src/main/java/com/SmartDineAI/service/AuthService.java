package com.SmartDineAI.service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.SmartDineAI.dto.request.AuthenticationRequest;
import com.SmartDineAI.dto.request.IntrospectRequest;
import com.SmartDineAI.dto.response.AuthenticationResponse;
import com.SmartDineAI.dto.response.IntrospectResponse;
import com.SmartDineAI.entity.User;
import com.SmartDineAI.exception.AppException;
import com.SmartDineAI.exception.ErrorCode;
import com.SmartDineAI.repository.UserRepository;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import lombok.experimental.NonFinal;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @NonFinal
    protected static final String SIGNER_KEY = "SmartDineAI_SuperSecretKey_ForJWTSigning_2026_ThisKeyMustBeLongEnough_123456";

    public AuthenticationResponse login(AuthenticationRequest request){
        User user = userRepository.findByUsername(request.getUsername())
        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        var isAuthenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if(!isAuthenticated){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        String token = generateToken(request.getUsername());

        AuthenticationResponse response = new AuthenticationResponse(token, isAuthenticated);
        return response;
    }

    private String generateToken(String username){
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                                                    .subject(username)
                                                    .issuer("SmartDineAI")
                                                    .issueTime(new Date())
                                                    .expirationTime(new Date(
                                                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                                                    ))
                                                    .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try{
            jwsObject.sign(new MACSigner(SIGNER_KEY));
            return jwsObject.serialize();
        } catch (JOSEException ex){
            throw new AppException(ErrorCode.TOKEN_GENERATION_FAILED);
        }
    }

    public IntrospectResponse introspect(IntrospectRequest request){
        String token = request.getToken();

        try{
            JWSVerifier verifier = new MACVerifier(SIGNER_KEY);

            SignedJWT signedJWT = SignedJWT.parse(token);

            Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();

            var isValid = signedJWT.verify(verifier);

            IntrospectResponse response = new IntrospectResponse();
            response.setValid(isValid && expirationTime.after(new Date()));
            return response;
        } catch (JOSEException | ParseException ex){
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }
    }


}
