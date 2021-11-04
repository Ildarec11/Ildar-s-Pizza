package models;

public class BoughtDish {

    private DishModel dishModel;
    private PurchaseHistoryModel purchaseHistoryModel;

    public DishModel getDishModel() {
        return dishModel;
    }

    public void setDishModel(DishModel dishModel) {
        this.dishModel = dishModel;
    }

    public PurchaseHistoryModel getPurchaseHistoryModel() {
        return purchaseHistoryModel;
    }

    public void setPurchaseHistoryModel(PurchaseHistoryModel purchaseHistoryModel) {
        this.purchaseHistoryModel = purchaseHistoryModel;
    }
}
