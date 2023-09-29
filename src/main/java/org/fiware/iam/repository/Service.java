package org.fiware.iam.repository;

import io.micronaut.core.annotation.Introspected;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Collection;

/**
 * Data entity representing a service
 */
@Introspected
@Accessors(chain = true)
@Data
@Entity
@EqualsAndHashCode
public class Service {

	@Id
	private String id;

	private String defaultOidcScope;

	@OneToMany(mappedBy = "service", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private Collection<ServiceScope> oidcScopes;
}
