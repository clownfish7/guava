package com.clownfish7.eventBus.monitor;

import com.google.common.eventbus.EventBus;

import java.io.IOException;
import java.nio.file.*;

/**
 * @author yzy
 * @classname DirectoryTargetMonitor
 * @description TODO
 * @create 2020-03-27 9:41 AM
 */
public class DirectoryTargetMonitor implements TargetMonitor {

    private WatchService watchService;

    private final EventBus eventBus;

    private final Path path;

    private volatile boolean start = false;

    DirectoryTargetMonitor(EventBus eventBus, String targetPath) {
        this(eventBus, targetPath, "");
    }

    DirectoryTargetMonitor(EventBus eventBus, String targetPath, String... morePaths) {
        this.eventBus = eventBus;
        this.path = Paths.get(targetPath, morePaths);
    }

    @Override
    public void startMonitor() throws IOException {
        this.watchService = FileSystems.getDefault().newWatchService();
        this.path.register(watchService,
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_MODIFY,
                StandardWatchEventKinds.ENTRY_DELETE);

        start = true;
        while (start) {
            WatchKey watchKey = null;
            try {
                watchKey = watchService.take();
                watchKey.pollEvents().forEach(event -> {
                    WatchEvent.Kind<?> kind = event.kind();
                    Path path = (Path) event.context();
                    System.out.println(event.context());
                    eventBus.post(new FileChangeEvent(kind, this.path.resolve(path)));
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
                start = false;
            } finally {
                if (watchKey != null) {
                    watchKey.reset();
                }
            }
        }
    }

    @Override
    public void stopMonitor() throws IOException {
        Thread.currentThread().interrupt();
        this.start = false;
        this.watchService.close();
    }
}
