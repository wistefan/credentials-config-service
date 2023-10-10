package org.fiware.iam.ccs.model;

@jakarta.annotation.Generated("org.openapitools.codegen.languages.MicronautCodegen")
@io.micronaut.core.annotation.Introspected
public class ServicesVO {

	public static final String JSON_PROPERTY_TOTAL = "total";
	public static final String JSON_PROPERTY_PAGE_NUMBER = "pageNumber";
	public static final String JSON_PROPERTY_PAGE_SIZE = "pageSize";
	public static final String JSON_PROPERTY_SERVICES = "services";

	/** Total number of services available */
	@com.fasterxml.jackson.annotation.JsonProperty(JSON_PROPERTY_TOTAL)
	@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
	private Integer total;

	/** Number of the page to be retrieved. */
	@com.fasterxml.jackson.annotation.JsonProperty(JSON_PROPERTY_PAGE_NUMBER)
	@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
	private Integer pageNumber;

	/** Size of the returend page, can be less than the requested depending on the available entries */
	@com.fasterxml.jackson.annotation.JsonProperty(JSON_PROPERTY_PAGE_SIZE)
	@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
	private Integer pageSize;

	/** The list of services */
	@com.fasterxml.jackson.annotation.JsonProperty(JSON_PROPERTY_SERVICES)
	@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
	private java.util.List<ServiceVO> services;

	// methods

	@Override
	public boolean equals(Object object) {
		if (object == this) {
			return true;
		}
		if (object == null || getClass() != object.getClass()) {
			return false;
		}
		ServicesVO other = (ServicesVO) object;
		return java.util.Objects.equals(total, other.total)
				&& java.util.Objects.equals(pageNumber, other.pageNumber)
				&& java.util.Objects.equals(pageSize, other.pageSize)
				&& java.util.Objects.equals(services, other.services);
	}

	@Override
	public int hashCode() {
		return java.util.Objects.hash(total, pageNumber, pageSize, services);
	}

	@Override
	public String toString() {
		return new StringBuilder()
				.append("ServicesVO[")
				.append("total=").append(total).append(",")
				.append("pageNumber=").append(pageNumber).append(",")
				.append("pageSize=").append(pageSize).append(",")
				.append("services=").append(services)
				.append("]")
				.toString();
	}

	// fluent

	public ServicesVO total(Integer newTotal) {
		this.total = newTotal;
		return this;
	}

	public ServicesVO pageNumber(Integer newPageNumber) {
		this.pageNumber = newPageNumber;
		return this;
	}

	public ServicesVO pageSize(Integer newPageSize) {
		this.pageSize = newPageSize;
		return this;
	}

	public ServicesVO services(java.util.List<ServiceVO> newServices) {
		this.services = newServices;
		return this;
	}
	
	public ServicesVO addServicesItem(ServiceVO servicesItem) {
		if (this.services == null) {
			this.services = new java.util.ArrayList<>();
		}
		this.services.add(servicesItem);
		return this;
	}

	public ServicesVO removeServicesItem(ServiceVO servicesItem) {
		if (this.services != null) {
			this.services.remove(servicesItem);
		}
		return this;
	}

	// getter/setter

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer newTotal) {
		this.total = newTotal;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer newPageNumber) {
		this.pageNumber = newPageNumber;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer newPageSize) {
		this.pageSize = newPageSize;
	}

	public java.util.List<ServiceVO> getServices() {
		return services;
	}

	public void setServices(java.util.List<ServiceVO> newServices) {
		this.services = newServices;
	}
}
