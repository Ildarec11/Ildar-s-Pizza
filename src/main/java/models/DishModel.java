package models;

import java.io.File;

public class DishModel {
    private int id;
    public String name;
    public String description;
    private int cost;
    private File image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }
}
