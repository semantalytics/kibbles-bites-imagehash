package com.semantalytics.stardog.kibble.bites.imagehash;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.*;
import org.openrdf.query.BindingSet;

import static org.junit.Assert.*;

public class TestStripAccents extends AbstractStardogTest {

    @Test
    public void testOneArgumentWithoutAccents() {

        final String aQuery = StringVocabulary.sparqlPrefix("string") +
            "select ?result where { bind(string:stripAccents(\"Stardog\") AS ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final String aValue = aResult.next().value("result").stringValue();

                assertEquals("Stardog", aValue);
                assertFalse("Should have no more results", aResult.hasNext());
            }    
    }

    @Test
    public void testOneArgumentWithAccents() {
       
        final String aQuery = StringVocabulary.sparqlPrefix("string") +
            "select ?result where { bind(string:stripAccents(\"\\u00E9clair\") AS ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final String aValue = aResult.next().value("result").stringValue();

                assertEquals("eclair", aValue);
                assertFalse("Should have no more results", aResult.hasNext());
            }
    }

    @Test
    public void testEmptyString() {
       
        final String aQuery = StringVocabulary.sparqlPrefix("string") +
            "select ?result where { bind(string:stripAccents(\"\") AS ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final String aValue = aResult.next().value("result").stringValue();

                assertEquals("", aValue);
                assertFalse("Should have no more results", aResult.hasNext());
            }
    }

    @Test
    public void testTooFewArgs() {

         final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:stripAccents() as ?result) }";

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
             "select ?result where { bind(string:stripAccents(\"one\", \"two\") as ?result) }";

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
             "select ?result where { bind(string:stripAccents(4) as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());
                assertFalse("Should have no more results", aResult.hasNext());
            }
    }
}
