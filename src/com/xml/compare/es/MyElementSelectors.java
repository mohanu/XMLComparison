package com.xml.compare.es;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.xmlunit.diff.ElementSelector;
import org.xmlunit.diff.ElementSelectors;

import com.xml.compare.util.XMLUtility;

public class MyElementSelectors implements ElementSelectorsIF {

	@Override
	public List<ElementSelector> build() {
		List<ElementSelector> esList = new ArrayList<>();
		//Add namespaces for XPath Searches
		//Map<String, String> prefix2Uri = XMLUtility.addXMLNSforMSI();
		Map<String, String> prefix2Uri = null;

		// Name Value pairs
		ElementSelector esChar = ElementSelectors.conditionalBuilder()
				.whenElementIsNamed("character")
				.thenUse(ElementSelectors.byXPath("./name", prefix2Uri, ElementSelectors.byNameAndText))
				.elseUse(ElementSelectors.byName).build();
		esList.add(esChar);

		// Complex nodes
		ElementSelector esSSId = ElementSelectors.conditionalBuilder().whenElementIsNamed("supplierService")
				.thenUse(
						ElementSelectors.byXPath("./id", prefix2Uri,
								ElementSelectors.and(ElementSelectors.byNameAndText, esChar)))
				.elseUse(ElementSelectors.byName).build();
		esList.add(esSSId);

		//returns list
		return esList;
	}

}
