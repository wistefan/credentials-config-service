package org.fiware.iam.repository;

import io.micronaut.core.annotation.Introspected;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Map;

public class ServiceScopes {

    @OneToMany(mappedBy = "serviceScopes", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Map<String, ServiceScopesEntry> additionalProperties;

}
