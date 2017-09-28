package ir.ac.iust.dml.kg.resource.extractor;

/**
 *
 * Farsi Knowledge Graph Project
 * Iran University of Science and Technology (Year 2017)
 * Developed by HosseiN Khademi khaledi
 *
 * Type of resource
 */
public enum ResourceType {
    Entity, Property, Category;


    @Override
    public String toString() {
        switch (this) {
            case Property:
                // TODO it must be http://www.w3.org/2002/07/owl#ObjectProperty but I didn't change it for backward compatibility
                return "http://www.w3.org/2002/07/owl#DatatypeProperty";
            case Entity:
                return "http://www.w3.org/2000/01/rdf-schema#Resource";
            case Category:
                return "https://www.w3.org/2009/08/skos-reference/skos.html#Concept";
        }
        throw new RuntimeException("Unknown type");
    }
}
