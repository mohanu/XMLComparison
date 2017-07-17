package com.xml.compare;

import java.io.FileReader;
import java.io.Reader;
import java.util.Iterator;
import java.util.List;

import javax.xml.transform.stream.StreamSource;

import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.diff.DefaultNodeMatcher;
import org.xmlunit.diff.Diff;
import org.xmlunit.diff.Difference;
import org.xmlunit.diff.ElementSelector;
import org.xmlunit.diff.ElementSelectors;

import com.xml.compare.es.ElementSelectorsIF;
import com.xml.compare.es.MyElementSelectors;

public class CompareXMLFiles {

	public int execute(String controlFile, String testFile) {
		// Default to MSIElementSelector
		return execute(controlFile, testFile, new MyElementSelectors());
	}

	public int execute(String controlFile, String testFile, ElementSelectorsIF es) {
		// Build ElementSelectors
		/*if (es == null)
			es = new MSIElementSelectors();*/

		List<ElementSelector> esList = es.build();

		// Perform Diff
		Diff diff = generateDifferences(controlFile, testFile, esList);

		// Print and output Differences
		return printDifferences(diff);
	}

	private Diff generateDifferences(String controlFile, String testFile, List<ElementSelector> esList) {
		Diff diff = null;
		try (Reader fr1 = new FileReader(controlFile); Reader fr2 = new FileReader(testFile)) {

			if (esList.isEmpty()) {
				diff = DiffBuilder.compare(new StreamSource(fr1)).withTest(new StreamSource(fr2)).ignoreComments()
						.ignoreWhitespace().normalizeWhitespace().checkForSimilar().build();
			} else {
				ElementSelector[] esArr = new ElementSelector[esList.size()];
				esArr = esList.toArray(esArr);

				DefaultNodeMatcher matcher = new DefaultNodeMatcher(ElementSelectors.and(esArr));
				
				diff = DiffBuilder.compare(new StreamSource(fr1)).withTest(new StreamSource(fr2)).ignoreComments()
						.ignoreWhitespace().normalizeWhitespace().checkForSimilar().withNodeMatcher(matcher).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return diff;
	}
	
	private int printDifferences(Diff diff){
		Iterator<Difference> iter = diff.getDifferences().iterator();
		int size = 0;
		while (iter.hasNext()) {
			Difference dif = iter.next();
			System.out.println(dif.toString());
			size++;
		}
		System.out.println("---------------------------------");
		System.out.println("Total Differences="+ size);
		System.out.println("---------------------------------");
		return size;
	}
}
