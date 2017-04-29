package ir.ac.iust.dml.kg.resource.extractor.readers.test;

import ir.ac.iust.dml.kg.resource.extractor.IResourceReader;
import ir.ac.iust.dml.kg.resource.extractor.ResourceCache;
import ir.ac.iust.dml.kg.resource.extractor.readers.ResourceReaderFromKGStoreV1Service;
import ir.ac.iust.dml.kg.resource.extractor.readers.ResourceReaderFromVirtuoso;
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

    @Test
    public void testVirtuosoReader() throws Exception {
        final ResourceCache cache = new ResourceCache("h:\\test");
        try (IResourceReader reader = new ResourceReaderFromVirtuoso("194.225.227.161", "1111",
                "dba", "dba", "http://localhost:8890/knowledgeGraphV2")) {
            cache.cache(reader, 100000);
        }
    }
}
