package com.semantalytics.stardog.kibble.bites.imagehash;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.*;

import static org.junit.Assert.*;

public class TestAbbreviate extends AbstractStardogTest {

   
    @Test
    public void testAbbreviate() {
    
        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:abbreviate(\"Stardog graph database\", 8) AS ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final String aValue = aResult.next().value("result").get().toString();

                assertEquals("Stard...", aValue);

                assertFalse("Should have no more results", aResult.hasNext());
            }
    }

    @Test
    public void testAbbreviateWithOffset() {
  
        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:abbreviate(\"Stardog graph database\", 15, 5) AS ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final String aValue = aResult.next().value("result").get().toString();

                assertEquals("...og graph ...", aValue);
                assertFalse("Should have no more results", aResult.hasNext());
            }
    }

    @Test
    public void testEmptyString() {
  
        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:abbreviate(\"\", 5) as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final String aValue = aResult.next().value("result").get().toString();

                assertEquals("", aValue);
                assertFalse("Should have no more results", aResult.hasNext());
            }     
    }

    @Test
    public void testTooFewArgs() {
    
        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:abbreviate(\"one\") as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertEquals("Should have no bindings", 0, aBindingSet.size());
                assertFalse("Should have no more results", aResult.hasNext());
            }
    }

    @Test
    public void testTooManyArgs() {

        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:abbreviate(\"one\", 9, \"three\", \"four\") as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {
       
                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertEquals("Should have no bindings", 0, aBindingSet.size());
                assertFalse("Should have no more results", aResult.hasNext());
            }
    }

    @Test
    public void testWrongTypeFirstArg() {
     
        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:abbreviate(4, 5) as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {
     
                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertEquals("Should have no bindings", 0, aBindingSet.size());
                assertFalse("Should have no more results", aResult.hasNext());
            }
    }

    @Test
    public void testWrongTypeSecondArg() {

        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:abbreviate(\"one\", \"two\") as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {
   
                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertEquals("Should have no bindings", 0, aBindingSet.size());
                assertFalse("Should have no more results", aResult.hasNext());
            }
    }

    @Test
    public void testWrongTypeThirdArg() {
 
        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:abbreviate(\"one\", 9, \"three\") as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertEquals("Should have no bindings", 0, aBindingSet.size());
                assertFalse("Should have no more results", aResult.hasNext());
            }
    }

    @Test
    public void testLengthTooShort() {
     
        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:abbreviate(\"Stardog\", 3) as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertEquals("Should have no bindings", 0, aBindingSet.size());
                assertFalse("Should have no more results", aResult.hasNext());
            }
    }
}
