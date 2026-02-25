package com.SmartDineAI.service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.SmartDineAI.dto.request.AuthenticationRequest;
import com.SmartDineAI.dto.request.IntrospectRequest;
import com.SmartDineAI.dto.request.RegisterRequest;
import com.SmartDineAI.dto.response.AuthenticationResponse;
import com.SmartDineAI.dto.response.IntrospectResponse;
import com.SmartDineAI.entity.Role;
import com.SmartDineAI.entity.User;
import com.SmartDineAI.exception.AppException;
import com.SmartDineAI.exception.ErrorCode;
import com.SmartDineAI.repository.RoleRepository;
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
    @Autowired
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @NonFinal
    @Value("${jwt.signerKey}")
    private String SIGNER_KEY;

    public AuthenticationResponse login(AuthenticationRequest request){
        User user = userRepository.findByUsername(request.getUsername())
        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        var isAuthenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if(!isAuthenticated){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        String token = generateToken(user);

        AuthenticationResponse response = new AuthenticationResponse(token, isAuthenticated);
        return response;
    }

    private String generateToken(User user){
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                                                    .subject(user.getUsername())
                                                    .issuer("SmartDineAI")
                                                    .issueTime(new Date())
                                                    .claim("scope", user.getRoleId().getName())
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

    public String register(RegisterRequest request){
        User user = new User();

        if(userRepository.existsByUsername(request.getUsername())){
            throw new AppException(ErrorCode.USER_ALREADY_EXISTS);
        }

        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setFullName(request.getFullName());
        Role role = roleRepository.findById(2L).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
        user.setRoleId(role);

        userRepository.save(user);
        return "Register Successful!!!";
    }


}
