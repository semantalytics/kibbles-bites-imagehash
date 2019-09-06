package com.semantalytics.stardog.kibble.bites.imagehash;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.*;
import org.openrdf.query.BindingSet;

import static org.junit.Assert.*;

public class TestCaseFormat extends AbstractStardogTest {
 
    @Test
    public void testLowerCamelToUpperUnderscoreByExample() {
       
        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?caseFormat where { bind(string:caseFormat(\"stardogUnion\", \"fromFormat\", \"TO_FORMAT\") as ?caseFormat) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final String aValue = aResult.next().value("caseFormat").stringValue();

                assertEquals("STARDOG_UNION", aValue);
                assertFalse("Should have no more results", aResult.hasNext());
            }
    }

    @Test
    public void testLowerCamelToLowerUnderscoreByExample() {
     
        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?caseFormat where { bind(string:caseFormat(\"stardogUnion\", \"fromFormat\", \"to_format\") as ?caseFormat) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final String aValue = aResult.next().value("caseFormat").stringValue();

                assertEquals("stardog_union", aValue);
                assertFalse("Should have no more results", aResult.hasNext());
            }
    }

    @Test
    public void testLowerCamelToLowerHyphenByExample() {
       
        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?caseFormat where { bind(string:caseFormat(\"stardogUnion\", \"fromFormat\", \"to-format\") as ?caseFormat) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {
           
                assertTrue("Should have a result", aResult.hasNext());

                final String aValue = aResult.next().value("caseFormat").stringValue();

                assertEquals("stardog-union", aValue);
                assertFalse("Should have no more results", aResult.hasNext());
            }
    }

    @Test
    public void testLowerCamelToUpperCamelByExample() {
    
        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?caseFormat where { bind(string:caseFormat(\"stardogUnion\", \"fromFormat\", \"ToFormat\") as ?caseFormat) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {
      
                assertTrue("Should have a result", aResult.hasNext());

                final String aValue = aResult.next().value("caseFormat").stringValue();

                assertEquals("StardogUnion", aValue);
                assertFalse("Should have no more results", aResult.hasNext());
            }
    }

    @Test
    public void testTooManyArgs() {
      
        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?caseFormat where { bind(string:caseFormat(\"one\", \"two\", \"three\", \"four\") as ?caseFormat) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());
                assertFalse("Should have no more results", aResult.hasNext());
            }
    }

    @Test
    public void testWrongType() {
    
        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?caseFormat where { bind(string:caseFormat(7, 8, 9) as ?caseFormat) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());
                assertFalse("Should have no more results", aResult.hasNext());
            }
    }
}
