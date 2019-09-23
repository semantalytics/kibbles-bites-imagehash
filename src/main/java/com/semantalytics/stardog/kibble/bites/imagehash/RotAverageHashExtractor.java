package com.semantalytics.stardog.kibble.bites.imagehash;

import com.complexible.common.rdf.StatementSource;
import com.complexible.common.rdf.StatementSources;
import com.complexible.common.rdf.impl.MemoryStatementSource;
import com.complexible.stardog.StardogException;
import com.complexible.stardog.api.Connection;
import com.complexible.stardog.docs.BitesException;
import com.complexible.stardog.docs.extraction.RDFExtractor;
import com.github.kilianB.hash.Hash;
import com.github.kilianB.hashAlgorithms.HashingAlgorithm;
import com.github.kilianB.hashAlgorithms.RotAverageHash;
import com.stardog.stark.IRI;
import com.stardog.stark.Statement;
import com.stardog.stark.Values;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class RotAverageHashExtractor implements RDFExtractor {

    private static final int BIT_RESOLUTION = 32;

    private final HashingAlgorithm hasher = new RotAverageHash(BIT_RESOLUTION);

    @Override
    public StatementSource extract(Connection theConnection, IRI theDocIri, Path theDocContents) throws BitesException {
        try {
            if (theDocContents.toFile().length() > 0L) {
                Hash imageHash = hasher.hash(theDocContents.toFile());
                Set<Statement> aModel = new HashSet();
                aModel.add(Values.statement(theDocIri, ImagehashVocabulary.rotAverageHash.iri, Values.literal(imageHash.getHashValue())));
                return MemoryStatementSource.of(aModel);
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
