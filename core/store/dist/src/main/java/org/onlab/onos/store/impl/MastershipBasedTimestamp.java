package org.onlab.onos.store.impl;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Objects;

import org.onlab.onos.store.Timestamp;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ComparisonChain;

/**
 * A logical timestamp that derives its value from two things:
 * <ul>
 * <li> The current mastership term of the device.</li>
 * <li> The value of the counter used for tracking topology events observed from
 * the device during that current time of a device. </li>
 * </ul>
 */
public final class MastershipBasedTimestamp implements Timestamp {

    private final int termNumber;
    private final int sequenceNumber;

    /**
     * Default version tuple.
     *
     * @param termNumber the mastership termNumber
     * @param sequenceNumber  the sequenceNumber number within the termNumber
     */
    public MastershipBasedTimestamp(int termNumber, int sequenceNumber) {
        this.termNumber = termNumber;
        this.sequenceNumber = sequenceNumber;
    }

    @Override
    public int compareTo(Timestamp o) {
        checkArgument(o instanceof MastershipBasedTimestamp,
                "Must be MastershipBasedTimestamp", o);
        MastershipBasedTimestamp that = (MastershipBasedTimestamp) o;

        return ComparisonChain.start()
                .compare(this.termNumber, that.termNumber)
                .compare(this.sequenceNumber, that.sequenceNumber)
                .result();
    }

    @Override
    public int hashCode() {
        return Objects.hash(termNumber, sequenceNumber);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MastershipBasedTimestamp)) {
            return false;
        }
        MastershipBasedTimestamp that = (MastershipBasedTimestamp) obj;
        return Objects.equals(this.termNumber, that.termNumber) &&
                Objects.equals(this.sequenceNumber, that.sequenceNumber);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(getClass())
                    .add("termNumber", termNumber)
                    .add("sequenceNumber", sequenceNumber)
                    .toString();
    }

    /**
     * Returns the termNumber.
     *
     * @return termNumber
     */
    public int termNumber() {
        return termNumber;
    }

    /**
     * Returns the sequenceNumber number.
     *
     * @return sequenceNumber
     */
    public int sequenceNumber() {
        return sequenceNumber;
    }

    // Default constructor for serialization
    @Deprecated
    protected MastershipBasedTimestamp() {
        this.termNumber = -1;
        this.sequenceNumber = -1;
    }
}
