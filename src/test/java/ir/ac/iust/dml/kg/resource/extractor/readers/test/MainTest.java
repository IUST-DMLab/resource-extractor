package ir.ac.iust.dml.kg.resource.extractor.readers.test;

import ir.ac.iust.dml.kg.resource.extractor.IResourceExtractor;
import ir.ac.iust.dml.kg.resource.extractor.IResourceReader;
import ir.ac.iust.dml.kg.resource.extractor.readers.ResourceReaderFromKGStoreV1Service;
import ir.ac.iust.dml.kg.resource.extractor.tree.TreeResourceExtractor;
import org.junit.Test;

/**
 * Test entity reader
 */
public class MainTest {
    @Test
    public void test() throws Exception {
        IResourceExtractor extractor = new TreeResourceExtractor();
        try (IResourceReader reader = new ResourceReaderFromKGStoreV1Service("http://localhost:8091/")) {
            extractor.setup(reader, 1000);
        }
    }
}
