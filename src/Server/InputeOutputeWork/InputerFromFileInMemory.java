package Server.InputeOutputeWork;//package L5.L6Server.InputeOutputeWork;
//
//import java.io.BufferedInputStream;
//import java.io.FileInputStream;
//import java.io.IOException;
//
//public class InputerFromFileInMemory {
//    public String input(ProcessedFile processedFile) throws IOException {
//        BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(processedFile.getFileAddress()));
//        int i;
//        String data = new String();
//        while ((i = inputStream.read()) != -1){
//            data += (char) i;
//        }
//        return data;
//    }
//
//}
