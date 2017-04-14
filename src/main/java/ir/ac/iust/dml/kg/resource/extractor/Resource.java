package ir.ac.iust.dml.kg.resource.extractor;

import java.util.HashSet;
import java.util.Set;

/**
 * Resource Data class
 */
public class Resource {
    private String iri;
    private ResourceType type;
    private String instanceOf;
    private final Set<String> classTree = new HashSet<>();
    private String label;
    private final Set<String> variantLabel = new HashSet<>();
    private final Set<String> disambiguatedFrom = new HashSet<>();

    public Resource() {
    }

    public Resource(String iri) {
        this.iri = iri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resource resource = (Resource) o;

        return iri != null ? iri.equals(resource.iri) : resource.iri == null;
    }

    @Override
    public int hashCode() {
        return iri != null ? iri.hashCode() : 0;
    }

    public boolean hasData() {
        return type != null || instanceOf != null || !classTree.isEmpty() ||
                label != null || !variantLabel.isEmpty() || !disambiguatedFrom.isEmpty();
    }

    public String getIri() {
        return iri;
    }

    public void setIri(String iri) {
        this.iri = iri;
    }

    public ResourceType getType() {
        return type;
    }

    public void setType(ResourceType type) {
        this.type = type;
    }

    public String getInstanceOf() {
        return instanceOf;
    }

    public void setInstanceOf(String instanceOf) {
        this.instanceOf = instanceOf;
    }

    public Set<String> getClassTree() {
        return classTree;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Set<String> getVariantLabel() {
        return variantLabel;
    }

    public Set<String> getDisambiguatedFrom() {
        return disambiguatedFrom;
    }


    @Override
    public String toString() {
        return String.format("%s type:%s label:%s", iri, type, label);
    }
}
