package ir.ac.iust.dml.kg.resource.extractor;


import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Cache iReaders to a directory to read it again
 * Directory must be empty
 */
public class ResourceCache implements IResourceReader {
    private final static Logger LOGGER = Logger.getLogger(ResourceCache.class);
    private final Path path;
    private int writeIndex = 0;
    private int readIndex = 0;

    public ResourceCache(String path) throws IOException {
        this.path = Paths.get(path);
        Clear();
    }

    /**
     * Clear cache directory
     *
     * @throws IOException
     */
    public void Clear() throws IOException {
        if (!Files.exists(path))
            Files.createDirectories(path);
        else if (!Files.isDirectory(path))
            throw new RuntimeException("path must be a director");
        else {
            FileUtils.deleteDirectory(path.toFile());
            Files.createDirectories(path);
        }
        writeIndex = readIndex = 0;
        LOGGER.info("Cache directory is: " + path.toAbsolutePath().toString());
    }

    public void cache(IResourceReader reader, int pageSize) throws IOException {
        while (!reader.isFinished()) {
            final List<Resource> resources = reader.read(pageSize);
            if (resources.isEmpty()) continue;
            // write object to file
            try (OutputStream fos = Files.newOutputStream(getNextWritePath())) {
                try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                    oos.writeObject(resources);
                    oos.close();
                }
            }
        }
        LOGGER.info("Cache reader index" + writeIndex);
    }

    private synchronized Path getNextWritePath() {
        return path.resolve(String.format("%06d", writeIndex++));
    }

    private synchronized Path getNextReadPath() {
        return path.resolve(String.format("%06d", readIndex++));
    }

    private synchronized Path getCurrentReadPath() {
        return path.resolve(String.format("%06d", readIndex));
    }


    @Override
    public List<Resource> read(int pageSize) throws IOException {
        final Path path = getNextReadPath();
        if (Files.exists(path))
            try (InputStream in = Files.newInputStream(path)) {
                try (ObjectInputStream ois = new ObjectInputStream(in)) {
                    return (List<Resource>) ois.readObject();

                } catch (ClassNotFoundException e) {
                }
            }
        return new ArrayList<>();
    }

    @Override
    public Boolean isFinished() {
        return !Files.exists(getCurrentReadPath());
    }

    @Override
    public void close() throws Exception {

    }
}
