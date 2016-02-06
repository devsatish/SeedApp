package com.project.oauth.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="oauth_refresh_token")
public class OAuthRefreshToken {

    @Id
    private String token_id;    
    private Byte[] token;    
    private Byte[] authentication;
    
    public void _setTokenId(String token_id) {
        this.token_id = token_id;
    }
    
    public Byte[] _getToken() {
        return token;
    }

    public void _setToken(Byte[] token) {
        this.token = token;
    }
    
    public Byte[] _getAuthentication() {
        return authentication;
    }

    public void _setAuthentication(Byte[] authentication) {
        this.authentication = authentication;
    }
            
}