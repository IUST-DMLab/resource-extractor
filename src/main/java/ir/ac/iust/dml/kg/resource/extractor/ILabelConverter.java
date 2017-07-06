package ir.ac.iust.dml.kg.resource.extractor;


import java.util.HashSet;

/**
 * Convert label to new label
 * for example convert 'hossein (birth 79)' to 'hossein'
 * if you not return original label, it has been ignored
 */
public interface ILabelConverter {
    HashSet<String> convert(String label);
}
