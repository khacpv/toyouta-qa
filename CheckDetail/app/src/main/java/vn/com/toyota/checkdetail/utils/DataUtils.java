package vn.com.toyota.checkdetail.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import vn.com.toyota.checkdetail.config.AppConfig;
import vn.com.toyota.checkdetail.model.ErrorPosition;
import vn.com.toyota.checkdetail.model.Product;

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
            int index = r.nextInt(errors.length - 1) + 1;
            String code = errors[index];
            list.add(new ErrorPosition(code));
        }
        return list;
    }

    public static Product getProduct() {
        String seq1 = RandomStringUtils.randomNumeric(1);
        String seq2 = RandomStringUtils.randomAlphabetic(1);
        String seq3 = RandomStringUtils.randomNumeric(9);
        String sequence = seq1 + seq2 + seq3;
        String grade = RandomStringUtils.randomAlphabetic(2);

        return new Product(sequence, grade);
    }
}
