package org.fiware.iam.ccs.model;

@jakarta.annotation.Generated("org.openapitools.codegen.languages.MicronautCodegen")
@io.micronaut.core.annotation.Introspected
public class ServiceScopesEntryVO extends java.util.ArrayList<CredentialVO> {


	// methods

	@Override
	public boolean equals(Object object) {
		if (object == this) {
			return true;
		}
		if (object == null || getClass() != object.getClass()) {
			return false;
		}
		return super.equals(object);
	}

	@Override
	public int hashCode() {
		return java.util.Objects.hash();
	}

	@Override
	public String toString() {
		return new StringBuilder()
				.append("ServiceScopesEntryVO[")
				.append("super").append(super.toString())
				.append("]")
				.toString();
	}
}
