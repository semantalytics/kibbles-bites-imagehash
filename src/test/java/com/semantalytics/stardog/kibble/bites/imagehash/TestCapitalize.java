package com.semantalytics.stardog.kibble.bites.imagehash;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.*;
import org.openrdf.query.BindingSet;

import static org.junit.Assert.*;

public class TestCapitalize extends AbstractStardogTest {
   
    @Test
    public void testNotCapitalized() {
     
         final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:capitalize(\"stardog\") AS ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final String aValue = aResult.next().value("result").stringValue();

                assertEquals("Stardog", aValue);

                assertFalse("Should have no more results", aResult.hasNext());
            }
    }

    @Test
    public void testCapitalized() {
   
         final String aQuery = StringVocabulary.sparqlPrefix("string") +
               "select ?result where { bind(string:capitalize(\"Stardog\") AS ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final String aValue = aResult.next().value("result").stringValue();

                assertEquals("Stardog", aValue);

                assertFalse("Should have no more results", aResult.hasNext());
            }
    }

    @Test
    public void testAllCaps() {
   
         final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:capitalize(\"STARDOG\") AS ?result) }";


            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final String aValue = aResult.next().value("result").stringValue();

                assertEquals("STARDOG", aValue);

                assertFalse("Should have no more results", aResult.hasNext());
            }
    }

    @Test
    public void testEmptyString() {
     
         final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:capitalize(\"\") as ?result) }";

            final SelectQueryResult aResult = connection.select(aQuery).execute();

                assertTrue("Should have a result", aResult.hasNext());

                final String aValue = aResult.next().value("result").stringValue();

                assertEquals("", aValue);

                assertFalse("Should have no more results", aResult.hasNext());
    }

    @Test
    public void testTooFewArgs() {

         final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:capitalize() as ?result) }";

            final SelectQueryResult aResult = connection.select(aQuery).execute();
    
                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());

                assertFalse("Should have no more results", aResult.hasNext());
    }

    @Test
    public void testTooManyArgs() {

         final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?capitalize where { bind(string:capitalize(\"one\", \"two\") as ?result) }";

            final SelectQueryResult aResult = connection.select(aQuery).execute();

                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());

                assertFalse("Should have no more results", aResult.hasNext());
    }

    @Test
    public void testArgumentWrongType() {
     
         final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:capitalize(1) as ?result) }";

            final SelectQueryResult aResult = connection.select(aQuery).execute();
         
                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());

                assertFalse("Should have no more results", aResult.hasNext());
    }
}
