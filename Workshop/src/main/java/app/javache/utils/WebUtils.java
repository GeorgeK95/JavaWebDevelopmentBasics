//package main.java.app.javache.utils;
//
//import java.io.IOException;
//import java.nio.file.DirectoryStream;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by George-Lenovo on 6/29/2017.
// */
//public final class WebUtils {
//    private WebUtils() {
//    }
//
//    public static String getFileExtension(String param) {
//        int dotIndex = param.lastIndexOf(ServerConstants.DOT);
//        String fileExtension;
//
//        try {
//            fileExtension = param.substring(dotIndex, param.length()).substring(1);
//        } catch (StringIndexOutOfBoundsException sioobe) {
//            fileExtension = ServerConstants.EMPTY;
//        }
//
//        return fileExtension;
//    }
//
//    public static List<String> getAllFileNames(String dynamicPagesFullPath) {
//        List<String> names = new ArrayList<>();
//        getFileNames(names, Paths.get(dynamicPagesFullPath));
//        return names;
//    }
//
//    private static List<String> getFileNames(List<String> fileNames, Path dir) {
//        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
//            for (Path path : stream) {
//                if (path.toFile().isDirectory()) {
//                    getFileNames(fileNames, path);
//                } else {
//                    fileNames.add(path.toFile().getName());
////                    System.out.println(path.getFileName());
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return fileNames;
//    }
//
//}
