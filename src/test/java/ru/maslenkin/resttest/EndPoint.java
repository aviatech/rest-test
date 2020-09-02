package ru.maslenkin.resttest;

public final class EndPoint {
    public static final String pathLink = "v1/disk/resources/upload?path={path}&overwrite=true";
    public static final String copyPathBadRequest = "/v1/disk/resources/copy?from=&path=";
    public static final String pathUploadFolder = "/v1/disk/resources?path={folderName}";
    public static final String copyPathFile = "/v1/disk/resources/copy?from={fileName}&path=copy_{fileName}&overwrite=true";
    public static final String copyPathFolder = "/v1/disk/resources/copy?from={folderName}&path=copy_{folderName}&overwrite=true";
    public static final String path = "/v1/disk/resources?path={path}";


}
