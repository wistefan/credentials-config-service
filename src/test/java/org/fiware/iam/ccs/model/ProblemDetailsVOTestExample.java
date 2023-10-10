package org.fiware.iam.ccs.model;

import org.fiware.iam.ccs.model.ProblemDetailsVO;

public class ProblemDetailsVOTestExample {

	public static ProblemDetailsVO build() {
		ProblemDetailsVO exampleInstance = new ProblemDetailsVO();
		exampleInstance.setType(java.net.URI.create("my:uri"));
		exampleInstance.setTitle("Internal Server Error");
		exampleInstance.setStatus(500);
		exampleInstance.setDetail("Connection timeout");
		exampleInstance.setInstance(java.net.URI.create("my:uri"));
		return exampleInstance;
	}
}
