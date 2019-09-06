package com.semantalytics.stardog.kibble.bites.imagehash;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.*;
import org.openrdf.query.BindingSet;

import static org.junit.Assert.*;

public class TestDigits extends AbstractStardogTest {

    @Test
    public void testGetDigits() {
   
            final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:digits(\"Stard0g\") AS ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final String aValue = aResult.next().value("result").stringValue();

                assertEquals("0", aValue);

                assertFalse("Should have no more results", aResult.hasNext());
            }
    }

    @Test
    public void testOnlyDigits() {
       

            final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:digits(\"12345\") AS ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final String aValue = aResult.next().value("result").stringValue();

                assertEquals("12345", aValue);
                assertFalse("Should have no more results", aResult.hasNext());
            } 
    }
  
    @Test
    public void testEmptyString() {
       
            final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:digits(\"\") as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {
        
                assertTrue("Should have a result", aResult.hasNext());

                final String aValue = aResult.next().value("result").stringValue();

                assertEquals("", aValue);
                assertFalse("Should have no more results", aResult.hasNext());
            }
    }
  
    @Test
    public void testNonAscii() {
       
            final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:digits(\"१२३\") as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

        
                assertTrue("Should have a result", aResult.hasNext());

                final String aValue = aResult.next().value("result").stringValue();

                assertEquals("१२३", aValue);
                assertFalse("Should have no more results", aResult.hasNext());
            }
    }

    @Test
    public void testTooFewArgs() {

       
            final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:digits() as ?result) }";

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
                    "select ?result where { bind(string:digits(\"Stardog\", \"one\") as ?result) }";

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
                    "select ?result where { bind(string:digits(4) as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {
       
                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());
                assertFalse("Should have no more results", aResult.hasNext());
            }
    }
}
