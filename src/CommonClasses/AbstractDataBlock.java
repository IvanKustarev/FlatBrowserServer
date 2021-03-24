package CommonClasses;

import java.io.Serializable;

public abstract class AbstractDataBlock implements Serializable {

    static final long serialVersionUID = 0;

    public boolean isServerNeedStringParameter = false;
    public boolean isServerNeedElementParameter = false;
    public boolean isUserNeedToShowFlatArr = false;

    public String phrase = null;
    public String parameter = null;
    public Flat flat = null;
    public boolean allRight;
    public Flat[] flats;

    public boolean isAllRight() {
        return allRight;
    }

    public boolean isServerNeedElementParameter() {
        return isServerNeedElementParameter;
    }

    public boolean isServerNeedStringParameter() {
        return isServerNeedStringParameter;
    }

    public boolean isUserNeedToShowFlatArr() {
        return isUserNeedToShowFlatArr;
    }

    public Flat getFlat() {
        return flat;
    }

    public String getParameter() {
        return parameter;
    }

    public Flat[] getFlats() {
        return flats;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setFlat(Flat flat) {
        this.flat = flat;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public void setAllRight(boolean allRight) {
        this.allRight = allRight;
    }

    public void setFlats(Flat[] flats) {
        this.flats = flats;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public void setServerNeedElementParameter(boolean serverNeedElementParameter) {
        isServerNeedElementParameter = serverNeedElementParameter;
    }

    public void setServerNeedStringParameter(boolean serverNeedStringParameter) {
        isServerNeedStringParameter = serverNeedStringParameter;
    }

    public void setUserNeedToShowFlatArr(boolean userNeedToShowFlatArr) {
        isUserNeedToShowFlatArr = userNeedToShowFlatArr;
    }
}
