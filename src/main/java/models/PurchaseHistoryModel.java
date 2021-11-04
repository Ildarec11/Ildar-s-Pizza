package models;

import java.math.BigDecimal;
import java.util.Date;

public class PurchaseHistoryModel {
    private int userId;
    private int dishId;
    private int discountsPercents;
    private Date purchaseDate;
    private BigDecimal cost;

    public int getDiscountsPercents() {
        return discountsPercents;
    }

    public void setDiscountsPercents(int discountsPercents) {
        this.discountsPercents = discountsPercents;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getDishId() {
        return dishId;
    }

    public void setDishId(int dishId) {
        this.dishId = dishId;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
}
