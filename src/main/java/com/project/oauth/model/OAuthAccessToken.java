package com.project.oauth.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="oauth_access_token")
public class OAuthAccessToken {

    @Id
    private String token_id;    
    private Byte[] token;    
    private String authentication_id;    
    private String user_name;    
    private String client_id;    
    private Byte[] authentication;    
    private String refresh_token;
    
    public String _getTokenId() {
        return token_id;
    }

    public void _setTokenId(String token_id) {
        this.token_id = token_id;
    }
    
    public Byte[] _getToken() {
        return token;
    }

    public void _setToken(Byte[] token) {
        this.token = token;
    }
    
    public String _getAuthenticationId() {
        return authentication_id;
    }

    public void _setAuthenticationId(String authentication_id) {
        this.authentication_id = authentication_id;
    }
    
    public String _getUserName() {
        return user_name;
    }

    public void _setUserName(String user_name) {
        this.user_name = user_name;
    }
    
    public String _getClientId() {
        return client_id;
    }

    public void _setClientId(String client_id) {
        this.client_id = client_id;
    }
    
    public Byte[] _getAuthentication() {
        return authentication;
    }

    public void _setAuthentication(Byte[] authentication) {
        this.authentication = authentication;
    }
    
    public String _getRefreshToken() {
        return refresh_token;
    }

    public void _setRefreshToken(String refresh_token) {
        this.refresh_token = refresh_token;
    }
    
}