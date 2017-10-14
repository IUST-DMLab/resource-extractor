package ir.ac.iust.dml.kg.resource.extractor.readers;

import ir.ac.iust.dml.kg.resource.extractor.Resource;
import ir.ac.iust.dml.kg.resource.extractor.ResourceType;

class ResourceConverter {

  static void setTypeAndLabel(Resource resource, String predicate, String value) {
    switch (predicate) {
      case "http://www.w3.org/1999/02/22-rdf-syntax-ns#type":
      case "https://www.w3.org/1999/02/22-rdf-syntax-ns#type":
        switch (value) {
          case "http://www.w3.org/2000/01/rdf-schema#Resource":
          case "http://www.w3.org/2002/07/owl#NamedIndividual":
            resource.setType(ResourceType.Entity);
            break;
          case "https://www.w3.org/2009/08/skos-reference/skos.html#Concept":
            resource.setType(ResourceType.Category);
            break;
          case "http://www.w3.org/2002/07/owl#DatatypeProperty":
          case "http://www.w3.org/2002/07/owl#ObjectProperty":
          case "https://www.w3.org/1999/02/22-rdf-syntax-ns#Property":
          case "http://www.w3.org/1999/02/22-rdf-syntax-ns#Property":
            resource.setType(ResourceType.Property);
            break;
          default:
            resource.getClassTree().add(value);
            break;
        }
        break;
      case "https://www.w3.org/1999/02/22-rdf-syntax-ns#instanceOf":
      case "http://www.w3.org/1999/02/22-rdf-syntax-ns#instanceOf":
        resource.setInstanceOf(value);
        break;
      case "http://www.w3.org/2000/01/rdf-schema#label":
        resource.setLabel(value);
        resource.getVariantLabel().add(value);
        break;
      case "http://fkg.iust.ac.ir/ontology/variantLabel":
      case "http://dbpedia.org/ontology/wikiDisambiguatedFrom":
      case "http://fkg.iust.ac.ir/ontology/wikiDisambiguatedFrom":
        resource.getVariantLabel().add(value);
        break;
      case "http://www.w3.org/2000/01/rdf-schema#domain":
        resource.getClassTree().add(value);
        break;
    }
  }
}
