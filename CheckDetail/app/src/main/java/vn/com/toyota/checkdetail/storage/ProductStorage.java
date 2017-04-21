package vn.com.toyota.checkdetail.storage;

import vn.com.toyota.checkdetail.model.Error;
import vn.com.toyota.checkdetail.model.ErrorPart;
import vn.com.toyota.checkdetail.model.ErrorPixel;
import vn.com.toyota.checkdetail.model.ErrorPosition;
import vn.com.toyota.checkdetail.model.Product;
import vn.com.toyota.checkdetail.utils.DataUtils;

public class ProductStorage {
    private static ProductStorage instance;

    public static synchronized ProductStorage getInstance() {
        if (instance == null) {
            instance = new ProductStorage();
        }
        return instance;
    }

    private Product mProduct;
    private ErrorPosition mCurrentErrorPosition;
    private ErrorPart mCurrentErrorPart;
    private Error mCurrentError;
    private ErrorPixel mCurrentErrorPixel;

    private ProductStorage() {
        this.mProduct = DataUtils.createProduct();
    }

    public Product getProduct() {
        return mProduct;
    }

    public void setProduct(Product product) {
        mProduct = product;
    }

    public ErrorPosition getCurrentErrorPosition() {
        return mCurrentErrorPosition;
    }

    public void setCurrentErrorPosition(ErrorPosition currentErrorPosition) {
        mCurrentErrorPosition = currentErrorPosition;
    }

    public ErrorPart getCurrentErrorPart() {
        return mCurrentErrorPart;
    }

    public void setCurrentErrorPart(ErrorPart currentErrorPart) {
        mCurrentErrorPart = currentErrorPart;
    }

    public Error getCurrentError() {
        return mCurrentError;
    }

    public void setCurrentError(Error currentError) {
        mCurrentError = currentError;
    }

    public ErrorPixel getCurrentErrorPixel() {
        return mCurrentErrorPixel;
    }

    public void setCurrentErrorPixel(ErrorPixel currentErrorPixel) {
        mCurrentErrorPixel = currentErrorPixel;
    }

    public void clearMemory() {
        for (ErrorPart ep : mCurrentErrorPosition.getErrorParts()) {
            ep.setSelected(false);
        }
        for (Error err : mCurrentErrorPart.getErrors()) {
            err.setSelected(false);
        }
        this.mCurrentErrorPixel = null;
        this.mCurrentError = null;
        this.mCurrentErrorPart = null;
        this.mCurrentErrorPosition = null;
    }
}