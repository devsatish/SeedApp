package com.project.oauth.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="oauth_code")
public class OAuthCode {

	@Id
    private String code;    
    private Byte[] authentication;
    
    public String _getCode() {
        return code;
    }

    public void _setCode(String code) {
        this.code = code;
    }

    public Byte[] _getAuthentication() {
        return authentication;
    }

    public void _setAuthentication(Byte[] authentication) {
        this.authentication = authentication;
    }
            
}