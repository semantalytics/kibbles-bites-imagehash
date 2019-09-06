
package com.semantalytics.stardog.kibble.bites.imagehash;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.Test;
import org.openrdf.query.BindingSet;

import static org.junit.Assert.*;

public class TestEqualsAny extends AbstractStardogTest {

    @Test
    public void testTrue() {
   
            final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:equalsAny(\"Stardog\", \"Stardog\", \"graph\", \"database\") AS ?result) }";

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
                    "select ?result where { bind(string:equalsAny(\"Stardog\", \"graph\", \"database\") as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {
        
                assertTrue("Should have a result", aResult.hasNext());

                final boolean aValue = Boolean.parseBoolean(aResult.next().value("result").stringValue());

                assertEquals(false, aValue);
                assertFalse("Should have no more results", aResult.hasNext());
            }
    }

    @Test
    public void testTooFewArgs() {

            final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:equalsAny(\"one\") as ?result) }";

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
                    "select ?result where { bind(string:equalsAny(1, \"two\") as ?result) }";

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
                    "select ?result where { bind(string:equalsAny(\"one\", 2) as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {
       
                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());
                assertFalse("Should have no more results", aResult.hasNext());
            }
    }
}
