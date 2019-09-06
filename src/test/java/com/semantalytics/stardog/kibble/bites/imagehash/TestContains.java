package com.semantalytics.stardog.kibble.bites.imagehash;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.*;
import org.openrdf.query.BindingSet;

import static org.junit.Assert.*;

public class TestContains extends AbstractStardogTest {

    @Test
    public void testTrue() {
    
        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:contains(\"Stardog\", \"dog\") AS ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final boolean aValue = Boolean.parseBoolean(aResult.next().value("result").stringValue());

                assertEquals(true, aValue);
                assertFalse("Should have no more results", aResult.hasNext());
            }
    }

    @Test
    public void testFalse() {

        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                "select ?result where { bind(string:contains(\"Stardog\", \"graph\") AS ?result) }";

        try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final boolean aValue = Boolean.parseBoolean(aResult.next().value("result").stringValue());

            assertEquals(false, aValue);
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testEmptyString() {
     
        final String aQuery = StringVocabulary.sparqlPrefix("string") +
            "select ?result where { bind(string:contains(\"\", \"\") as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final boolean aValue = Boolean.parseBoolean(aResult.next().value("result").stringValue());

                assertEquals(true, aValue);
                assertFalse("Should have no more results", aResult.hasNext());
            }
    }

    @Test
    public void testTooFewArgs() {

     
        final String aQuery = StringVocabulary.sparqlPrefix("string") +
            "select ?result where { bind(string:contains(\"one\") as ?result) }";

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
            "select ?result where { bind(string:contains(\"one\", \"two\", \"three\") as ?result) }";

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
            "select ?result where { bind(string:contains(1, \"two\") as ?result) }";

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
            "select ?result where { bind(string:contains(\"one\", 2) as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());
                assertFalse("Should have no more results", aResult.hasNext());
            }
    }
}
