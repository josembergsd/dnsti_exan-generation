package br.com.devdojo.examgeneration.bean.login;

import br.com.devdojo.examgeneration.custom.CustomURLEncoder;
import br.com.devdojo.examgeneration.persistence.dao.LoginDao;
import br.com.devdojo.examgeneration.persistence.model.Token;

import javax.faces.context.ExternalContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class LoginBean implements Serializable {

    private String username;
    private String password;
    private final LoginDao logindao;
    private final ExternalContext externalContext;

    @Inject
    public LoginBean(LoginDao logindao, ExternalContext externalContext) {
        this.logindao = logindao;
        this.externalContext = externalContext;
    }

    public String login() {
        Token token = logindao.loginReturnToken(username,password);
        //System.out.println("Token retornado: " + token.getToken());
        if(token == null) return null;
        addTokenAndExpirationTimeToCookies(token.getToken(), token.getExpirationTime().toString());
        return "index.xhtml?faces-redirect=true";
    }
    public String logout(){
        removeExpirationTimeFromCookies();
        return "login.xhtml?faces-redirect=true";
    }
    //Adiciona o token aos cookies
    private void addTokenAndExpirationTimeToCookies(String token, String expirationTime){
        externalContext.addResponseCookie("token", CustomURLEncoder.encoderUTF8(token) , null);
        externalContext.addResponseCookie("expirationTime", expirationTime, null);
    }
    //Remover o token dos cookies
    private void removeExpirationTimeFromCookies(){
        addTokenAndExpirationTimeToCookies(null, null);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
