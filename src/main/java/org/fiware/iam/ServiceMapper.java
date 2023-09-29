package org.fiware.iam;

import org.fiware.iam.ccs.model.CredentialVO;
import org.fiware.iam.ccs.model.ServiceScopesEntryVO;
import org.fiware.iam.ccs.model.ServiceScopesVO;
import org.fiware.iam.ccs.model.ServiceVO;
import org.fiware.iam.repository.*;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Responsible for mapping entities from the Service api domain to the internal model.
 */
@Mapper(componentModel = "jsr330")
public interface ServiceMapper {

    default Service map(ServiceVO serviceVO) {
        return new Service()
                .setDefaultOidcScope(serviceVO.getDefaultOidcScope())
                .setId(serviceVO.getId())
                .setOidcScopes(map(serviceVO.getOidcScopes(), serviceVO.getId()));
    }

    ServiceVO map(Service service);

    default ServiceScope map(ServiceScopesEntryVO serviceScopesEntryVO, String scopeName, String serviceName) {
        return new ServiceScope()
                .setId("%s_%s".formatted(scopeName, serviceName))
                .setScopeName(scopeName)
                .setCredentials(serviceScopesEntryVO.stream().map(this::map).toList());
    }

    ServiceScopesEntryVO map(ServiceScope serviceScope);

    default Collection<ServiceScope> map(ServiceScopesVO value, String serviceName) {
        if (value.getAdditionalProperties() == null) {
            return List.of();
        }
        return value
                .getAdditionalProperties()
                .entrySet()
                .stream()
                .map(e -> map(e.getValue(), e.getKey(), serviceName))
                .toList();
    }

    default ServiceScopesVO mapEntries(Collection<ServiceScope> value) {
        ServiceScopesVO mappedScopes = new ServiceScopesVO();
        if (value != null) {
            value.forEach(e -> {
                        ServiceScopesEntryVO scopes = new ServiceScopesEntryVO();
                        scopes.addAll(map(e.getCredentials()));
                        mappedScopes.setAdditionalProperties(e.getScopeName(), scopes);
                    }
            );
        }
        return mappedScopes;
    }

    default Credential map(CredentialVO credentialVO) {
        if (credentialVO == null) {
            return null;
        }
        Credential credential = new Credential()
                .setCredentialType(credentialVO.getType());
        List<EndpointEntry> trustedList = new ArrayList<>();
        Optional.ofNullable(issuersToEntries(credentialVO.getTrustedIssuersLists())).ifPresent(trustedList::addAll);
        Optional.ofNullable(participantsToEntries(credentialVO.getTrustedParticipantsLists())).ifPresent(trustedList::addAll);
        credential.setTrustedLists(trustedList);
        return credential;
    }

    default Collection<CredentialVO> map(Collection<Credential> credentials) {
        if (credentials == null) {
            return null;
        }
        return credentials.stream().map(this::map).toList();
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
