package com.xml.compare.util;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.transform.Source;

import org.w3c.dom.Node;
import org.xmlunit.builder.Input;
import org.xmlunit.util.Convert;
import org.xmlunit.xpath.JAXPXPathEngine;

public class XMLUtility {

	public static Map<String, String> addXMLNSforMSI() {
		Map<String, String> prefix2Uri = new LinkedHashMap<String, String>();
		prefix2Uri.put("env", "http://schemas.xmlsoap.org/soap/envelope/");
		return prefix2Uri;
	}

	public static String xPathEvaluate(String xPath, File input, Map<String, String> prefix2Uri) {
		try {
			JAXPXPathEngine engine = new JAXPXPathEngine();
			if (prefix2Uri != null) {
				engine.setNamespaceContext(prefix2Uri);
			}
			Source s = Input.fromFile(input).build();
			Node n = Convert.toNode(s);
			return engine.evaluate(xPath, n);
		} catch (Exception e) {
			return "";
		}
	}

}
