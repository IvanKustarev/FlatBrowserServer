package Server.InputeOutputeWork;//package L5.L6Server.InputeOutputeWork;
//
//import org.w3c.dom.Document;
//
//import javax.xml.transform.Transformer;
//import javax.xml.transform.TransformerConfigurationException;
//import javax.xml.transform.TransformerException;
//import javax.xml.transform.TransformerFactory;
//import javax.xml.transform.dom.DOMSource;
//import javax.xml.transform.stream.StreamResult;
//import java.io.File;
//import java.io.IOException;
//
//public class ConvertAndOutput {
//    public void output(ProcessedFile processedFile, Document document) throws IOException {
//
////        FileOutputStream fileOutputStream = new FileOutputStream("DataFileXML.txt");
////        fileOutputStream.write(processedFile.getFileXML().getBytes());
////        fileOutputStream.close();
//
//
//            try {
//                TransformerFactory transformerFactory = TransformerFactory.newInstance();
//                Transformer transformer = transformerFactory.newTransformer();
//                DOMSource source = new DOMSource(document);
////                StreamResult result = new StreamResult();
//                StreamResult result = new StreamResult(new File(processedFile.getFileAddress()));
////            transformer.transform(source, result);
////            System.out.println(result.);
//
//            }catch(TransformerConfigurationException e){
//            }catch (TransformerException e) { }
//
//    }
//}
