package org.fiware.iam.rest;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import lombok.RequiredArgsConstructor;
import org.fiware.iam.ccs.api.ServiceApiTestClient;
import org.fiware.iam.ccs.api.ServiceApiTestSpec;
import org.fiware.iam.ccs.model.*;
import org.fiware.iam.repository.ServiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
@MicronautTest
public class ServiceApiControllerTest implements ServiceApiTestSpec {

    public final ServiceApiTestClient testClient;
    public final ServiceRepository serviceRepository;

    private ServiceVO theService;
    private List<String> expectedScopes;
    private int pageSize;
    private int pageNumber;

    @BeforeEach
    public void cleanUp() {
        serviceRepository.deleteAll();
    }

    @Override
    public void createService201() throws Exception {
        HttpResponse<?> creationResponse = testClient.createService(theService);
        assertEquals(HttpStatus.CREATED, creationResponse.getStatus(), "The service should have been created.");
        assertTrue(creationResponse.getHeaders().contains("Location"), "Id should be returned as location header.");
        String location = creationResponse.header("Location");
        if (theService.getId() != null) {
            assertTrue(location.endsWith(theService.getId()), "The provided id should be used.");
        }
    }

    @ParameterizedTest
    @MethodSource("validServices")
    public void createService201(ServiceVO serviceVO) throws Exception {
        theService = serviceVO;
        createService201();
    }

    private static Stream<Arguments> validServices() {
        // Empty service with ID
        ServiceScopesEntryVO serviceScopesEntryVO =
                ServiceScopesEntryVOTestExample.build();
        CredentialVO credentialVO = CredentialVOTestExample.build();
        serviceScopesEntryVO.add(credentialVO);
        ServiceScopesVO serviceScopesVO = ServiceScopesVOTestExample.build();
        serviceScopesVO.setAdditionalProperties("test-oidc-scope", serviceScopesEntryVO);
        ServiceVO serviceVOEmptyWithId = ServiceVOTestExample.build().id("my-service");
        serviceVOEmptyWithId.setDefaultOidcScope("test-oidc-scope");
        serviceVOEmptyWithId.setOidcScopes(serviceScopesVO);

        // Empty credential
        ServiceVO serviceVO = ServiceVOTestExample.build().oidcScopes(serviceScopesVO);
        serviceVO.setDefaultOidcScope("test-oidc-scope");

        // 2 - Credential with type
        ServiceScopesEntryVO serviceScopesEntryVO2 =
                ServiceScopesEntryVOTestExample.build();
        CredentialVO credentialVO2 = CredentialVOTestExample.build().type("my-credential");
        serviceScopesEntryVO2.add(credentialVO2);
        ServiceScopesVO serviceScopesVO2 = ServiceScopesVOTestExample.build();
        serviceScopesVO2.setAdditionalProperties("test-oidc-scope", serviceScopesEntryVO2);
        ServiceVO serviceVO2 = ServiceVOTestExample.build().oidcScopes(serviceScopesVO2);
        serviceVO2.setDefaultOidcScope("test-oidc-scope");

        // 3 - Credential with type + TIL entry
        ServiceScopesEntryVO serviceScopesEntryVO3 =
                ServiceScopesEntryVOTestExample.build();
        CredentialVO credentialVO3 = CredentialVOTestExample.build()
                .type("my-credential")
                .trustedIssuersLists(List.of("http://til.de"));
        serviceScopesEntryVO3.add(credentialVO3);
        ServiceScopesVO serviceScopesVO3 = ServiceScopesVOTestExample.build();
        serviceScopesVO3.setAdditionalProperties("test-oidc-scope", serviceScopesEntryVO3);
        ServiceVO serviceVO3 = ServiceVOTestExample.build().oidcScopes(serviceScopesVO3);
        serviceVO3.setDefaultOidcScope("test-oidc-scope");

        // 4 - Credential with type + 2 TIL entries
        ServiceScopesEntryVO serviceScopesEntryVO4 =
                ServiceScopesEntryVOTestExample.build();
        CredentialVO credentialVO4 = CredentialVOTestExample.build()
                .type("my-credential")
                .trustedIssuersLists(List.of("http://til.de", "http://another-til.de"));
        serviceScopesEntryVO4.add(credentialVO4);
        ServiceScopesVO serviceScopesVO4 = ServiceScopesVOTestExample.build();
        serviceScopesVO4.setAdditionalProperties("test-oidc-scope", serviceScopesEntryVO4);
        ServiceVO serviceVO4 = ServiceVOTestExample.build().oidcScopes(serviceScopesVO4);
        serviceVO4.setDefaultOidcScope("test-oidc-scope");

        // 5 - Credential with type + TIL entry + TIR entry
        ServiceScopesEntryVO serviceScopesEntryVO5 =
                ServiceScopesEntryVOTestExample.build();
        CredentialVO credentialVO5 = CredentialVOTestExample.build()
                .type("my-credential")
                .trustedIssuersLists(List.of("http://til.de"))
                .trustedParticipantsLists(List.of("http://tir.de"));
        serviceScopesEntryVO5.add(credentialVO5);
        ServiceScopesVO serviceScopesVO5 = ServiceScopesVOTestExample.build();
        serviceScopesVO5.setAdditionalProperties("test-oidc-scope", serviceScopesEntryVO5);
        ServiceVO serviceVO5 = ServiceVOTestExample.build().oidcScopes(serviceScopesVO5);
        serviceVO5.setDefaultOidcScope("test-oidc-scope");

        // 6 - Credential with type + TIL entry + 2 TIR entries
        ServiceScopesEntryVO serviceScopesEntryVO6 =
                ServiceScopesEntryVOTestExample.build();
        CredentialVO credentialVO6 = CredentialVOTestExample.build()
                .type("my-credential")
                .trustedIssuersLists(List.of("http://til.de"))
                .trustedParticipantsLists(List.of("http://tir.de", "another-tir.de"));
        serviceScopesEntryVO6.add(credentialVO6);
        ServiceScopesVO serviceScopesVO6 = ServiceScopesVOTestExample.build();
        serviceScopesVO6.setAdditionalProperties("test-oidc-scope", serviceScopesEntryVO6);
        ServiceVO serviceVO6 = ServiceVOTestExample.build().oidcScopes(serviceScopesVO6);
        serviceVO6.setDefaultOidcScope("test-oidc-scope");

        // 7 - Credential with type + 2 TIR entries
        ServiceScopesEntryVO serviceScopesEntryVO7 =
                ServiceScopesEntryVOTestExample.build();
        CredentialVO credentialVO7 = CredentialVOTestExample.build()
                .type("my-credential")
                .trustedParticipantsLists(List.of("http://tir.de", "another-tir.de"));
        serviceScopesEntryVO7.add(credentialVO7);
        ServiceScopesVO serviceScopesVO7 = ServiceScopesVOTestExample.build();
        serviceScopesVO7.setAdditionalProperties("test-oidc-scope", serviceScopesEntryVO7);
        ServiceVO serviceVO7 = ServiceVOTestExample.build().oidcScopes(serviceScopesVO7);
        serviceVO7.setDefaultOidcScope("test-oidc-scope");

        // 8 - 2 Credentials with type (2 TIR entries / 1 TIR + 1 TIL entry)
        ServiceScopesEntryVO serviceScopesEntryVO8 =
                ServiceScopesEntryVOTestExample.build();
        CredentialVO credentialVO8_1 = CredentialVOTestExample.build()
                .type("my-credential")
                .trustedParticipantsLists(List.of("http://tir.de", "another-tir.de"));
        CredentialVO credentialVO8_2 = CredentialVOTestExample.build()
                .type("another-credential")
                .trustedIssuersLists(List.of("til.de"))
                .trustedParticipantsLists(List.of("http://tir.de"));
        serviceScopesEntryVO8.add(credentialVO8_1);
        serviceScopesEntryVO8.add(credentialVO8_2);
        ServiceScopesVO serviceScopesVO8 = ServiceScopesVOTestExample.build();
        serviceScopesVO8.setAdditionalProperties("test-oidc-scope", serviceScopesEntryVO8);
        ServiceVO serviceVO8 = ServiceVOTestExample.build().oidcScopes(serviceScopesVO8);
        serviceVO8.setDefaultOidcScope("test-oidc-scope");

        // 9 - 2 OIDC scopes, each with different credentials
        ServiceScopesEntryVO serviceScopesEntryVO9_1 =
                ServiceScopesEntryVOTestExample.build();
        ServiceScopesEntryVO serviceScopesEntryVO9_2 =
                ServiceScopesEntryVOTestExample.build();
        CredentialVO credentialVO9_1 = CredentialVOTestExample.build()
                .type("my-credential")
                .trustedIssuersLists(List.of("http://til.de"))
                .trustedParticipantsLists(List.of("http://tir.de"));
        serviceScopesEntryVO9_1.add(credentialVO9_1);
        CredentialVO credentialVO9_2 = CredentialVOTestExample.build()
                .type("my-credential")
                .trustedIssuersLists(List.of("http://til.de"))
                .trustedParticipantsLists(List.of("http://tir.de", "another-tir.de"));
        CredentialVO credentialVO9_3 = CredentialVOTestExample.build()
                .type("another-credential")
                .trustedIssuersLists(List.of("http://til.de"))
                .trustedParticipantsLists(List.of("http://tir.de", "another-tir.de"));
        serviceScopesEntryVO9_2.add(credentialVO9_2);
        serviceScopesEntryVO9_2.add(credentialVO9_3);
        ServiceScopesVO serviceScopesVO9 = ServiceScopesVOTestExample.build();
        serviceScopesVO9.setAdditionalProperties("test-oidc-scope", serviceScopesEntryVO9_1);
        serviceScopesVO9.setAdditionalProperties("another-oidc-scope", serviceScopesEntryVO9_2);
        ServiceVO serviceVO9 = ServiceVOTestExample.build().oidcScopes(serviceScopesVO9);
        serviceVO9.setDefaultOidcScope("test-oidc-scope");

        return Stream.of(
                // Empty service with ID
                Arguments.of(serviceVOEmptyWithId, List.of()),
                // Empty credential
                Arguments.of(serviceVO,
                        List.of("VerifiableCredential")),
                // 2 - Credential with type
                Arguments.of(serviceVO2,
                        List.of("my-credential")),
                // 3 - Credential with type + TIL entry
                Arguments.of(serviceVO3,
                        List.of("my-credential")),
                // 4 - Credential with type + 2 TIL entries
                Arguments.of(serviceVO4,
                        List.of("my-credential")),
                // 5 - Credential with type + TIL entry + TIR entry
                Arguments.of(serviceVO5,
                        List.of("my-credential")),
                // 6 - Credential with type + TIL entry + 2 TIR entries
                Arguments.of(serviceVO6,
                        List.of("my-credential")),
                // 7 - Credential with type + 2 TIR entries
                Arguments.of(serviceVO7,
                        List.of("my-credential")),
                // 8 - 2 Credentials with type (2 TIR entries / 1 TIR + 1 TIL entry)
                Arguments.of(serviceVO8,
                        List.of("my-credential")),
                // 9 - 2 OIDC scopes, each with different credentials
                Arguments.of(serviceVO9,
                        List.of("my-credential"))
        );
    }

    @Override
    public void createService400() throws Exception {
        try {
            testClient.createService(theService);
        } catch (HttpClientResponseException e) {
            assertEquals(HttpStatus.BAD_REQUEST, e.getStatus(), "The service should not have been created.");
            return;
        }
        fail("The creation attempt should fail for an invalid service.");
    }

    @ParameterizedTest
    @MethodSource("invalidServices")
    public void createService400(ServiceVO serviceVO) throws Exception {
        theService = serviceVO;
        createService400();
    }

    private static Stream<Arguments> invalidServices() {
        // Credential with empty type
        ServiceScopesEntryVO serviceScopesEntryVO =
                ServiceScopesEntryVOTestExample.build();
        CredentialVO credentialVO = CredentialVOTestExample.build().type(null);
        serviceScopesEntryVO.add(credentialVO);
        ServiceScopesVO serviceScopesVO = ServiceScopesVOTestExample.build();
        serviceScopesVO.setAdditionalProperties("test-oidc-scope", serviceScopesEntryVO);
        ServiceVO serviceVO = ServiceVOTestExample.build().oidcScopes(serviceScopesVO);
        serviceVO.setDefaultOidcScope("test-oidc-scope");

        // 2 - 2 Credentials, but 1 has empty type
        ServiceScopesEntryVO serviceScopesEntryVO2 =
                ServiceScopesEntryVOTestExample.build();
        CredentialVO credentialVO2_1 = CredentialVOTestExample.build();
        serviceScopesEntryVO2.add(credentialVO2_1);
        CredentialVO credentialVO2_2 = CredentialVOTestExample.build().type(null);
        serviceScopesEntryVO2.add(credentialVO2_2);
        ServiceScopesVO serviceScopesVO2 = ServiceScopesVOTestExample.build();
        serviceScopesVO2.setAdditionalProperties("test-oidc-scope", serviceScopesEntryVO2);
        ServiceVO serviceVO2 = ServiceVOTestExample.build().oidcScopes(serviceScopesVO2);
        serviceVO2.setDefaultOidcScope("test-oidc-scope");

        // 3 - 1 OIDC scope/Credential, but no default OIDC scope
        ServiceScopesEntryVO serviceScopesEntryVO3 =
                ServiceScopesEntryVOTestExample.build();
        CredentialVO credentialVO3 = CredentialVOTestExample.build().type(null);
        serviceScopesEntryVO3.add(credentialVO3);
        ServiceScopesVO serviceScopesVO3 = ServiceScopesVOTestExample.build();
        serviceScopesVO3.setAdditionalProperties("test-oidc-scope", serviceScopesEntryVO3);
        ServiceVO serviceVO3 = ServiceVOTestExample.build().oidcScopes(serviceScopesVO3);
        serviceVO3.setDefaultOidcScope(null);

        return Stream.of(
                // Service with empty OIDC scopes
                Arguments.of(ServiceVOTestExample.build().oidcScopes(null)),
                // Service with ID but empty OIDC scopes
                Arguments.of(ServiceVOTestExample.build().id("my-service").oidcScopes(null)),
                // Credential with empty type
                Arguments.of(serviceVO),
                // 2 - 2 Credentials, but 1 has empty type
                Arguments.of(serviceVO2),
                // 3 - 1 OIDC scope/Credential, but no default OIDC scope
                Arguments.of(serviceVO3)
        );
    }

    @Test
    @Override
    public void createService409() throws Exception {
        ServiceVO serviceToBeCreated = ServiceVOTestExample.build().id("my-service");
        assertEquals(HttpStatus.CREATED, testClient.createService(serviceToBeCreated).getStatus(),
                "The initial creation should succeed.");
        try {
            testClient.createService(serviceToBeCreated);
        } catch (HttpClientResponseException e) {
            assertEquals(HttpStatus.CONFLICT, e.getStatus(), "Creating another service with the same id should fail.");
            return;
        }
        fail("Creating another service with the same id should fail.");

    }

    @Test
    @Override
    public void deleteServiceById204() throws Exception {
        ServiceVO serviceToBeCreated = ServiceVOTestExample.build().id("my-service");
        assertEquals(HttpStatus.CREATED, testClient.createService(serviceToBeCreated).getStatus(),
                "The initial creation should succeed.");

        HttpResponse<?> deletionResponse = testClient.deleteServiceById("my-service");
        assertEquals(HttpStatus.NO_CONTENT, deletionResponse.getStatus(), "The service should have been deleted.");
        assertFalse(serviceRepository.existsById("my-service"), "The service should have been deleted");

    }

    @Test
    @Override
    public void deleteServiceById404() throws Exception {
        HttpResponse<?> deletionResponse = testClient.deleteServiceById("my-service");
        assertEquals(HttpStatus.NOT_FOUND, deletionResponse.getStatus(), "The deletion request should not succeed.");
    }

    @Override
    public void getScopeForService200() throws Exception {
        // stable id, so that we can retrieve
        theService.setId("my-service");
        assertEquals(HttpStatus.CREATED, testClient.createService(theService).getStatus(),
                "The service should be initially created.");
        HttpResponse<ScopeVO> scopeResponse = testClient.getScopeForService("my-service", null);
		java.util.List<java.lang.String> returnedScope = scopeResponse.body();
        assertTrue(returnedScope.size() == expectedScopes.size() && returnedScope.containsAll(
                        expectedScopes) && expectedScopes.containsAll(returnedScope),
                "All expected scopes should have been returned.");
    }

    @ParameterizedTest
    @MethodSource("validServices")
    public void getScopeForService200(ServiceVO serviceVO, List<String> scopes) throws Exception {
        theService = serviceVO;
        expectedScopes = scopes;
        getScopeForService200();
    }

    @Test
    @Override
    public void getScopeForService404() throws Exception {
        assertEquals(HttpStatus.NOT_FOUND, testClient.getScopeForService("my-service", null).getStatus(),
                "If no such service exists, a 404 should be returned.");
    }

    @Override
    public void getService200() throws Exception {

        HttpResponse<ServiceVO> theServiceResponse = testClient.getService(theService.getId());
        assertEquals(HttpStatus.OK, theServiceResponse.getStatus(), "The service should be responded with status OK.");
        assertEquals(theService, theServiceResponse.body(), "The service should be responded with equal service object.");
    }

    @ParameterizedTest
    @MethodSource("validServices")
    public void getService200(ServiceVO serviceVO) throws Exception {
        serviceVO.setId(Optional.ofNullable(serviceVO.getId()).orElse(UUID.randomUUID().toString()));
        theService = serviceVO;
        assertEquals(HttpStatus.CREATED, testClient.createService(serviceVO).getStatus(),
                "The service should have been initially created.");
        getService200();
    }

    @Test
    @Override
    public void getService404() throws Exception {
        assertEquals(HttpStatus.NOT_FOUND, testClient.getService("my-service").status(),
                "If no service exists, a 404 should be returned.");
    }

    @Test
    @Override
    public void getServices200() throws Exception {
        HttpResponse<ServicesVO> servicesVOHttpResponse = testClient.getServices(null, null);
        assertEquals(HttpStatus.OK, servicesVOHttpResponse.status(),
                "If no services exist, an empty list should be returned.");
        assertTrue(servicesVOHttpResponse.body().getServices().isEmpty(),
                "If no services exist, an empty list should be returned.");

        List<ServiceVO> services = new ArrayList<>();
        for (int i = 10; i < 30; i++) {
            ServiceScopesEntryVO serviceScopesEntryVO =
                    ServiceScopesEntryVOTestExample.build();
            CredentialVO credentialVO = CredentialVOTestExample.build();
            serviceScopesEntryVO.add(credentialVO);
            ServiceScopesVO serviceScopesVO = ServiceScopesVOTestExample.build();
            serviceScopesVO.setAdditionalProperties("test-oidc-scope", serviceScopesEntryVO);
            ServiceVO serviceVO = ServiceVOTestExample.build()
                    .id(String.valueOf(i))
                    .oidcScopes(serviceScopesVO);
            serviceVO.setDefaultOidcScope("test-oidc-scope");

            assertEquals(HttpStatus.CREATED, testClient.createService(serviceVO).status(),
                    "Initial creation should succeed.");
            services.add(serviceVO);
        }
        servicesVOHttpResponse = testClient.getServices(null, null);
        assertEquals(HttpStatus.OK, servicesVOHttpResponse.status(), "The services should have been returned.");
        assertServicesResponse(20, 20, 10, 29, 0, servicesVOHttpResponse.body());

        servicesVOHttpResponse = testClient.getServices(10, null);
        assertEquals(HttpStatus.OK, servicesVOHttpResponse.getStatus(), "The services should have been returned");
        assertServicesResponse(20, 10, 10, 19, 0, servicesVOHttpResponse.body());

        servicesVOHttpResponse = testClient.getServices(10, 1);
        assertEquals(HttpStatus.OK, servicesVOHttpResponse.getStatus(), "The services should have been returned");
        assertServicesResponse(20, 10, 20, 29, 1, servicesVOHttpResponse.body());

    }

    private void assertServicesResponse(int total, int pageSize, int startIndex, int endIndex, int pageNumber,
                                        ServicesVO servicesVO) {
        assertEquals(total, servicesVO.getTotal(), "The correct total should be returned");
        assertEquals(pageNumber, servicesVO.getPageNumber(), "The correct page should have been returend.");
        assertEquals(pageSize, servicesVO.getPageSize(), "The correct page size should be returned.");
        assertEquals(endIndex - startIndex + 1, servicesVO.getServices().size(),
                "All requested items should be included.");
        assertEquals(String.format("%s", startIndex), servicesVO.getServices().get(0).getId(),
                "The correct start item should be returned.");
        assertEquals(String.format("%s", endIndex), servicesVO.getServices().get(endIndex - startIndex).getId(),
                "The correct end item should be returned.");
    }

    @Override
    public void getServices400() throws Exception {
        try {
            testClient.getServices(pageSize, pageNumber);
        } catch (HttpClientResponseException e) {
            assertEquals(HttpStatus.BAD_REQUEST, e.getStatus(), "Invalid pagination parameters should be rejected.");
            return;
        }
        fail("Invalid pagination parameters should be rejected.");
    }

    @ParameterizedTest
    @MethodSource("invalidPagination")
    public void getServices400(int pageSize, int pageNumber) throws Exception {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        getServices400();
    }

    private static Stream<Arguments> invalidPagination() {
        return Stream.of(Arguments.of(-1, 10),
                Arguments.of(1, -1),
                Arguments.of(0, 10),
                Arguments.of(-1, -1),
                Arguments.of(0, -1));
    }

    @Override
    public void updateService200() throws Exception {
        HttpResponse<ServiceVO> updatedService = testClient.updateService(theService.getId(), theService);
        assertEquals(HttpStatus.OK, updatedService.status(), "The service should have been updated withst status OK.");
        assertEquals(theService, updatedService.body(), "The service should have been updated with an equal object.");
    }

    @ParameterizedTest
    @MethodSource("validServices")
    public void updateService200(ServiceVO serviceVO) throws Exception {
        HttpResponse<?> initialCreate = testClient.createService(ServiceVOTestExample.build());
        assertEquals(HttpStatus.CREATED, initialCreate.status(), "The creation should have been succeeded.");
        // id is not allowed to be updated
        theService = serviceVO.id("packet-delivery-service");
        updateService200();
    }

    @Override
    public void updateService400() throws Exception {
        try {
            testClient.updateService(theService.getId(), theService);
        } catch (HttpClientResponseException e) {
            assertEquals(HttpStatus.BAD_REQUEST, e.getStatus(), "Invalid services should be rejected.");
            return;
        }
        fail("Invalid services should be rejected.");
    }

    @ParameterizedTest
    @MethodSource("invalidServices")
    public void updateService400(ServiceVO serviceVO) throws Exception {
        HttpResponse<?> initialCreate = testClient.createService(ServiceVOTestExample.build());
        assertEquals(HttpStatus.CREATED, initialCreate.status(), "The creation should have been succeeded.");
        theService = serviceVO;
        updateService400();
    }

    @Test
    @Override
    public void updateService404() throws Exception {
        ServiceVO serviceVO = ServiceVOTestExample.build();
        assertEquals(HttpStatus.NOT_FOUND, testClient.updateService(serviceVO.getId(), serviceVO).status(),
                "Only existing services can be updated.");
    }
}