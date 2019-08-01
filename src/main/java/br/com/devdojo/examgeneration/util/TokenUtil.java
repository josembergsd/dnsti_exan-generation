package br.gov.pi.appfapepi.v1.util;
/*
* Classe para pegar o token dos cookies
* */

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.util.Arrays.asList;

public class TokenUtil {

    public String getTokenFromCookies(HttpServletRequest request){
        if(request.getCookies() == null) return "";
        List<Cookie> cookieList = asList(request.getCookies());
        return getCookieByKey(cookieList, "token");
    }
    public boolean isExpirationTimeFromCookieValid(HttpServletRequest request){
        if(request.getCookies() == null) return false;
        List<Cookie> cookieList = asList(request.getCookies());
        String expirationTime = getCookieByKey(cookieList, "expirationTime");
        return validIfTimeNowIsBeforeTokenExpires(expirationTime);
    }

    private String getCookieByKey(List<Cookie> cookieList, String key) {
        return cookieList.stream()
                .filter(cookie -> cookie.getName().equals(key))
                .map(Cookie::getValue)
                .findFirst()
                .orElse("");
    }

    private boolean validIfTimeNowIsBeforeTokenExpires(String expirationTime){
        if(expirationTime.isEmpty()) return false;
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS").withZone(ZoneId.of("UTC"));
        //LocalDateTime tokenExpirationTime = LocalDateTime.parse(expirationTime, formatter);
        LocalDateTime tokenExpirationTime = LocalDateTime.parse(expirationTime);
        return LocalDateTime.now().isBefore(tokenExpirationTime);
    }

}
