Extract all resource from a text

# How to use
```java
class Test {
    void sample() {
        IResourceExtractor extractor = new TreeResourceExtractor();
                try (IResourceReader reader = new ResourceReaderFromKGStoreV1Service("http://localhost:8091/")) {
                    extractor.setup(reader, 1000);
                }
                extractor.search(" قانون اساسی ایران ماگدبورگ", true).forEach(System.out::println);
    }
}
```

* if `remove subset` is true, remove subset of entities else not
* for example `farhad rahbar` with removeSubset has 1 entities
* for example `farhad rahbar` with !removeSubset has 2 entities


## Entity readers
read entity from file or rest or ...

* `ResourceReaderFromKGStoreV1Service` read entity from rest KGStore Service `/rs/v1/triples/search`

```java
class Test {
    void sample() {
        try (IResourceReader reader = new ResourceReaderFromKGStoreV1Service("http://194.225.227.161:8091/")) {
            extractor.setup(reader, 1000);
        }
    }
}
```