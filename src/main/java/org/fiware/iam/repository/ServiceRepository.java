package org.fiware.iam.repository;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.annotation.Join;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.repository.PageableRepository;

import java.util.Optional;

/**
 * Extension of the base repository to support {@link Service}
 */
public interface ServiceRepository extends PageableRepository<Service, String> {

	/**
	 * Find services by their ID. All child values will be returned through left-joins to fill out the full entity.
	 *
	 * @param id of the service
	 * @return the complete service
	 */
	@Join(value = "credentials", type = Join.Type.LEFT_FETCH)
	@Join(value = "credentials.trustedIssuersLists", type = Join.Type.LEFT_FETCH)
	@Join(value = "credentials.trustedParticipantsLists", type = Join.Type.LEFT_FETCH)
	Service getById(String id);

	/**
	 * Get all services. All child values will be returned through left-joins to fill out the full entity.
	 *
	 * @param pageable pagination information to be used
	 * @return the current page
	 */
	@NonNull
	@Join(value = "credentials", type = Join.Type.LEFT_FETCH)
	@Join(value = "credentials.trustedIssuersLists", type = Join.Type.LEFT_FETCH)
	@Join(value = "credentials.trustedParticipantsLists", type = Join.Type.LEFT_FETCH)
	Page<Service> findAll(@NonNull Pageable pageable);
}

