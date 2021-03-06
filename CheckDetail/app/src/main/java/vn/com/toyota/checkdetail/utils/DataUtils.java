package vn.com.toyota.checkdetail.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import vn.com.toyota.checkdetail.config.AppConfig;
import vn.com.toyota.checkdetail.model.Error;
import vn.com.toyota.checkdetail.model.ErrorPart;
import vn.com.toyota.checkdetail.model.ErrorPosition;
import vn.com.toyota.checkdetail.model.Product;

/**
 * Created by Do Hoang on 4/11/2017.
 */

public class DataUtils {

    public static Product createProduct() {
        String seq1 = RandomStringUtils.randomNumeric(1);
        String seq2 = RandomStringUtils.randomAlphabetic(1);
        String seq3 = RandomStringUtils.randomNumeric(9);
        String sequence = seq1 + seq2 + seq3;
        String grade = RandomStringUtils.randomAlphabetic(2);

        //TODO: data fixed
        sequence = "1H041832404";
        grade = "CZ";
        Product product = new Product(sequence, grade, createErrorPositionList());

        return product;
    }

    public static List<ErrorPosition> createErrorPositionList() {
        List<ErrorPosition> errorPositions = new ArrayList<>();
        errorPositions.add(new ErrorPosition("SF-RH", createErrorPartList()));
        errorPositions.add(new ErrorPosition("SF-LH", createErrorPartList()));
        errorPositions.add(new ErrorPosition("SF-RR", createErrorPartList()));
        errorPositions.add(new ErrorPosition("SF-FR", createErrorPartList()));
        errorPositions.add(new ErrorPosition("EG", createErrorPartList()));
        errorPositions.add(new ErrorPosition("AI1", createErrorPartList()));
        errorPositions.add(new ErrorPosition("AI2", createErrorPartList()));
        int maxSize = AppConfig.ERROR_POSITION_COL * AppConfig.ERROR_POSITION_ROW;
        for (int i = errorPositions.size(); i < maxSize; i++) {
            errorPositions.add(new ErrorPosition("", createErrorPartList()));
        }
        return errorPositions;
    }

    public static List<ErrorPart> createErrorPartList() {
        List<ErrorPart> errorParts = new ArrayList<>();
        try {
            String directory = FileUtils.getDirectory();
            errorParts.add(new ErrorPart("Step_1", "1. Bề mặt Door FR", directory + "/main_01.jpg", directory + "/guide_01.jpg", createErrorList()));
            errorParts.add(new ErrorPart("Step_2", "2. Bề mặt Opening FR", directory + "/main_02.jpg", directory + "/guide_02.jpg", createErrorList()));
            errorParts.add(new ErrorPart("Step_3", "3. Bề mặt Door3 FR", "", "",createErrorList()));
            errorParts.add(new ErrorPart("Step_4", "4. Bề mặt Door4 FR", "", "",createErrorList()));
            errorParts.add(new ErrorPart("Step_5", "5. Bề mặt Door5 FR", "", "",createErrorList()));
            errorParts.add(new ErrorPart("Step_6", "6. Bề mặt Door6 RR", "", "",createErrorList()));
            errorParts.add(new ErrorPart("Step_7", "7. Bề mặt Door7 FR", "", "",createErrorList()));
            errorParts.add(new ErrorPart("Step_8", "8. Bề mặt Door8 RR", "", "",createErrorList()));
            errorParts.add(new ErrorPart("Step_9", "9. Bề mặt Door9 RR", "", "",createErrorList()));
            errorParts.add(new ErrorPart("Step_10", "10. Bề mặt Door10 RR", "", "",createErrorList()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return errorParts;
    }

    public static List<Error> createErrorList() {
        List<Error> errors = new ArrayList<>();
        errors.add(new Error("S-"));
        errors.add(new Error("CR"));
        errors.add(new Error("PP"));
        return errors;
    }
}