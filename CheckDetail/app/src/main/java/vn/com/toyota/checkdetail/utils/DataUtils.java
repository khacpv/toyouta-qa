package vn.com.toyota.checkdetail.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import vn.com.toyota.checkdetail.config.AppConfig;
import vn.com.toyota.checkdetail.model.ErrorPosition;

/**
 * Created by Do Hoang on 4/11/2017.
 */

public class DataUtils {

    public static final String[] errors = {"SF-RH", "SF-LH", "SF-RR", "SF-FR", "EG", "AI1", "AI2",
            "KU-RH", "KU-LH", "BS", "WH", "MU1", "SC5", "TT-RR", "TT-FR"};

    public static List<ErrorPosition> getErrorPositionList() {
        List<ErrorPosition> list = new ArrayList<>();
        int maxSize = AppConfig.ERROR_POSITION_COL * AppConfig.ERROR_POSITION_ROW;
        Random r = new Random();
        int size = r.nextInt(maxSize - 1) + 1;
        for (int i = 0; i < maxSize; i++) {
            if (i >= size) {
                list.add(new ErrorPosition(""));
                continue;
            }
            int index = r.nextInt(maxSize - 1) + 1;
            String code = errors[index];
            list.add(new ErrorPosition(code));
        }
        return list;
    }
}
