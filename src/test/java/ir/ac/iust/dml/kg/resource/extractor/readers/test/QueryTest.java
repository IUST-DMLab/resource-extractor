package ir.ac.iust.dml.kg.resource.extractor.readers.test;

import ir.ac.iust.dml.kg.resource.extractor.IResourceExtractor;
import ir.ac.iust.dml.kg.resource.extractor.IResourceReader;
import ir.ac.iust.dml.kg.resource.extractor.MatchedResource;
import ir.ac.iust.dml.kg.resource.extractor.ResourceCache;
import ir.ac.iust.dml.kg.resource.extractor.tree.TreeResourceExtractor;
import org.junit.Test;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryTest {
    class KeyCount {
        String key;
        int count;

        public KeyCount(String key, int count) {
            this.key = key;
            this.count = count;
        }
    }

    @Test
    public void createMostQuery() throws Exception {
        final IResourceExtractor extractor = new TreeResourceExtractor();
        try (IResourceReader reader = new ResourceCache("G:\\Cache", true)) {
            extractor.setup(reader, 0);
        }

        final List<KeyCount> sentences = new ArrayList<>();
        Files.readAllLines(Paths.get("G:\\logs.txt"), Charset.forName("utf8")).stream().skip(1).forEach(l -> {
            final String[] args = l.split(",");
            if (args.length == 3) {
                final String key = args[1].replace("\"", "");
                final int count = Integer.parseInt(args[2].replace("\"", ""));
                sentences.add(new KeyCount(key, count));
            } else
                System.err.println("Bag log:" + l);
        });

        final Map<String, KeyCount> resources = new HashMap<>();
        sentences.parallelStream().forEach(s -> {
            final List<MatchedResource> res = extractor.search(s.key, true);
            res.forEach(r -> {
                if (r.getResource() != null) {
                    KeyCount current = resources.get(r.getResource().getIri());
                    if (current == null) current = new KeyCount(r.getResource().getIri(), 0);
                    current.count += s.count;
                    resources.put(r.getResource().getIri(), current);
                }
                r.getAmbiguities().forEach(a -> {
                    KeyCount current = resources.get(a.getIri());
                    if (current == null) current = new KeyCount(a.getIri(), 0);
                    current.count += s.count;
                    resources.put(a.getIri(), current);
                });
            });
        });
    }
}
