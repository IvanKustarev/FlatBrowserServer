package CommonClasses;

import java.io.Serializable;

public class DataBlock extends AbstractDataBlock implements Serializable {
    @Override
    public boolean startProcessingCommand(AbstractDataBlock answer) {
        return false;
    }
}
