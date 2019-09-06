package com.semantalytics.stardog.kibble.bites.imagehash;

import com.stardog.stark.IRI;
import com.stardog.stark.Values;

public enum ImagehashVocabulary {

    averageColorHash,
    averageHash,
    averageKernelHash,
    differenceHash,
    medianHash,
    perceptiveHash,
    rotHash,
    waveletHash;

    public static final String NAMESPACE = "http://semantalytics.com/2019/09/ns/stardog/kibble/bites/imagehash/";
    public final IRI iri;

    ImagehashVocabulary() {
        iri = Values.iri(NAMESPACE, name());
    }

    public static String sparqlPrefix(String prefixName) {
        return "PREFIX " + prefixName + ": <" + NAMESPACE + "> ";
    }

    @Override
    public String toString() {
        return iri.toString();
    }
}
