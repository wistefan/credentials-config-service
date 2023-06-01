package org.fiware.iam;

import org.fiware.iam.ccs.model.CredentialVO;
import org.fiware.iam.ccs.model.ServiceVO;
import org.fiware.iam.repository.Credential;
import org.fiware.iam.repository.EndpointEntry;
import org.fiware.iam.repository.EndpointType;
import org.fiware.iam.repository.Service;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Responsible for mapping entities from the Service api domain to the internal model.
 */
@Mapper(componentModel = "jsr330")
public interface ServiceMapper {

	Service map(ServiceVO serviceVO);

	ServiceVO map(Service service);

	default Credential map(CredentialVO credentialVO) {
		if (credentialVO == null) {
			return null;
		}
		Credential credential = new Credential()
				.setCredentialType(credentialVO.getType());
		List<EndpointEntry> trustedList = new ArrayList<>();
		trustedList.addAll(issuersToEntries(credentialVO.getTrustedIssuersLists()));
		trustedList.addAll(participantsToEntries(credentialVO.getTrustedParticipantsLists()));
		credential.setTrustedLists(trustedList);
		return credential;
	}

	default CredentialVO map(Credential credential) {
		if (credential == null) {
			return null;
		}
		return new CredentialVO()
				.type(credential.getCredentialType())
				.trustedIssuersLists(entriesToIssuers(credential.getTrustedLists()))
				.trustedParticipantsLists(entriesToParticipants(credential.getTrustedLists()));
	}

	/**
	 * Map a list of string-entries, encoding TrustedParticipants endpoints to a list of {@link EndpointEntry} with
	 * type {{@link EndpointType.TRUSTED_PARTICIPANTS}
	 */
	default List<EndpointEntry> participantsToEntries(List<String> endpoints) {
		if (endpoints == null) {
			return null;
		}
		return endpoints.stream()
				.map(endpoint -> new EndpointEntry()
						.setEndpoint(endpoint)
						.setType(EndpointType.TRUSTED_PARTICIPANTS))
				.toList();
	}

	/**
	 * Map a list of string-entries, encoding TrustedIssuers endpoints to a list of {@link EndpointEntry} with
	 * type {{@link EndpointType.TRUSTED_ISSUERS}
	 */
	default List<EndpointEntry> issuersToEntries(List<String> endpoints) {
		if (endpoints == null) {
			return null;
		}
		return endpoints.stream()
				.map(endpoint -> new EndpointEntry()
						.setEndpoint(endpoint)
						.setType(EndpointType.TRUSTED_ISSUERS))
				.toList();
	}

	/**
	 * Return issuer endpoints from the {@link EndpointEntry} list to a list of strings
	 */
	default List<String> entriesToIssuers(List<EndpointEntry> endpoints) {
		if (endpoints == null) {
			return null;
		}
		return endpoints.stream()
				.filter(entry -> entry.getType().equals(EndpointType.TRUSTED_ISSUERS))
				.map(EndpointEntry::getEndpoint)
				.toList();
	}

	/**
	 * Return participant endpoints from the {@link EndpointEntry} list to a list of strings
	 */
	default List<String> entriesToParticipants(List<EndpointEntry> endpoints) {
		if (endpoints == null) {
			return null;
		}
		return endpoints.stream()
				.filter(entry -> entry.getType().equals(EndpointType.TRUSTED_PARTICIPANTS))
				.map(EndpointEntry::getEndpoint)
				.toList();
	}

}
