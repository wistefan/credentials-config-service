package org.fiware.iam.ccs.model;

import org.fiware.iam.ccs.model.ServiceVO;

public class ServiceVOTestExample {

	public static ServiceVO build() {
		ServiceVO exampleInstance = new ServiceVO();
		exampleInstance.setId("packet-delivery-service");
		exampleInstance.setDefaultOidcScope("default");
		exampleInstance.setOidcScopes(null);
		return exampleInstance;
	}
}
