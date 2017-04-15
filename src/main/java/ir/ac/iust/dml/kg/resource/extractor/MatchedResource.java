package ir.ac.iust.dml.kg.resource.extractor;

/**
 * Resource that match
 */
public class MatchedResource {
    private final int start;
    private final int end;

    public MatchedResource(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

}