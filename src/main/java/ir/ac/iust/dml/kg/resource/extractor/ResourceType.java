package ir.ac.iust.dml.kg.resource.extractor;

/**
 * Type of resource
 */
public enum ResourceType {
    Entity, Property;


    @Override
    public String toString() {
        switch (this) {
            case Property:
                return "http://www.w3.org/2002/07/owl#DatatypeProperty";
            case Entity:
                return "http://www.w3.org/2000/01/rdf-schema#Resource";
        }
        throw new RuntimeException("Unknown type");
    }
}
