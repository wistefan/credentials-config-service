package org.fiware.iam;

import org.fiware.iam.ccs.model.*;
import org.fiware.iam.repository.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ServiceMapperTest {

    private ServiceMapper serviceMapper = new ServiceMapperImpl();

    @Test
    void testMapToService() {
        // Create ServiceVO
        ServiceScopesEntryVO serviceScopesEntryVO_1 =
                ServiceScopesEntryVOTestExample.build();
        CredentialVO credentialVO_1 = CredentialVOTestExample.build()
                .type("my-credential")
                .trustedIssuersLists(List.of("http://til.de"))
                .trustedParticipantsLists(List.of("http://tir.de"));
        serviceScopesEntryVO_1.add(credentialVO_1);
        ServiceScopesVO serviceScopesVO = ServiceScopesVOTestExample.build();
        serviceScopesVO.setAdditionalProperties("test-oidc-scope", serviceScopesEntryVO_1);
        ServiceVO serviceVO = ServiceVOTestExample.build().oidcScopes(serviceScopesVO);
        serviceVO.setDefaultOidcScope("test-oidc-scope");

        // Map to Service
        Service service = serviceMapper.map(serviceVO);

        // Asserts
        assertEquals("test-oidc-scope", service.getDefaultOidcScope(),
                "should have equal default OIDC scope");

        Collection<ServiceScope> serviceScopes = service.getOidcScopes();
        assertEquals(1, serviceScopes.size(), "Collection of service scopes should have 1 entry");

        ServiceScope serviceScope = serviceScopes.iterator().next();
        Collection<Credential> credentials = serviceScope.getCredentials();
        assertEquals(1, credentials.size(), "Collection of credentials should have 1 entry");
        assertEquals("test-oidc-scope", serviceScope.getScopeName(),
                "ServiceScope should have correct name");

        Credential credential = credentials.iterator().next();
        assertEquals("my-credential", credential.getCredentialType(),
                "Credential should have correct type");

        List<EndpointEntry> endpointEntries = credential.getTrustedLists();
        assertEquals(2, endpointEntries.size(), "Trusted lists should have 2 entries");

        for (EndpointEntry endpointEntry : endpointEntries) {
            if (endpointEntry.getType() == EndpointType.TRUSTED_ISSUERS) {
                assertEquals("http://til.de", endpointEntry.getEndpoint(),
                        "Trusted issuer endpoint should have correct URL");
            } else if (endpointEntry.getType() == EndpointType.TRUSTED_PARTICIPANTS) {
                assertEquals("http://tir.de", endpointEntry.getEndpoint(),
                        "Trusted participants endpoint should have correct URL");
            } else {
                fail("Invalid EndpointEntry type");
            }
        }
    }

    @Test
    void testMapToServiceMultipleScopesAndCredentials() {
        // Create ServiceVO
        ServiceScopesEntryVO serviceScopesEntryVO_1 =
                ServiceScopesEntryVOTestExample.build();
        ServiceScopesEntryVO serviceScopesEntryVO_2 =
                ServiceScopesEntryVOTestExample.build();
        CredentialVO credentialVO_1 = CredentialVOTestExample.build()
                .type("my-credential")
                .trustedIssuersLists(List.of("http://til.de"))
                .trustedParticipantsLists(List.of("http://tir.de"));
        serviceScopesEntryVO_1.add(credentialVO_1);
        CredentialVO credentialVO_2 = CredentialVOTestExample.build()
                .type("my-credential")
                .trustedIssuersLists(List.of("http://til.de"))
                .trustedParticipantsLists(List.of("http://tir.de", "another-tir.de"));
        CredentialVO credentialVO_3 = CredentialVOTestExample.build()
                .type("another-credential")
                .trustedIssuersLists(List.of("http://til.de"))
                .trustedParticipantsLists(List.of("http://tir.de", "another-tir.de"));
        serviceScopesEntryVO_2.add(credentialVO_2);
        serviceScopesEntryVO_2.add(credentialVO_3);
        ServiceScopesVO serviceScopesVO = ServiceScopesVOTestExample.build();
        serviceScopesVO.setAdditionalProperties("test-oidc-scope", serviceScopesEntryVO_1);
        serviceScopesVO.setAdditionalProperties("another-oidc-scope", serviceScopesEntryVO_2);
        ServiceVO serviceVO = ServiceVOTestExample.build().oidcScopes(serviceScopesVO);
        serviceVO.setDefaultOidcScope("test-oidc-scope");

        // Map to Service
        Service service = serviceMapper.map(serviceVO);

        // Asserts
        assertEquals("test-oidc-scope", service.getDefaultOidcScope(),
                "should have correct default OIDC scope");

        Collection<ServiceScope> serviceScopes = service.getOidcScopes();
        assertEquals(2, serviceScopes.size(),
                "Collection of service scopes should have 2 entries");

        Collection<Credential> credentials = null;
        for (ServiceScope serviceScope : serviceScopes) {
            credentials = serviceScope.getCredentials();
            if (credentials.size() == 1) {
                assertEquals("test-oidc-scope", serviceScope.getScopeName(),
                        "ServiceScope should have correct name");

                Credential credential = credentials.iterator().next();
                assertEquals("my-credential", credential.getCredentialType(),
                        "Credential should have correct type");

                List<EndpointEntry> endpointEntries = credential.getTrustedLists();
                assertEquals(2, endpointEntries.size(), "Trusted lists should have 2 entries");

                for (EndpointEntry endpointEntry : endpointEntries) {
                    if (endpointEntry.getType() == EndpointType.TRUSTED_ISSUERS) {
                        assertEquals("http://til.de", endpointEntry.getEndpoint(),
                                "Trusted issuer endpoint should have correct URL");
                    } else if (endpointEntry.getType() == EndpointType.TRUSTED_PARTICIPANTS) {
                        assertEquals("http://tir.de", endpointEntry.getEndpoint(),
                                "Trusted participants endpoint should have correct URL");
                    } else {
                        fail("Invalid EndpointEntry type");
                    }
                }
            } else if (credentials.size() == 2) {
                assertEquals("another-oidc-scope", serviceScope.getScopeName(),
                        "ServiceScope should have correct name");
                for (Credential credential : credentials) {
                    assertTrue(credential.getCredentialType() == "my-credential" ||
                            credential.getCredentialType() == "another-credential",
                            "Credential should have correct type");

                    List<EndpointEntry> endpointEntries = credential.getTrustedLists();
                    assertEquals(3, endpointEntries.size(), "Trusted lists should have 3 entries");

                    for (EndpointEntry endpointEntry : endpointEntries) {
                        if (endpointEntry.getType() == EndpointType.TRUSTED_ISSUERS) {
                            assertEquals("http://til.de", endpointEntry.getEndpoint(),
                                    "Trusted issuer endpoint should have correct URL");
                        } else if (endpointEntry.getType() == EndpointType.TRUSTED_PARTICIPANTS) {
                            assertTrue(
                                    endpointEntry.getEndpoint() == "http://tir.de" ||
                                            endpointEntry.getEndpoint() == "another-tir.de",
                                    "Trusted participants endpoints should have correct URL");
                        } else {
                            fail("Invalid EndpointEntry type");
                        }
                    }
                }
            } else {
                fail("ServiceScope should have either 1 or 2 credentials");
            }
        }
    }

    @Test
    void testMapToServiceVO() {
        // Create Service
        EndpointEntry endpointEntryTil = new EndpointEntry();
        endpointEntryTil.setType(EndpointType.TRUSTED_ISSUERS);
        endpointEntryTil.setEndpoint("http://til.de");
        EndpointEntry endpointEntryTir = new EndpointEntry();
        endpointEntryTir.setType(EndpointType.TRUSTED_PARTICIPANTS);
        endpointEntryTir.setEndpoint("http://tir.de");

        Credential credential = new Credential();
        credential.setCredentialType("my-credential");
        List<EndpointEntry> endpointEntries = List.of(endpointEntryTil, endpointEntryTir);
        credential.setTrustedLists(endpointEntries);

        ServiceScope serviceScope = new ServiceScope();
        serviceScope.setScopeName("test-oidc-scope");
        Collection<Credential> credentials = List.of(credential);
        serviceScope.setCredentials(credentials);

        Service service = new Service();
        service.setDefaultOidcScope("test-oidc-scope");
        Collection<ServiceScope> oidcScopes = List.of(serviceScope);
        service.setOidcScopes(oidcScopes);

        // Map to ServiceVO
        ServiceVO serviceVO = serviceMapper.map(service);

        // Asserts
        assertEquals("test-oidc-scope", serviceVO.getDefaultOidcScope(),
                "ServiceVO should have correct defaultOidcScope");

        ServiceScopesVO serviceScopesVO = serviceVO.getOidcScopes();
        Map<String, ServiceScopesEntryVO> additionalProperties = serviceScopesVO.getAdditionalProperties();
        assertEquals(1, additionalProperties.size(), "ServiceVO should have 1 OIDC scope");

        ServiceScopesEntryVO serviceScopesEntryVO = additionalProperties.get("test-oidc-scope");
        assertNotNull(serviceScopesEntryVO,
                "ServiceVO should have an OIDC scope with key '\"test-oidc-scope\"'");

        assertEquals(1, serviceScopesEntryVO.size(),
                "the OIDC scope should have 1 credential");
        CredentialVO credentialVO = serviceScopesEntryVO.get(0);
        assertEquals("my-credential", credentialVO.getType(),
                "the credential should have the correct type");

        List<String> trustedParticipantsLists = credentialVO.getTrustedParticipantsLists();
        assertEquals(1, trustedParticipantsLists.size(),
                "the trusted participants list should have 1 entry");
        String trustedParticipantUrl = trustedParticipantsLists.get(0);
        assertEquals("http://tir.de", trustedParticipantUrl,
                "the trusted participants list entry should have the correct URL");

        List<String> trustedIssuersLists = credentialVO.getTrustedIssuersLists();
        assertEquals(1, trustedIssuersLists.size(),
                "the trusted issuers list should have 1 entry");
        String trustedIssuerUrl = trustedIssuersLists.get(0);
        assertEquals("http://til.de", trustedIssuerUrl,
                "the trusted issuers list entry should have the correct URL");
    }

}
