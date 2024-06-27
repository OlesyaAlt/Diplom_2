package Praktikum.models;
import lombok.Data;

import java.util.List;

@Data
public class Ingredient {
    private List<String> ingredients;

    public Ingredient(List<String> ingredients) {
       this.ingredients = ingredients;
    }
}
