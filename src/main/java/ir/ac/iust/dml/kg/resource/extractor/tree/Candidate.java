package ir.ac.iust.dml.kg.resource.extractor.tree;

import ir.ac.iust.dml.kg.resource.extractor.MatchedResource;
import ir.ac.iust.dml.kg.resource.extractor.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Candidate of entity must not entity yet
 */
public class Candidate {
    private final int start;
    private boolean valid;
    private final List<TreeNode> path = new ArrayList<>();


    Candidate(int start, TreeNode node) {
        this.start = start;
        this.valid = true;
        this.path.add(node);
    }

    Candidate extend(String word) {
        if (!valid) return this;
        final TreeNode node = lastNode().extend(word);
        if (node != null)
            path.add(node);
        else
            valid = false;
        return this;
    }

    private TreeNode lastNode() {
        return path.get(path.size() - 1);
    }

    List<MatchedResource> createResource(boolean removeSubset) {
        final List<MatchedResource> result = new ArrayList<>();
        for (int j = path.size() - 1; j >= 0; j--) {
            final Resource resource = path.get(j).getResource();
            final Set<Resource> ambiguities = path.get(j).getAmbiguities();
            if (resource != null || !ambiguities.isEmpty()) {
                final MatchedResource m = new MatchedResource(start, start + j - 1, resource, ambiguities);
                result.add(m);
                if (removeSubset)
                    break;
            }
        }
        return result;
    }
}
