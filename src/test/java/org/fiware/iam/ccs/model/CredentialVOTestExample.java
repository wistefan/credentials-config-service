package org.fiware.iam.ccs.model;

import org.fiware.iam.ccs.model.CredentialVO;

public class CredentialVOTestExample {

	public static CredentialVO build() {
		CredentialVO exampleInstance = new CredentialVO();
		exampleInstance.setType("VerifiableCredential");
		exampleInstance.setTrustedParticipantsLists(java.util.List.of());
		exampleInstance.setTrustedIssuersLists(java.util.List.of());
		return exampleInstance;
	}
}
