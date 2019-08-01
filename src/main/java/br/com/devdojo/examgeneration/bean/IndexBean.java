package br.com.devdojo.examgeneration.bean;

import br.com.devdojo.examgeneration.persistence.dao.LoginDao;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class IndexBean implements Serializable {

    private final LoginDao loginDao;

    @Inject
    public IndexBean(LoginDao loginDao) {
        this.loginDao = loginDao;
    }

   /* public void login(){
       Token token = loginDao.loginReturnToken("josemberg","f@p3p12o1B");
        System.out.println(token);
    }*/

}
