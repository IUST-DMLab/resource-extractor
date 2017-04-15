package ir.ac.iust.dml.kg.resource.extractor.readers.test;

import ir.ac.iust.dml.kg.resource.extractor.IResourceReader;
import ir.ac.iust.dml.kg.resource.extractor.readers.ResourceReaderFromKGStoreV1Service;
import org.junit.Test;

/**
 * test readers
 */
public class ReadersTest {
    @Test
    public void testKGStoreV1Service() throws Exception {
        try (IResourceReader reader = new ResourceReaderFromKGStoreV1Service("http://localhost:8091/")) {
            while (!reader.isFinished())
                reader.read(1000).forEach(System.out::println);
        }

    }
}
