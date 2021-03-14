package L6Server.InputeOutputeWork;

import CommonClasses.Flat;
import CommonClasses.ApartmentDescription.*;
import L6Server.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.ArrayDeque;
import java.util.Date;

public class LoadingCollectionFromFile {

    private Document document;
    private ArrayDeque<Flat> setOfFlats = new ArrayDeque<>();

    public Document load(String fileAddress) throws ParserConfigurationException {
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        try {
            document = documentBuilder.parse(new BufferedInputStream(new FileInputStream(fileAddress)));
        } catch (Throwable e) {
            System.out.println("Указанный файл не содержит XML формата, либо в XML формате допущены ошибки!");
//            L6Server.Main.getFileAddress();
//            document = L6Server.Main.startLoading();
//            User user = new User();
            String[] strArr = new String[1];
            strArr[0] = Main.printAndRead("Попробуем всё сначала. Введите адресс файла:");
            document = (new LoadingCollectionFromFile()).load(Main.gettingAddress(strArr));
        }
        return document;
    }

    public FlatCollection convert(Document document){


        Element mainTeg = document.getDocumentElement();
        FlatCollection flatCollection = new FlatCollection();


        for(int i = 0; i < mainTeg.getChildNodes().getLength();i++){
            Node nodeFlat = mainTeg.getChildNodes().item(i);
            Flat flat = new Flat();

            flat.setId(Long.valueOf(nodeFlat.getChildNodes().item(0).getTextContent()));

            flat.setName(nodeFlat.getChildNodes().item(1).getTextContent());

            Coordinates coordinates = new Coordinates();

            coordinates.setX(Double.valueOf(nodeFlat.getChildNodes().item(2).getChildNodes().item(0).getTextContent()));

            coordinates.setY(Integer.valueOf(nodeFlat.getChildNodes().item(2).getChildNodes().item(1).getTextContent()));

            flat.setCoordinates(coordinates);

            flat.setCreationDate(new Date());

            flat.setArea(Long.valueOf(nodeFlat.getChildNodes().item(4).getTextContent()));

            flat.setNumberOfRooms(Long.valueOf(nodeFlat.getChildNodes().item(5).getTextContent()));

            flat.setFurnish(Furnish.valueOf(nodeFlat.getChildNodes().item(6).getTextContent()));

            if(!nodeFlat.getChildNodes().item(7).getTextContent().equals("")) {
                flat.setView(View.valueOf(nodeFlat.getChildNodes().item(7).getTextContent()));
            }
            else {
                flat.setView(null);
            }

            if(!nodeFlat.getChildNodes().item(8).getTextContent().equals("")) {
                flat.setTransport(Transport.valueOf(nodeFlat.getChildNodes().item(8).getTextContent()));
            }
            else {
                flat.setTransport(null);
            }

            House house = new House();
            if(!nodeFlat.getChildNodes().item(9).getTextContent().equals("")) {
                house.setName(nodeFlat.getChildNodes().item(9).getChildNodes().item(0).getTextContent());
                house.setYear(Long.valueOf(nodeFlat.getChildNodes().item(9).getChildNodes().item(1).getTextContent()));
                house.setNumberOfFloors(Long.valueOf(nodeFlat.getChildNodes().item(9).getChildNodes().item(2).getTextContent()));
                house.setNumberOfFlatsOnFloor(Integer.valueOf(nodeFlat.getChildNodes().item(9).getChildNodes().item(3).getTextContent()));
                house.setNumberOfLifts(Long.valueOf(nodeFlat.getChildNodes().item(9).getChildNodes().item(4).getTextContent()));
                flat.setHouse(house);
            }
            else {
                flat.setHouse(null);
            }

          addFlatToCollection(flat, flatCollection);
        }
        return flatCollection;

    }

    private void addFlatToCollection(Flat flat, FlatCollection flatCollection){
        flatCollection.add(flat);
    }
}

