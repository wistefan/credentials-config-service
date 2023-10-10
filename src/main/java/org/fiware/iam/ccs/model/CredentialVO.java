package org.fiware.iam.ccs.model;

@jakarta.annotation.Generated("org.openapitools.codegen.languages.MicronautCodegen")
@io.micronaut.core.annotation.Introspected
public class CredentialVO {

	public static final String JSON_PROPERTY_TYPE = "type";
	public static final String JSON_PROPERTY_TRUSTED_PARTICIPANTS_LISTS = "trustedParticipantsLists";
	public static final String JSON_PROPERTY_TRUSTED_ISSUERS_LISTS = "trustedIssuersLists";

	/** Type of the credential */
	@com.fasterxml.jackson.annotation.JsonProperty(JSON_PROPERTY_TYPE)
	@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.ALWAYS)
	private String type;

	/** A list of (EBSI Trusted Issuers Registry compatible) endpoints to  retrieve the trusted participants from.  */
	@com.fasterxml.jackson.annotation.JsonProperty(JSON_PROPERTY_TRUSTED_PARTICIPANTS_LISTS)
	@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
	private java.util.List<String> trustedParticipantsLists;

	/** A list of (EBSI Trusted Issuers Registry compatible) endpoints to  retrieve the trusted issuers from. The attributes need to be formated to comply with the verifiers requirements.  */
	@com.fasterxml.jackson.annotation.JsonProperty(JSON_PROPERTY_TRUSTED_ISSUERS_LISTS)
	@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
	private java.util.List<String> trustedIssuersLists;

	// methods

	@Override
	public boolean equals(Object object) {
		if (object == this) {
			return true;
		}
		if (object == null || getClass() != object.getClass()) {
			return false;
		}
		CredentialVO other = (CredentialVO) object;
		return java.util.Objects.equals(type, other.type)
				&& java.util.Objects.equals(trustedParticipantsLists, other.trustedParticipantsLists)
				&& java.util.Objects.equals(trustedIssuersLists, other.trustedIssuersLists);
	}

	@Override
	public int hashCode() {
		return java.util.Objects.hash(type, trustedParticipantsLists, trustedIssuersLists);
	}

	@Override
	public String toString() {
		return new StringBuilder()
				.append("CredentialVO[")
				.append("type=").append(type).append(",")
				.append("trustedParticipantsLists=").append(trustedParticipantsLists).append(",")
				.append("trustedIssuersLists=").append(trustedIssuersLists)
				.append("]")
				.toString();
	}

	// fluent

	public CredentialVO type(String newType) {
		this.type = newType;
		return this;
	}

	public CredentialVO trustedParticipantsLists(java.util.List<String> newTrustedParticipantsLists) {
		this.trustedParticipantsLists = newTrustedParticipantsLists;
		return this;
	}
	
	public CredentialVO addTrustedParticipantsListsItem(String trustedParticipantsListsItem) {
		if (this.trustedParticipantsLists == null) {
			this.trustedParticipantsLists = new java.util.ArrayList<>();
		}
		this.trustedParticipantsLists.add(trustedParticipantsListsItem);
		return this;
	}

	public CredentialVO removeTrustedParticipantsListsItem(String trustedParticipantsListsItem) {
		if (this.trustedParticipantsLists != null) {
			this.trustedParticipantsLists.remove(trustedParticipantsListsItem);
		}
		return this;
	}

	public CredentialVO trustedIssuersLists(java.util.List<String> newTrustedIssuersLists) {
		this.trustedIssuersLists = newTrustedIssuersLists;
		return this;
	}
	
	public CredentialVO addTrustedIssuersListsItem(String trustedIssuersListsItem) {
		if (this.trustedIssuersLists == null) {
			this.trustedIssuersLists = new java.util.ArrayList<>();
		}
		this.trustedIssuersLists.add(trustedIssuersListsItem);
		return this;
	}

	public CredentialVO removeTrustedIssuersListsItem(String trustedIssuersListsItem) {
		if (this.trustedIssuersLists != null) {
			this.trustedIssuersLists.remove(trustedIssuersListsItem);
		}
		return this;
	}

	// getter/setter

	public String getType() {
		return type;
	}

	public void setType(String newType) {
		this.type = newType;
	}

	public java.util.List<String> getTrustedParticipantsLists() {
		return trustedParticipantsLists;
	}

	public void setTrustedParticipantsLists(java.util.List<String> newTrustedParticipantsLists) {
		this.trustedParticipantsLists = newTrustedParticipantsLists;
	}

	public java.util.List<String> getTrustedIssuersLists() {
		return trustedIssuersLists;
	}

	public void setTrustedIssuersLists(java.util.List<String> newTrustedIssuersLists) {
		this.trustedIssuersLists = newTrustedIssuersLists;
	}
}
