package org.fiware.iam.rest;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.Sort;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fiware.iam.ServiceMapper;
import org.fiware.iam.ccs.api.ServiceApi;
import org.fiware.iam.ccs.model.CredentialVO;
import org.fiware.iam.ccs.model.ScopeVO;
import org.fiware.iam.ccs.model.ServiceVO;
import org.fiware.iam.ccs.model.ServicesVO;
import org.fiware.iam.exception.ConflictException;
import org.fiware.iam.repository.Service;
import org.fiware.iam.repository.ServiceRepository;

import java.net.URI;
import java.util.Optional;

/**
 * Implementation of the service api to configure services and there credentials
 */
@Slf4j
@Controller("${general.basepath:/}")
@RequiredArgsConstructor
@Introspected
public class ServiceApiController implements ServiceApi {

	private final ServiceRepository serviceRepository;
	private final ServiceMapper serviceMapper;

	@Override
	public HttpResponse<Object> createService(@NonNull ServiceVO serviceVO) {
		if (serviceVO.getId() != null && serviceRepository.existsById(serviceVO.getId())) {
			throw new ConflictException(String.format("The service with id %s already exists.", serviceVO.getId()),
					serviceVO.getId());
		}
		return HttpResponse.created(
				URI.create(
						ServiceApi.PATH_GET_SERVICE.replace(
								"{id}",
								serviceRepository.save(
										serviceMapper.map(serviceVO)).getId())));
	}

	@Override public HttpResponse<Object> deleteServiceById(@NonNull String id) {
		if (!serviceRepository.existsById(id)) {
			return HttpResponse.notFound();
		}
		serviceRepository.deleteById(id);
		return HttpResponse.noContent();
	}

	@Override public HttpResponse<ScopeVO> getScopeForService(@NonNull String id) {
		if (!serviceRepository.existsById(id)) {
			return HttpResponse.notFound();
		}
		ScopeVO scopeVO = new ScopeVO();
		scopeVO.addAll(
				serviceMapper.map(serviceRepository.getById(id))
						.getCredentials()
						.stream()
						.map(CredentialVO::getType)
						.toList());
		return HttpResponse.ok(scopeVO);

	}

	@Override public HttpResponse<ServiceVO> getService(@NonNull String id) {
		if (!serviceRepository.existsById(id)) {
			return HttpResponse.notFound();
		}
		return HttpResponse.ok(serviceMapper.map(serviceRepository.getById(id)));
	}

	@Override public HttpResponse<ServicesVO> getServices(@Nullable Integer nullablePageSize,
			@Nullable Integer nullableOffset) {
		var pageSize = Optional.ofNullable(nullablePageSize).orElse(100);
		var offset = Optional.ofNullable(nullableOffset).orElse(0);
		if (pageSize < 1) {
			throw new IllegalArgumentException("PageSize has to be at least 1.");
		}
		if (offset < 0) {
			throw new IllegalArgumentException("Offsets below 0 are not supported.");
		}
		Page<Service> requestedPage = serviceRepository.findAll(
				Pageable.from(offset, pageSize, Sort.of(Sort.Order.asc("id"))));
		return HttpResponse.ok(
				new ServicesVO()
						.total((int) requestedPage.getTotalSize())
						.offset(offset)
						.pageSize(requestedPage.getSize())
						.services(requestedPage.getContent().stream().map(serviceMapper::map).toList()));
	}

	@Override public HttpResponse<ServiceVO> updateService(@NonNull String id, @NonNull ServiceVO serviceVO) {
		if (serviceVO.getId() != null && !id.equals(serviceVO.getId())) {
			throw new IllegalArgumentException("The id of a service cannot be updated.");
		}
		if (!serviceRepository.existsById(id)) {
			return HttpResponse.notFound();
		}
		// just in case none is set in the object
		serviceVO.setId(id);
		return HttpResponse.ok(
				serviceMapper.map(serviceRepository.update(serviceMapper.map(serviceVO))));
	}
}
