package com.clownfish7.eventBus.monitor;

import java.nio.file.Path;
import java.nio.file.WatchEvent;

/**
 * @author yzy
 * @classname FileChangeEvent
 * @description TODO
 * @create 2020-03-27 10:02 AM
 */
public class FileChangeEvent {

    private WatchEvent.Kind<?> kind;

    private Path path;

    public FileChangeEvent(WatchEvent.Kind<?> kind, Path path) {
        this.kind = kind;
        this.path = path;
    }

    public WatchEvent.Kind<?> getKind() {
        return kind;
    }

    public void setKind(WatchEvent.Kind<?> kind) {
        this.kind = kind;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }
}
