package com.semantalytics.stardog.kibble.bites.imagehash;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.*;
import org.openrdf.query.BindingSet;

import static org.junit.Assert.*;

public class TestAbbreviateWithMarker extends AbstractStardogTest {

  
    @Test
    public void testThreeArg() {
    
        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:abbreviateWithMarker(\"Stardog graph database\", \"***\" , 10) AS ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final String aValue = aResult.next().value("result").stringValue();

                assertEquals("Stardog***", aValue);
                assertFalse("Should have no more results", aResult.hasNext());
            }
    }

    @Test
    public void testFourArg() {
     
        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:abbreviateWithMarker(\"Stardog graph database\", \"***\", 10, 3) AS ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final String aValue = aResult.next().value("result").stringValue();

                assertEquals("Stardog***", aValue);
                assertFalse("Should have no more results", aResult.hasNext());
            }
    }

    @Test
    public void testEmptyString() {
      
        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?abbreviation where { bind(string:abbreviateWithMarker(\"\", \"***\", 5) as ?abbreviation) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {
    
                assertTrue("Should have a result", aResult.hasNext());

                final String aValue = aResult.next().value("abbreviation").stringValue();

                assertEquals("", aValue);
                assertFalse("Should have no more results", aResult.hasNext());
            }
    }

    @Test
    public void testTooFewArgs() {

        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?abbreviation where { bind(string:abbreviateWithMarker(\"one\") as ?abbreviation) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {
       
                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());
                assertFalse("Should have no more results", aResult.hasNext());
            }
    }


    @Test
    public void testTooManyArgs() {

        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?abbreviation where { bind(string:abbreviateWithMarker(\"one\", 2, \"three\") as ?abbreviation) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {
  
                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());
                assertFalse("Should have no more results", aResult.hasNext());
            }
    }

    @Test
    public void testWrongTypeFirstArg() {
  
        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?abbreviation where { bind(string:abbreviateWithMarker(4, 5) as ?abbreviation) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {
    
                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());
                assertFalse("Should have no more results", aResult.hasNext());
            }
    }

    @Test
    public void testWrongTypeSecondArg() {
   
        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?abbreviation where { bind(string:abbreviateWithMarker(\"one\", \"two\") as ?abbreviation) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {
          
                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());
                assertFalse("Should have no more results", aResult.hasNext());
            }
    }

    @Test
    public void testLengthTooShort() {
    
        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?abbreviation where { bind(string:abbreviateWithMarker(\"Stardog\", 3) as ?abbreviation) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {
  
                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());
                assertFalse("Should have no more results", aResult.hasNext());
            }
    }
}
