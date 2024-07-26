package Praktikum.models;
import lombok.Data;

@Data
public class Order {
    private String[] ingredients;
    private String _id;
    private String status;
    private int number;
    private String createdAt;
    private String updatedAt;
}
