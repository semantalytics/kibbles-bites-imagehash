package com.semantalytics.stardog.kibble.bites.imagehash;

import com.complexible.common.rdf.StatementSource;
import com.complexible.common.rdf.StatementSources;
import com.complexible.stardog.StardogException;
import com.complexible.stardog.api.Connection;
import com.complexible.stardog.docs.BitesException;
import com.complexible.stardog.docs.extraction.RDFExtractor;
import com.github.kilianB.hash.Hash;
import com.github.kilianB.hashAlgorithms.HashingAlgorithm;
import com.github.kilianB.hashAlgorithms.PerceptiveHash;
import com.stardog.stark.IRI;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;

public class RotHashExtractor implements RDFExtractor {

    private final HashingAlgorithm hasher = new PerceptiveHash(32);

    @Override
    public StatementSource extract(Connection theConnection, IRI theDocIri, Path theDocContents) throws BitesException {
        try {
            if (theDocContents.toFile().length() > 0L) {
                Hash imageHash = hasher.hash(theDocContents.toFile());
                return this.extractFromText(theConnection, theDocIri, aText);
            } else {
                return StatementSources.empty();
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } catch (Exception e) {
            throw new StardogException(e);
        }
    }

}
