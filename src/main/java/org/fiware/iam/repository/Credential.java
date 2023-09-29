package org.fiware.iam.repository;

import io.micronaut.core.annotation.Introspected;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Data entity to map a credential
 */
@Introspected
@Data
@Accessors(chain = true)
@Entity
@EqualsAndHashCode(exclude = "serviceScope")
@ToString(exclude = "serviceScope")
public class Credential {

	@GeneratedValue
	@Id
	private Integer id;

	private String credentialType;

	@OneToMany(mappedBy = "credential", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<EndpointEntry> trustedLists;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "service_scope_id")
	private ServiceScope serviceScope;




}
