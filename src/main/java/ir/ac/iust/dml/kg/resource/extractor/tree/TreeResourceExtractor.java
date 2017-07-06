package ir.ac.iust.dml.kg.resource.extractor.tree;

import ir.ac.iust.dml.kg.resource.extractor.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Create a tree on labels of resource for fast matching
 */
public class TreeResourceExtractor implements IResourceExtractor {
    private TreeNode root = new TreeNode();
    private Map<String, Resource> allResource = new HashMap<>();
    static final Logger LOGGER = LogManager.getLogger(TreeResourceExtractor.class);

    @Override
    public void setup(IResourceReader reader, int pageSize) throws Exception {
        setup(reader, null, pageSize);
    }

    @Override
    public void setup(IResourceReader reader, ILabelConverter converter, int pageSize) throws Exception {
        LOGGER.info("Start create index");
        while (!reader.isFinished()) {
            final List<Resource> resources = reader.read(pageSize);
            resources.forEach(r -> {
                final Set<String> newLabels = new HashSet<>();
                final Resource old = allResource.get(r.getIri());
                r.getVariantLabel().forEach(l -> {
                    if (converter != null)
                        converter.convert(l).forEach(l2 -> {
                            if (old == null || !old.getVariantLabel().contains(l2))
                                newLabels.add(l2);
                        });
                    else if (old == null || !old.getVariantLabel().contains(l))
                        newLabels.add(l);
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
                    if (!r.getClassTree().isEmpty())
                        old.getClassTree().addAll(r.getClassTree());
                    old.getVariantLabel().addAll(newLabels);
                }
                newLabels.forEach(l -> root.add(current, l.split("\\s", -1), 0));
            });
        }
        LOGGER.info("Succeed to create index");
    }

    @Override
    public List<MatchedResource> search(String text, Boolean removeSubset) {
        if (root == null) return null;
        final String[] words = text.split("\\s", -1);
        final List<Candidate> candidates = new ArrayList<>();
        for (int i = 0; i < words.length; i++) {
            final String word = words[i];
            candidates.add(new Candidate(i, root));
            candidates.forEach(c -> c.extend(word));
        }
        final List<MatchedResource> resources = new ArrayList<>();
        candidates.forEach(c -> {
            final List<MatchedResource> newResources = c.createResource(removeSubset);
            newResources.forEach(n -> {
                if (removeSubset && resources.size() > 0 && n.getEnd() == resources.get(resources.size() - 1).getEnd())
                    return;
                resources.add(n);
            });
        });
        return resources;
    }
}
