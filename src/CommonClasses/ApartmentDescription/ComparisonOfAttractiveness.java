package CommonClasses.ApartmentDescription;

import CommonClasses.Flat;

import java.io.Serializable;

public class ComparisonOfAttractiveness  implements Serializable {
    public static long compare(Flat flat){
        long levelOfPreference;
        levelOfPreference = flat.getNumberOfRooms();

        int houseAttractive;
        int transportAttractive;
        int viewAttractive;

        try {
            houseAttractive = flat.getHouse().levelAttractive();
        }catch (NullPointerException e){
            houseAttractive = 0;
        }

        try {

            transportAttractive = flat.getTransport().levelAttractive();
        }catch (NullPointerException e){
            transportAttractive = 0;
        }

        try {
            viewAttractive = flat.getView().levelAttractive();
        }catch (NullPointerException e){
            viewAttractive =0;
        }

        levelOfPreference += flat.getFurnish().levelAttractive() + houseAttractive + transportAttractive + viewAttractive;

        return levelOfPreference;
    }
}
