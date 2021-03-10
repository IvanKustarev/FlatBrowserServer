package L6Server.ApartmentDescription;

public enum Furnish implements Attractive {
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