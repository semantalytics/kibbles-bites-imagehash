package com.semantalytics.stardog.kibble.bites.imagehash;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.*;
import org.openrdf.query.BindingSet;

import static org.junit.Assert.*;

public class TestIsBlank extends AbstractStardogTest {

    @Test
    public void testFalse() {

            final String aQuery = "prefix string: <" + StringVocabulary.NAMESPACE + "> " +
                    "select ?result where { bind(string:isBlank(\"Stardog\") AS ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final boolean aValue = Boolean.parseBoolean(aResult.next().value("result").stringValue());

                assertEquals(false, aValue);
                assertFalse("Should have no more results", aResult.hasNext());
            }
    }

    @Test
    public void testTrue() {

            final String aQuery = "prefix string: <" + StringVocabulary.NAMESPACE + "> " +
                    "select ?result where { bind(string:isBlank(\" \") as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {


                assertTrue("Should have a result", aResult.hasNext());

                final boolean aValue = Boolean.parseBoolean(aResult.next().value("result").stringValue());

                assertEquals(true, aValue);
                assertFalse("Should have no more results", aResult.hasNext());
            }
    }

    @Test
    public void testTooFewArgs() {

            final String aQuery = "prefix string: <" + StringVocabulary.NAMESPACE + "> " +
                    "select ?result where { bind(string:isBlank() as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());
                assertFalse("Should have no more results", aResult.hasNext());
            }
    }


    @Test
    public void testTooManyArgs() {

            final String aQuery = "prefix string: <" + StringVocabulary.NAMESPACE + "> " +
                    "select ?result where { bind(string:isBlank(\"one\", \"two\") as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());
                assertFalse("Should have no more results", aResult.hasNext());
            }
    }

    @Test
    public void testWrongTypeFirstArg() {
       
            final String aQuery = "prefix string: <" + StringVocabulary.NAMESPACE + "> " +
                    "select ?result where { bind(string:isBlank(1) as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());
                assertFalse("Should have no more results", aResult.hasNext());
            }
    }
}
