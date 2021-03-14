package CommonClasses.ApartmentDescription;

import java.io.Serializable;

public enum Transport implements Attractive , Serializable {
    FEW{
        @Override
        public int levelAttractive() {
            return 1;
        }
    },
    NONE {
        @Override
        public int levelAttractive() {
            return 0;
        }
    },
    LITTLE {
        @Override
        public int levelAttractive() {
            return 2;
        }
    },
    NORMAL {
        @Override
        public int levelAttractive() {
            return 3;
        }
    },
    ENOUGH {
        @Override
        public int levelAttractive() {
            return 4;
        }
    };
}