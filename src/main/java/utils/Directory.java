package utils;

import java.io.File;
import java.util.List;

public class Directory {
    private String relativePath;
    private String path;
    private String name;
    private List<String> files;
    private List<String> folders;
    private boolean isEmpty;

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }

    public void setFolders(List<String> folders) {
        this.folders = folders;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public List<String> getFiles() {
        return files;
    }

    public List<String> getFolders() {
        return folders;
    }
}
