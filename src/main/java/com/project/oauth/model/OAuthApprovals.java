package com.project.oauth.model;

import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="oauth_approvals")
public class OAuthApprovals {

    @Id
    private String user_id;    
    private String client_id;    
    private String scope;    
    private String status;    
    private Date expires_at;    
    private Date last_modified;
    
    public String _getUserId() {
        return user_id;
    }

    public void _setUserId(String user_id) {
        this.user_id = user_id;
    }
    
    public String _getClientId() {
        return client_id;
    }

    public void _setClientId(String client_id) {
        this.client_id = client_id;
    }
    
    public String _getScope() {
        return scope;
    }

    public void _setScope(String scope) {
        this.scope = scope;
    }
    
    public String _getStatus() {
        return status;
    }

    public void _setStatus(String status) {
        this.status = status;
    }
    
    public Date _getExpiresAt() {
        return expires_at;
    }

    public void _setExpiresAt(Date expires_at) {
        this.expires_at = expires_at;
    }
        
    public Date _getLastModified() {
        return last_modified;
    }

    public void _setLastModified(Date last_modified) {
        this.last_modified = last_modified;
    }
    
}