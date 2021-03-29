package Server.InputeOutputeWork;


import CommonClasses.Flat;
import Server.FlatCollectionWorkers.FlatCollection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.Iterator;

public class UpLoadingCollectionToFile {
    private Document document;

    public Document convert(FlatCollection flatCollection) throws ParserConfigurationException {

        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        document = documentBuilder.newDocument();
        Element mainTeg = document.createElement("mainTeg");
        document.appendChild(mainTeg);

        Iterator iterator = flatCollection.getIterator();

        for (int i = 0; i < flatCollection.getCollectionSize(); i++) {

            Node flatNode = document.createElement("flat");
            mainTeg.appendChild(flatNode);

            Flat flat = (Flat) iterator.next();

            Node id = document.createElement("id");
            id.setTextContent(String.valueOf(flat.getId()));
            flatNode.appendChild(id);


            Node name = document.createElement("name");
            name.setTextContent(String.valueOf(flat.getName()));
            flatNode.appendChild(name);


            Node coordinates = document.createElement("coordinates");
            flatNode.appendChild(coordinates);

            Node x = document.createElement("x");

            x.setTextContent(String.valueOf(flat.getCoordinates().getX()));


            Node y = document.createElement("y");
            y.setTextContent(String.valueOf(flat.getCoordinates().getY()));


            coordinates.appendChild(x);
            coordinates.appendChild(y);


            Node date = document.createElement("creationDate");
            date.setTextContent(String.valueOf(flat.getCreationDate()));
            flatNode.appendChild(date);


            Node area = document.createElement("area");
            area.setTextContent(String.valueOf(flat.getArea()));
            flatNode.appendChild(area);


            Node numberOfRooms = document.createElement("numberOfRooms");
            numberOfRooms.setTextContent(String.valueOf(flat.getNumberOfRooms()));
            flatNode.appendChild(numberOfRooms);


            Node furnish = document.createElement("furnish");
            furnish.setTextContent(flat.getFurnish().name());
            flatNode.appendChild(furnish);


            Node view = document.createElement("view");
            try {
                view.setTextContent(flat.getView().name());
            } catch (Exception e) {
                view.setTextContent("");
            }
            flatNode.appendChild(view);


            Node transport = document.createElement("transport");
            try {
                transport.setTextContent(flat.getTransport().name());
            } catch (Exception e) {
                transport.setTextContent("");
            }
            flatNode.appendChild(transport);


            Node house = document.createElement("house");
            flatNode.appendChild(house);

            if (flat.getHouse() != null) {
                Node houseName = document.createElement("name");
                houseName.setTextContent(String.valueOf(flat.getHouse().getName()));

                Node year = document.createElement("year");
                year.setTextContent(String.valueOf(flat.getHouse().getYear()));

                Node numberOfFloors = document.createElement("numberOfFloors");
                numberOfFloors.setTextContent(String.valueOf(flat.getHouse().getNumberOfFloors()));

                Node numberOfFlatsOnFloor = document.createElement("numberOfFlatsOnFloor");
                numberOfFlatsOnFloor.setTextContent(String.valueOf(flat.getHouse().getNumberOfFlatsOnFloor()));

                Node numberOfLifts = document.createElement("numberOfLifts");
                numberOfLifts.setTextContent(String.valueOf(flat.getHouse().getNumberOfLifts()));

                house.appendChild(houseName);
                house.appendChild(year);
                house.appendChild(numberOfFloors);
                house.appendChild(numberOfFlatsOnFloor);
                house.appendChild(numberOfLifts);
            } else {
                house.setTextContent("");
            }
        }
        return document;
    }

    public void save(Document document, String fileAddress) throws TransformerException {
//        String fileAddress = findFileAddress();
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);

        StreamResult result = new StreamResult(new File(fileAddress));
        transformer.transform(source, result);
    }

//    private String findFileAddress(){
//        return L6Server.Main.getFileAddress();
//    }
}
