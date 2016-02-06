package com.project.oauth.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

@Entity
@Table(name="oauth_client_details")
public class OAuthClientDetails implements ClientDetails {
	
	private static final long serialVersionUID = 1L;
	
	@Id
    private String  client_id;
    private String  resource_ids;
    private String  client_secret;
    private String  scope;
    private String  authorized_grant_types;
    private String  web_server_redirect_uri;
    private String  authorities;
    private Integer access_token_validity;
    private Integer refresh_token_validity;
    private String  additional_information;
    private String  autoapprove;
    
    public OAuthClientDetails() {}
    
    public OAuthClientDetails(String  client_id,
    						  String  resource_ids,    
    						  String  client_secret,
    						  String  scope,
    						  String  authorized_grant_types,
    						  String  web_server_redirect_uri,
    						  String  authorities,
    						  Integer access_token_validity,
    						  Integer refresh_token_validity,
    						  String  additional_information,
    						  String  autoapprove) {
    	this.client_id = client_id;
    	this.resource_ids = resource_ids;
    	this.client_secret = client_secret;
    	this.scope = scope;
    	this.authorized_grant_types = authorized_grant_types;
    	this.web_server_redirect_uri = web_server_redirect_uri;
    	this.authorities = authorities;
    	this.access_token_validity = access_token_validity;
    	this.refresh_token_validity = refresh_token_validity;
    	this.additional_information = additional_information;
    	this.autoapprove = autoapprove;
    }

    public String _getClientId() {
        return client_id;
    }

    public void _setClientId(String client_id) {
        this.client_id = client_id;
    }
    
    public String _getResourceIds() {
        return resource_ids;
    }

    public void _setResourceIds(String resource_ids) {
        this.resource_ids = resource_ids;
    }
    
    public String _getClientSecret() {
        return client_secret;
    }

    public void _setClientSecret(String client_secret) {
        this.client_secret = client_secret;
    }
    
    public String _getScope() {
        return scope;
    }

    public void _setScope(String scope) {
        this.scope = scope;
    }
    
    public String _getAuthorizedGrantTypes() {
        return authorized_grant_types;
    }

    public void _setAuthorizedGrantTypes(String authorized_grant_types) {
        this.authorized_grant_types = authorized_grant_types;
    }
    
    public String _getWebServerRedirectUri() {
        return web_server_redirect_uri;
    }

    public void _setWebServerRedirectUri(String web_server_redirect_uri) {
        this.web_server_redirect_uri = web_server_redirect_uri;
    }
    
    public String _getAuthorities() {
        return authorities;
    }

    public void _setAuthorities(String authorities) {
        this.authorities = authorities;
    }
    
    public Integer _getAccessTokenValidity() {
        return access_token_validity;
    }

    public void _setAccessTokenValidity(int access_token_validity) {
        this.access_token_validity = access_token_validity;
    }
    
    public Integer _getRefreshTokenValidity() {
        return refresh_token_validity;
    }

    public void _setRefreshTokenValidity(int refresh_token_validity) {
        this.refresh_token_validity = refresh_token_validity;
    }
    
    public String _getAdditionalInformation() {
        return additional_information;
    }

    public void _setAdditionalInformation(String additional_information) {
        this.additional_information = additional_information;
    }
    
    public String _getAutoapprove() {
        return autoapprove;
    }

    public void _setAutoapprove(String autoapprove) {
        this.autoapprove = autoapprove;
    }

	@Override
	public Integer getAccessTokenValiditySeconds() {
		return access_token_validity;
	}

	@Override
	public Map<String, Object> getAdditionalInformation() {
		Map<String, Object> _additional_information = new HashMap<String, Object>();
		int i = 0;
		for(String info : this.additional_information.split(",")) {
			_additional_information.put(Integer.toString(i), info);
		}		
		return _additional_information;
	}	

	@Override
	public Collection<GrantedAuthority> getAuthorities() {		
		List<GrantedAuthority> _authorities = new ArrayList<GrantedAuthority>();		
		for (String role : this.authorities.split(",")) {
			_authorities.add(new SimpleGrantedAuthority(role));
		}
		return _authorities;
	}

	@Override
	public Set<String> getAuthorizedGrantTypes() {
		Set<String> _authorized_grant_types = new HashSet<String>(Arrays.asList(this.authorized_grant_types.split(",")));
		return _authorized_grant_types;
	}

	@Override
	public String getClientId() {
		return client_id;
	}

	@Override
	public String getClientSecret() {
		return client_secret;
	}

	@Override
	public Integer getRefreshTokenValiditySeconds() {
		return refresh_token_validity;
	}

	@Override
	public Set<String> getRegisteredRedirectUri() {
		Set<String> _web_server_redirect_uri = new HashSet<String>(Arrays.asList(this.web_server_redirect_uri.split(",")));
		return _web_server_redirect_uri;
	}

	@Override
	public Set<String> getResourceIds() {
		Set<String> _resource_ids = new HashSet<String>(Arrays.asList(this.resource_ids.split(",")));
		return _resource_ids;
	}

	@Override
	public Set<String> getScope() {
		Set<String> _scope = new HashSet<String>(Arrays.asList(this.scope.split(",")));
		return _scope;
	}

	@Override
	public boolean isAutoApprove(String arg) {		
		return "true".equals(this.autoapprove) ? true : false;
	}

	@Override
	public boolean isScoped() {
		if(this.scope == null || "".equals(this.scope))
			return false;
		else
			return true;
	}

	@Override
	public boolean isSecretRequired() {
		return true;
	}

}