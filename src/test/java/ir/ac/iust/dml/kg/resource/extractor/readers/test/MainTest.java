package ir.ac.iust.dml.kg.resource.extractor.readers.test;

import ir.ac.iust.dml.kg.resource.extractor.*;
import ir.ac.iust.dml.kg.resource.extractor.ahocorasick.AhoCorasickResourceExtractor;
import ir.ac.iust.dml.kg.resource.extractor.readers.ResourceReaderFromKGStoreV1Service;
import ir.ac.iust.dml.kg.resource.extractor.tree.TreeResourceExtractor;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

/**
 * Test entity reader
 */
public class MainTest {
    @Test
    public void test() throws Exception {
        IResourceExtractor extractor = new AhoCorasickResourceExtractor();
        try (IResourceReader reader = new ResourceCache("H:\\cache", true)) {
            extractor.setup(reader, 1000);
        }
        extractor.search(" قانون اساسی ایران ماگدبورگ زادگاه حسن روحانی", true).forEach(System.out::println);
    }

    @Test
    public void cache() throws Exception {
        final String baseUrl = "http://localhost:8091/";
        final ResourceCache cache = new ResourceCache("test");
        final List<Resource> allResources = new ArrayList<>();
        final List<Resource> allCachedResource = new ArrayList<>();
        try (IResourceReader reader = new ResourceReaderFromKGStoreV1Service(baseUrl)) {
            cache.cache(reader, 2);
        }
        try (IResourceReader reader = new ResourceReaderFromKGStoreV1Service(baseUrl)) {
            while (!reader.isFinished()) allResources.addAll(reader.read(2));
        }
        while (!cache.isFinished()) allCachedResource.addAll(cache.read(2));
        assert allResources.size() == allCachedResource.size();
        for (int i = 0; i < allResources.size(); i++) {
            assert Objects.equals(allResources.get(i).getIri(), allCachedResource.get(i).getIri());
            assert Objects.equals(allResources.get(i).getInstanceOf(), allCachedResource.get(i).getInstanceOf());
            assert Objects.equals(allResources.get(i).getLabel(), allCachedResource.get(i).getLabel());
            assert Objects.equals(allResources.get(i).getType(), allCachedResource.get(i).getType());
            assert Objects.equals(allResources.get(i).getVariantLabel(), allCachedResource.get(i).getVariantLabel());
            assert Objects.equals(allResources.get(i).getClassTree(), allCachedResource.get(i).getClassTree());
        }
    }

    @Test
    public void resourceExtractor() throws Exception {
        final IResourceExtractor re = new TreeResourceExtractor();
        re.setup(new IResourceReader() {
            boolean finished = false;

            @Override
            public List<Resource> read(int pageSize) throws Exception {
                finished = true;
                final List<Resource> r = new ArrayList<>();
                r.add(new Resource("http://hossein", "حسین", "محمد حسین", "حسین خادمی خالدی"));
                r.add(new Resource("http://hossein2", "حسین خادمی", "حسین خادمی خالدی"));
                return r;
            }

            @Override
            public Boolean isFinished() {
                return finished;
            }

            @Override
            public void close() throws Exception {

            }
        }, 0);
        final List<MatchedResource> x = re.search("محمد حسین خادمی خالدی", false);
        x.forEach(System.out::println);
    }

    @Test
    public void labelConverter() throws Exception {
        final IResourceExtractor re = new TreeResourceExtractor();
        re.setup(new IResourceReader() {
            boolean finished = false;

            @Override
            public List<Resource> read(int pageSize) throws Exception {
                finished = true;
                final List<Resource> r = new ArrayList<>();
                r.add(new Resource("http://hossein", "حسین (64)"));
                return r;
            }

            @Override
            public Boolean isFinished() {
                return finished;
            }

            @Override
            public void close() throws Exception {

            }
        }, label -> {
            final HashSet<String> newLabels = new HashSet<>();
            newLabels.add(label);
            newLabels.add(label.replaceAll("\\(.*\\)", "").trim());
            return newLabels;
        }, 0);
        final List<MatchedResource> x = re.search("حسین", false);
        x.forEach(System.out::println);
    }

    @Test
    public void ahoCorasickResourceExtractor() throws Exception {
        final IResourceExtractor re = new AhoCorasickResourceExtractor();
        re.setup(new IResourceReader() {
            boolean finished = false;

            @Override
            public List<Resource> read(int pageSize) throws Exception {
                finished = true;
                final List<Resource> r = new ArrayList<>();
                r.add(new Resource("http://ac", "a c"));
                r.add(new Resource("http://c", "c"));
                r.add(new Resource("http://ca", "c a"));
                r.add(new Resource("http://abb", "a b b"));
                r.add(new Resource("http://b", "b"));
                r.add(new Resource("http://bb", "b b"));
                return r;
            }

            @Override
            public Boolean isFinished() {
                return finished;
            }

            @Override
            public void close() throws Exception {

            }
        }, 0);
        final List<MatchedResource> x = re.search("a c a b b", false);
        x.forEach(System.out::println);
    }

}
