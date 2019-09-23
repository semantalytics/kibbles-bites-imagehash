package com.semantalytics.stardog.kibble.bites.imagehash;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.Constant;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.IRI;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import java.nio.file.Paths;

public class HammingDistance extends AbstractFunction extends StringFunction {

    public HammingDistance() {
        super(2, ImagehashVocabulary.hammingDistance.toString());
    }

    public HammingDistance(HammingDistance hammingDistance) {
        super(hammingDistance);
    }

    @Override
    protected ValueOrError internalEvaluate(Value... values) {
        if(assertIRI(values[0]) && values[0] instanceof Constant && assertIRI(values[1])) {
            Paths.get(((IRI)values[0]).toString()).toFile();
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Function copy() {
        return new HammingDistance(this);
    }

    @Override
    public void accept(ExpressionVisitor expressionVisitor) {

    }
}
