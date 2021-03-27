package CommonClasses.ApartmentDescription;

import java.io.Serializable;

public enum View implements Attractive , Serializable {
    BAD{
        @Override
        public int levelAttractive() {
            return 1;
        }
    },
    NORMAL {
        @Override
        public int levelAttractive() {
            return 2;
        }
    },
    GOOD {
        @Override
        public int levelAttractive() {
            return 3;
        }
    },
    TERRIBLE {
        @Override
        public int levelAttractive() {
            return 0;
        }
    };
}