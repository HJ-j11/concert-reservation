package com.demo.concert.component.login;

import com.demo.concert.entity.login.UserRole;
import com.demo.concert.service.login.CustomUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;

import io.jsonwebtoken.security.Keys;

import java.security.Key;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

  private final Key key;
  private final Key refreshKey;
  private final long expiration;

  private final long accessTokenValidity = 1000 * 60 * 15; // 15분
  private final long refreshTokenValidity = 1000L * 60 * 60 * 24 * 7; // 7일


  public JwtProvider(
      @Value("${jwt.access}") String access,
      @Value("${jwt.refresh}") String refresh,
      @Value("${jwt.expiration}") long expiration
  ) {
    this.key = Keys.hmacShaKeyFor(access.getBytes()); // 최소 32바이트 필요 (HS256)
    // HS512 requires at least 64 bytes (512 bits) - ensure refresh key meets this requirement
    byte[] refreshBytes = refresh.getBytes();
    if (refreshBytes.length < 64) {
      throw new IllegalArgumentException(
          "JWT refresh key must be at least 64 bytes (512 bits) for HS512 algorithm. " +
          "Current key length: " + refreshBytes.length + " bytes"
      );
    }
    this.refreshKey = Keys.hmacShaKeyFor(refreshBytes);
    this.expiration = expiration;
  }

  /**
   * JWT 토큰 생성
   *
   * @param username 사용자 ID 또는 username
   * @param userRole
   * @return JWT 문자열
   */
  public String createToken(String username, UserRole userRole) {
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + expiration);

    return Jwts.builder()
        .setSubject(username)        // payload "sub"
        .setIssuedAt(now)            // 생성 시간
        .setExpiration(expiryDate)   // 만료 시간
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();
  }

  /**
   * JWT Refresh 토큰 생성
   *
   * @param username 사용자 ID 또는 username
   * @return JWT 문자열
   */
  public String createRefreshToken(String username) {
    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + refreshTokenValidity))
        .signWith(refreshKey, SignatureAlgorithm.HS512)
        .compact();
  }

  /**
   * JWT 토큰에서 username 추출
   */
  public String getUsername(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }


  public Authentication getAuthentication(String token, CustomUserDetailsService userDetailsService) {
    String username = getUsername(token); // 토큰에서 username 추출
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);      // UserDetailsService를 통해 UserDetails 조회

    // Authentication 객체 생성
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  /**
   * JWT 유효성 검증
   */
  public boolean validateToken(String token) {
    try {
      Jws<Claims> claims = Jwts.parserBuilder()
          .setSigningKey(key)
          .build()
          .parseClaimsJws(token);

      return !claims.getBody().getExpiration().before(new Date());
    } catch (JwtException | IllegalArgumentException e) {
      return false;
    }
  }

  /**
   * JWT 만료 시간 확인
   */
  public Date getExpirationDate(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getExpiration();
  }
}
