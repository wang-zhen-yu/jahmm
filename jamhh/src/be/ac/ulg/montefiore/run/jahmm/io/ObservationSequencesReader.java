/*
 * Copyright (c) 2004-2009, Jean-Marc François. All Rights Reserved.
 * Licensed under the New BSD license.  See the LICENSE file.
 */
package be.ac.ulg.montefiore.run.jahmm.io;

import be.ac.ulg.montefiore.run.jahmm.Observation;
import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * This class can read observations sequences from file.
 * <p>
 * The file format has been chosen to be very simple:
 * <ul>
 * <li> a line per observation sequence, in pure 7 bits ASCII;</li>
 * <li> empty (white) lines, space and tab characters are not significant;</li>
 * <li> each observation is followed by a semi-colon (<i>i.e.</i> the line ends
 * with a semi-colon);</li>
 * <li> The '#' character introduce a comment; the rest of the line is skipped;
 * </li>
 * <li> A newline can be escaped using the '\' character; this character can't
 * be used in any other context;</li>
 * <li> the format of each observation is defined by the corresponding IO
 * class.</li>
 * </ul>
 * <p>
 * Those rules must be followed by {@link ObservationReader ObservationReader}
 * subclasses.
 */
public class ObservationSequencesReader {

    /**
     * Reads observation sequences file. Such a file holds a set of observation
     * sequences.
     *
     * @param or An observation reader.
     * @param reader Holds the character stream reader the sequences are read
     * from.
     * @return A {@link java.util.Vector Vector} of
     * {@link java.util.Vector Vector}s of
     * {@link be.ac.ulg.montefiore.run.jahmm.Observation Observation}s.
     * @throws be.ac.ulg.montefiore.run.jahmm.io.FileFormatException
     */
    static public <O extends Observation> List<List<O>>
            readSequences(ObservationReader<O> or, Reader reader)
            throws IOException, FileFormatException {
        List<List<O>> sequences = new ArrayList<>();
        StreamTokenizer st = new StreamTokenizer(reader);

        initSyntaxTable(st);

        for (st.nextToken(); st.ttype != StreamTokenizer.TT_EOF;
                st.nextToken()) {
            st.pushBack();
            List<O> sequence = new ArrayList<>(readSequence(or, st));

            if (sequence == null) {
                break;
            }

            sequences.add(sequence);
        }

        return sequences;
    }

    /* Initialize the syntax table of a stream tokenizer */
    static void initSyntaxTable(StreamTokenizer st) {
        st.resetSyntax();
        st.parseNumbers();
        st.whitespaceChars(0, (int) ' ');
        st.eolIsSignificant(true);
        st.commentChar((int) '#');
    }

    /**
     * Reads an observation sequence out of a file {@link java.io.Reader
     * Reader}.
     *
     * @param oir An observation reader.
     * @param reader Holds the character reader the sequences are read from.
     * @return An observation sequence read from <code>st</code> or null if the
     * end of the file is reached before any sequence is found.
     * @throws be.ac.ulg.montefiore.run.jahmm.io.FileFormatException
     */
    static public <O extends Observation> List<O>
            readSequence(ObservationReader<O> oir, Reader reader)
            throws IOException, FileFormatException {
        StreamTokenizer st = new StreamTokenizer(reader);
        initSyntaxTable(st);

        return readSequence(oir, st);
    }

    /*
     * Reads an observation sequence out of a {@link java.io.StreamTokenizer
     * StreamTokenizer}.  Empty lines or comments can appear before the
     * sequence itself. <code>st</code>'s syntax table must be properly
     * initialized.
     */
    static <O extends Observation> List<O>
            readSequence(ObservationReader<O> oir, StreamTokenizer st)
            throws IOException, FileFormatException {
        for (st.nextToken(); st.ttype == StreamTokenizer.TT_EOL;
                st.nextToken());
        if (st.ttype == StreamTokenizer.TT_EOF) {
            return null;
        }

        List<O> sequence = new ArrayList<>();

        do {
            st.pushBack();
            sequence.add(oir.read(st));

            if (st.nextToken() == '\\') { /* New lines can be escaped by '\' */

                if (st.nextToken() != StreamTokenizer.TT_EOL) {
                    throw new FileFormatException("'\' token is not followed "
                            + "by a new line");
                }
                st.nextToken();
            }
        } while (st.ttype != StreamTokenizer.TT_EOL
                && st.ttype != StreamTokenizer.TT_EOF);

        if (st.ttype == StreamTokenizer.TT_EOF) {
            throw new FileFormatException("Unexpected token: EOF");
        }

        return sequence;
    }
<<<<<<< HEAD

    private ObservationSequencesReader() {
    }
    private static final Logger LOG = Logger.getLogger(ObservationSequencesReader.class.getName());
=======
>>>>>>> parent of e8b9e16... refactorings
}