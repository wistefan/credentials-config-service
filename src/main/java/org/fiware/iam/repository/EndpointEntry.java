package org.fiware.iam.repository;

import io.micronaut.core.annotation.Introspected;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.net.URL;

/**
 * Entity to represent a single endpoint for either the trusted-issuers-list or the trusted-participants-list
 */
@Introspected
@Data
@Accessors(chain = true)
@Entity
@EqualsAndHashCode(exclude = "credential")
@ToString(exclude = "credential")
public class EndpointEntry {

	@GeneratedValue
	@Id
	private Integer id;

	private EndpointType type;

	private String endpoint;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "credential_id")
	private Credential credential;
}
