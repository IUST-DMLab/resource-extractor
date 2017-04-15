package ir.ac.iust.dml.kg.resource.extractor.tree;

import ir.ac.iust.dml.kg.resource.extractor.IResourceExtractor;
import ir.ac.iust.dml.kg.resource.extractor.IResourceReader;
import ir.ac.iust.dml.kg.resource.extractor.MatchedResource;
import ir.ac.iust.dml.kg.resource.extractor.Resource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.*;

/**
 * Create a tree on labels of resource for fast matching
 */
public class TreeResourceExtractor implements IResourceExtractor {
    private TreeNode root = new TreeNode();
    private Map<String, Resource> allResource = new HashMap<>();
    static final Logger LOGGER = LogManager.getLogger(TreeResourceExtractor.class);

    @Override
    public void setup(IResourceReader reader, int pageSize) throws IOException {
        LOGGER.info("Start create index");
        while (!reader.isFinished()) {
            final List<Resource> resources = reader.read(pageSize);
            resources.forEach(r -> {
                final Set<String> newLabels = new HashSet<>();
                final Set<String> newAmbiguities = new HashSet<>();
                final Resource old = allResource.get(r.getIri());
                r.getVariantLabel().forEach(l -> {
                    if (old == null || !old.getVariantLabel().contains(l))
                        newLabels.add(l);
                });
                r.getDisambiguatedFrom().forEach(l -> {
                    if (old == null || !old.getDisambiguatedFrom().contains(l))
                        newAmbiguities.add(l);
                });
                final Resource current;
                if (old == null) {
                    current = r;
                    allResource.put(current.getIri(), current);
                } else {
                    current = old;
                    if (r.getLabel() != null)
                        old.setLabel(r.getLabel());
                    if (r.getInstanceOf() != null)
                        old.setInstanceOf(r.getInstanceOf());
                    if (r.getType() != null)
                        old.setType(r.getType());
                    old.getVariantLabel().addAll(newLabels);
                    old.getDisambiguatedFrom().addAll(newAmbiguities);
                }
                newLabels.forEach(l -> root.add(current, l.split("\\s", -1), 0));
                newAmbiguities.forEach(l -> root.addAmbiguity(current, l.split("\\s", -1), 0));
            });
        }
        LOGGER.info("Succeed to create index");
    }

    @Override
    public List<MatchedResource> search(String text, Boolean removeSubset) {
        return null;
    }
}
