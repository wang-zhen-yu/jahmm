/*
 * Copyright (c) 2004-2009, Jean-Marc François. All Rights Reserved.
 * Licensed under the New BSD license.  See the LICENSE file.
 */
package jahmm.observables;

/**
 * This class can build <code>OpdfMultiGaussian</code> observation probability
 * functions.
 */
public final class OpdfGaussianFactory implements OpdfFactory<OpdfGaussian> {

    public static final OpdfGaussianFactory Instance = new OpdfGaussianFactory();

    private OpdfGaussianFactory() {
    }

    @Override
    public OpdfGaussian generate() {
        return new OpdfGaussian();
    }
}
