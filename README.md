Extract all resource from a text

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