package ir.ac.iust.dml.kg.resource.extractor;

import java.util.HashSet;
import java.util.Set;

/**
 * Resource that match
 */
public class MatchedResource {
    private final int start;
    private final int end;
    private final Resource resource;
    private final Set<Resource> ambiguities;

    public MatchedResource(int start, int end, Resource resource, Set<Resource> ambiguities) {
        this.start = start;
        this.end = end;
        this.resource = resource == null ? null : new Resource(resource);
        this.ambiguities = new HashSet<>();
        if (ambiguities != null)
            ambiguities.forEach(a -> this.ambiguities.add(new Resource(a)));
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public Resource getResource() {
        return resource;
    }

    public Set<Resource> getAmbiguities() {
        return ambiguities;
    }

    @Override
    public String toString() {
        final StringBuilder str = new StringBuilder();
        if (ambiguities != null && !ambiguities.isEmpty())
            ambiguities.forEach(a -> str.append(String.format("or(%s)", a)));
        return String.format("<-%d-%d-> %s \t %s", start, end, resource, str);

    }
}