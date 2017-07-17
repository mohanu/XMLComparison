package com.xml.compare;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

import com.xml.compare.es.MyElementSelectors;

public class TestCompareXMLFiles1 {
	
	@Test
	public void testCompareSameFiles() {
		CompareXMLFiles xmlCompare = new CompareXMLFiles();
		int diffCount = xmlCompare.execute("resources" + File.separator + "ControlFile.xml",
				"resources" + File.separator + "TestFile.xml", new MyElementSelectors());
		assertEquals(0, diffCount);
	}
	
	@Test
	public void testCompareSameUndoredCharacter() {
		CompareXMLFiles xmlCompare = new CompareXMLFiles();
		int diffCount = xmlCompare.execute("resources" + File.separator + "ControlFile.xml",
				"resources" + File.separator + "TestFileUnorderedCharacter.xml", new MyElementSelectors());
		assertEquals(0, diffCount);

	}

	@Test
	public void testCompareSameSupplierAndCharUnordered() {
		CompareXMLFiles xmlCompare = new CompareXMLFiles();
		int diffCount = xmlCompare.execute("resources" + File.separator + "ControlFile.xml",
				"resources" + File.separator + "TestFileUndorderedSupplierAndChar.xml", new MyElementSelectors());
		assertEquals(0, diffCount);
	}
	
	@Test
	public void testCompareDiffSupplierAndCharUnordered() {
		CompareXMLFiles xmlCompare = new CompareXMLFiles();
		int diffCount = xmlCompare.execute("resources" + File.separator + "ControlFile.xml",
				"resources" + File.separator + "TestFileUndorderedSupplierAndCharDiffs.xml", new MyElementSelectors());
		assertEquals(2, diffCount);
	}
	
}