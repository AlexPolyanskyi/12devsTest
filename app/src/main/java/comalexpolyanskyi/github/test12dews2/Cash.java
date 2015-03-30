package comalexpolyanskyi.github.test12dews2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Алексей on 30.03.2015.
 */
public class Cash {
    static private ArrayList<HashMap> result = null;
    static public void putResult(ArrayList r) {
        result = r;
    };
    static public ArrayList getResult() {
        return result;
    };
}
