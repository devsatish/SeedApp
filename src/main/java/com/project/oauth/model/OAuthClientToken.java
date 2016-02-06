package com.project.oauth.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="oauth_client_token")
public class OAuthClientToken {

    @Id
    private String token_id;
    private Byte[] token;
    private String authentication_id;
    private String user_name;
    private String client_id;
    
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
        
}