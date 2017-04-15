package ir.ac.iust.dml.kg.resource.extractor.tree;

import ir.ac.iust.dml.kg.resource.extractor.Resource;

import java.util.*;

/**
 * A node of tree
 */
class TreeNode {
    private Resource resource;
    private final Set<Resource> ambiguities = new HashSet<>();
    private final Map<String, TreeNode> childs = new HashMap<>();

    void add(Resource resource, String[] path, int position) {
        if (position == path.length) {
            if (this.resource != null && !this.resource.equals(resource)) {
                System.err.printf("Conflict on %s: %s with new %s\n", Arrays.toString(path), this.resource, resource);
                return;
            }
            this.resource = resource;
        } else {
            final String urn = path[position];
            TreeNode current = childs.computeIfAbsent(urn, k -> new TreeNode());
            current.add(resource, path, position + 1);
        }
    }

    void addAmbiguity(Resource resource, String[] path, int position) {
        if (position == path.length)
            this.ambiguities.add(resource);
        else {
            final String urn = path[position];
            TreeNode current = childs.computeIfAbsent(urn, k -> new TreeNode());
            current.add(resource, path, position + 1);
        }
    }

    public Resource getResource() {
        return resource;
    }

    public Set<Resource> getAmbiguities() {
        return ambiguities;
    }
}
