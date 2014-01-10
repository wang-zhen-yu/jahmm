/*
 * Copyright (c) 2004-2009, Jean-Marc François. All Rights Reserved.
 * Licensed under the New BSD license.  See the LICENSE file.
 */
package be.ac.ulg.montefiore.run.jahmm.io;

import be.ac.ulg.montefiore.run.jahmm.OpdfMultiGaussian;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;

/**
 * This class implements a {@link OpdfMultiGaussian} writer. It is compatible
 * with the {@link OpdfMultiGaussianReader} class.
 */
public class OpdfMultiGaussianWriter
        extends OpdfWriter<OpdfMultiGaussian> {

    @Override
    public void write(Writer writer, OpdfMultiGaussian opdf)
            throws IOException {
        writer.write("MultiGaussianOPDF [ ");
        write(writer, opdf.mean());
        writer.write(" [");
        for (double[] line : opdf.covariance()) {
            writer.write(" ");
            write(writer, line);
        }
        writer.write(" ] ]");
    }
    private static final Logger LOG = Logger.getLogger(OpdfMultiGaussianWriter.class.getName());
}