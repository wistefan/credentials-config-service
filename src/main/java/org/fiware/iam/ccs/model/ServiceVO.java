package org.fiware.iam.ccs.model;

@jakarta.annotation.Generated("org.openapitools.codegen.languages.MicronautCodegen")
@io.micronaut.core.annotation.Introspected
public class ServiceVO {

	public static final String JSON_PROPERTY_ID = "id";
	public static final String JSON_PROPERTY_DEFAULT_OIDC_SCOPE = "defaultOidcScope";
	public static final String JSON_PROPERTY_OIDC_SCOPES = "oidcScopes";

	/** Id of the service to be configured. If no id is provided, the service will generate one. */
	@com.fasterxml.jackson.annotation.JsonProperty(JSON_PROPERTY_ID)
	@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
	private String id;

	/** Default OIDC scope to be used if none is specified */
	@com.fasterxml.jackson.annotation.JsonProperty(JSON_PROPERTY_DEFAULT_OIDC_SCOPE)
	@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.ALWAYS)
	private String defaultOidcScope;

	@com.fasterxml.jackson.annotation.JsonProperty(JSON_PROPERTY_OIDC_SCOPES)
	@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.ALWAYS)
	private ServiceScopesVO oidcScopes = new ServiceScopesVO();

	// methods

	@Override
	public boolean equals(Object object) {
		if (object == this) {
			return true;
		}
		if (object == null || getClass() != object.getClass()) {
			return false;
		}
		ServiceVO other = (ServiceVO) object;
		return java.util.Objects.equals(id, other.id)
				&& java.util.Objects.equals(defaultOidcScope, other.defaultOidcScope)
				&& java.util.Objects.equals(oidcScopes, other.oidcScopes);
	}

	@Override
	public int hashCode() {
		return java.util.Objects.hash(id, defaultOidcScope, oidcScopes);
	}

	@Override
	public String toString() {
		return new StringBuilder()
				.append("ServiceVO[")
				.append("id=").append(id).append(",")
				.append("defaultOidcScope=").append(defaultOidcScope).append(",")
				.append("oidcScopes=").append(oidcScopes)
				.append("]")
				.toString();
	}

	// fluent

	public ServiceVO id(String newId) {
		this.id = newId;
		return this;
	}

	public ServiceVO defaultOidcScope(String newDefaultOidcScope) {
		this.defaultOidcScope = newDefaultOidcScope;
		return this;
	}

	public ServiceVO oidcScopes(ServiceScopesVO newOidcScopes) {
		this.oidcScopes = newOidcScopes;
		return this;
	}

	// getter/setter

	public String getId() {
		return id;
	}

	public void setId(String newId) {
		this.id = newId;
	}

	public String getDefaultOidcScope() {
		return defaultOidcScope;
	}

	public void setDefaultOidcScope(String newDefaultOidcScope) {
		this.defaultOidcScope = newDefaultOidcScope;
	}

	public ServiceScopesVO getOidcScopes() {
		return oidcScopes;
	}

	public void setOidcScopes(ServiceScopesVO newOidcScopes) {
		this.oidcScopes = newOidcScopes;
	}
}
