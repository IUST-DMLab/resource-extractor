package ir.ac.iust.dml.kg.resource.extractor;

import java.util.List;

/**
 * Interface for extract resource
 */
public interface IResourceExtractor {
    void setup(IResourceReader reader, int pageSize) throws Exception;

    List<MatchedResource> search(String text, Boolean removeSubset);
}
