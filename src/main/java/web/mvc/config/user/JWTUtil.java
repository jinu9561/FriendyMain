package web.mvc.config.user;

import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import web.mvc.entity.user.Users;
import web.mvc.repository.user.UserRepository;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@Slf4j
public class JWTUtil {

    private SecretKey secretKey;
    @Autowired
    private UserRepository userRepository;

    // 여기서 ${}안에는 propertie의 키값이고 해당하는 value를 가져온다
    public JWTUtil(@Value("${spring.jwt.secret}")String secret) {
        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    public String getUsername(String token) {
        log.info("getUsername(String token)  call");
        String re = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("userName", String.class);
        log.info("getUsername(String token)  re = {}" ,re);
        return re;
    }

    public String getId(String token) {
        log.info("getId(String token)  call");
        String re = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("userId", String.class);
        log.info("getIds(String token)  re = {}" ,re);
        return re;
    }
    public Long getUserSeq(String token) {
        log.info("getUserSeq(String token)  call");
        Long re = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("userSeq", Long.class);
        log.info("getUserSeq(String token)  re = {}" ,re);
        return re;
    }

    public String getRole(String token) {
        log.info("getRole(String token)  call");
        String re = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
        log.info("getRole(String token)  re = {} " , re);
        return re;
    }

    public Boolean isExpired(String token) {
        log.info("isExpired(String token)  call");
        boolean re = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
        log.info("isExpired(String token)  re  = {}",re);
        return re;
    }

    //public String createJwt(String username, String role, Long expiredMs) {
    public String createJwt(Users users, String role, Long expiredMs) {
        log.info("createJwt  call");
        return Jwts.builder()
                .claim("userName", users.getUserName()) //이름  claim -> payload 데이터 설정
                .claim("userSeq", users.getUserSeq()) //pk
                .claim("userId", users.getUserId()) //아이디
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))  // 발급 시간 설정
                .expiration(new Date(System.currentTimeMillis() + expiredMs))  // 만료 시간 설정
                .signWith(secretKey) // signature에 사용할 내용, 암호화 알고리즘의 바탕이 되는 준비물
                .compact();
    }


}