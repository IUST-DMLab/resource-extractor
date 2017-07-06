package ir.ac.iust.dml.kg.resource.extractor;

import java.util.List;

/**
 * Interface for extract resource
 */
public interface IResourceExtractor {
    void setup(IResourceReader reader, int pageSize) throws Exception;

    void setup(IResourceReader reader, ILabelConverter converter, int pageSize) throws Exception;

    List<MatchedResource> search(String text, Boolean removeSubset);

    Resource getResourceByIRI(String iri);
}
