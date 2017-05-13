package ir.ac.iust.dml.kg.resource.extractor;

import ir.ac.iust.dml.kg.resource.extractor.tree.TreeResourceExtractor;

/**
 * Created by ali on 5/13/17.
 */
public class LoadingTest {
    public static void main(String[] args) throws Exception {
        long t1 = System.currentTimeMillis();
        IResourceExtractor extractor = new TreeResourceExtractor();
        try (IResourceReader reader = new ResourceCache("/media/sf_D/Dropbox/dev/kg/data/fst-cache")) {
            extractor.setup(reader, 1000);
        }
        System.out.println("" + (System.currentTimeMillis() - t1));
        extractor.search(" قانون اساسی ایران ماگدبورگ", true).forEach(System.out::println);
        System.out.println("Num results: " + extractor.search(" قانون اساسی ایران ماگدبورگ", true).size());;
    }
}
