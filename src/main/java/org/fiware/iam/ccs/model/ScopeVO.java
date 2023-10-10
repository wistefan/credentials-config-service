package org.fiware.iam.ccs.model;

@jakarta.annotation.Generated("org.openapitools.codegen.languages.MicronautCodegen")
@io.micronaut.core.annotation.Introspected
public class ScopeVO extends java.util.ArrayList<String> {


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
				.append("ScopeVO[")
				.append("super").append(super.toString())
				.append("]")
				.toString();
	}
}
