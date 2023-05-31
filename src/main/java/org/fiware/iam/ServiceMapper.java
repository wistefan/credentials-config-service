package org.fiware.iam;

import org.fiware.iam.ccs.model.CredentialVO;
import org.fiware.iam.ccs.model.ServiceVO;
import org.fiware.iam.repository.Credential;
import org.fiware.iam.repository.EndpointEntry;
import org.fiware.iam.repository.Service;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

/**
 * Responsible for mapping entities from the Service api domain to the internal model.
 */
@Mapper(componentModel = "jsr330")
public interface ServiceMapper {

	Service map(ServiceVO serviceVO);

	ServiceVO map(Service service);

	@Mapping(source = "type", target = "credentialType")
	@Mapping(source = "trustedParticipantsLists", target = "trustedParticipantsLists", qualifiedByName = "stringsToEntries")
	@Mapping(source = "trustedIssuersLists", target = "trustedIssuersLists", qualifiedByName = "stringsToEntries")
	Credential map(CredentialVO credentialVO);

	@Mapping(source = "credentialType", target = "type")
	@Mapping(source = "trustedParticipantsLists", target = "trustedParticipantsLists", qualifiedByName = "entriesToStrings")
	@Mapping(source = "trustedIssuersLists", target = "trustedIssuersLists", qualifiedByName = "entriesToStrings")
	CredentialVO map(Credential credential);

	@Named("stringsToEntries")
	default List<EndpointEntry> stringsToEntries(List<String> endpoints) {
		if (endpoints == null) {
			return null;
		}
		return endpoints.stream()
				.map(endpoint -> new EndpointEntry().setEndpoint(endpoint))
				.toList();
	}


	@Named("entriesToStrings")
	default List<String> entriesToStrings(List<EndpointEntry> endpoints) {
		if (endpoints == null) {
			return null;
		}
		return endpoints.stream()
				.map(EndpointEntry::getEndpoint)
				.toList();
	}

}
