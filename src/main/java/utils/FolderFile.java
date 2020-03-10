package utils;

import java.io.File;
import java.util.List;

public class FolderFile {
    private String name;
    private String path;
    private String relativePath;
    private List<File> files;
    private List<FolderFile> folderFiles;

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public void setFolderFiles(List<FolderFile> folderFiles) {
        this.folderFiles = folderFiles;
    }

    public List<FolderFile> getFolderFiles() {
        return folderFiles;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public List<File> getFiles() {
        return files;
    }
}
