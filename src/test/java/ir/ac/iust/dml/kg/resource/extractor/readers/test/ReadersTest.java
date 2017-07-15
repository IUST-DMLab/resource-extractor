package ir.ac.iust.dml.kg.resource.extractor.readers.test;

import ir.ac.iust.dml.kg.resource.extractor.IResourceExtractor;
import ir.ac.iust.dml.kg.resource.extractor.IResourceReader;
import ir.ac.iust.dml.kg.resource.extractor.ResourceCache;
import ir.ac.iust.dml.kg.resource.extractor.readers.ResourceReaderFromKGStoreV1Service;
import ir.ac.iust.dml.kg.resource.extractor.readers.ResourceReaderFromVirtuoso;
import ir.ac.iust.dml.kg.resource.extractor.tree.TreeResourceExtractor;
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
        final ResourceCache cache = new ResourceCache("h:\\test2", true);
        try (IResourceReader reader = new ResourceReaderFromVirtuoso("194.225.227.161", "1111",
                "dba", "fkgVIRTUOSO2017", "http://fkg.iust.ac.ir/")) {
            cache.cache(reader, 1000);
        }
    }

    @Test
    public void cacheTest() throws Exception {
        long t1 = System.currentTimeMillis();
        IResourceExtractor extractor = new TreeResourceExtractor();
        try (IResourceReader reader = new ResourceCache("H:\\cache", true)) {
            extractor.setup(reader, 1000);
        }
        System.out.println("" + (System.currentTimeMillis() - t1));
        extractor.search(" قانون اساسی ایران ماگدبورگ", true).forEach(System.out::println);
    }

}
