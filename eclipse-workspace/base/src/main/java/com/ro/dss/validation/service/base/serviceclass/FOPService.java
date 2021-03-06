/*
 * 
 * ##src for this service from the open-source repo
 * https://github.com/esig/dss-demonstrations/blob/master/dss-demo-webapp/src/main/java/eu/europa/esig/dss/web/service/FOPService.java
 * 
 */


package com.ro.dss.validation.service.base.serviceclass;

import java.io.File;
import java.io.OutputStream;

import javax.annotation.PostConstruct;
import javax.xml.transform.Result;
import javax.xml.transform.sax.SAXResult;

import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.FopFactoryBuilder;
import org.apache.fop.apps.MimeConstants;
import org.springframework.stereotype.Component;

import eu.europa.esig.dss.detailedreport.DetailedReportFacade;
import eu.europa.esig.dss.simplereport.SimpleReportFacade;


@Component
public class FOPService {

	private FopFactory fopFactory;
	private FOUserAgent foUserAgent;

	@PostConstruct
	public void init() throws Exception {
		FopFactoryBuilder builder = new FopFactoryBuilder(new File(".").toURI());
		builder.setAccessibility(true);

		fopFactory = builder.build();

		foUserAgent = fopFactory.newFOUserAgent();
		foUserAgent.setCreator("DSS Validation Service");
		foUserAgent.setAccessibility(true);
	}

	public void generateSimpleReport(String simpleReport, OutputStream os) throws Exception {
		Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, os);
		Result result = new SAXResult(fop.getDefaultHandler());
		SimpleReportFacade.newFacade().generatePdfReport(simpleReport, result);
	}

	public void generateDetailedReport(String detailedReport, OutputStream os) throws Exception {
		Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, os);
		Result result = new SAXResult(fop.getDefaultHandler());
		DetailedReportFacade.newFacade().generatePdfReport(detailedReport, result);
	}

}
