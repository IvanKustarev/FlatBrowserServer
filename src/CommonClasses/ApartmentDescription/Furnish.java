package CommonClasses.ApartmentDescription;

import java.io.Serializable;

public enum Furnish implements Attractive, Serializable {
    DESIGNER{
        @Override
        public int levelAttractive() {
            return 3;
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
            return 1;
        }
    };
}