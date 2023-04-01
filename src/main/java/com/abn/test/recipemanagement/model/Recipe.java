package com.abn.test.recipemanagement.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "recipes")
@Getter
@Setter
public class Recipe {

    @Id
    private String recipeId;
    private String recipeName;
    private String ownerName;
    private int servings;
    private List<String> ingredients;
    private String instructions;
    private int preparationTime;
    private String category;
    private String cuisine;
    private boolean vegetarian;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Recipe(String recipeName, String ownerName, int servings, List<String> ingredients, String instructions, int preparationTime, String category, String cuisine, boolean vegetarian) {
        this.recipeName = recipeName;
        this.ownerName = ownerName;
        this.servings = servings;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.preparationTime = preparationTime;
        this.category = category;
        this.cuisine = cuisine;
        this.vegetarian = vegetarian;
    }

    public Recipe(String recipeId, String recipeName, String ownerName, int servings, List<String> ingredients, String instructions, int preparationTime, String category, String cuisine, boolean vegetarian, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.ownerName = ownerName;
        this.servings = servings;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.preparationTime = preparationTime;
        this.category = category;
        this.cuisine = cuisine;
        this.vegetarian = vegetarian;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Recipe() {}

    @Override
    public String toString() {
        return "Recipe{" +
                "recipeId='" + recipeId + '\'' +
                ", recipeName='" + recipeName + '\'' +
                ", OwnerName='" + ownerName + '\'' +
                ", servings=" + servings +
                ", ingredients=" + ingredients +
                ", instructions='" + instructions + '\'' +
                ", preparationTime=" + preparationTime +
                ", category='" + category + '\'' +
                ", cuisine='" + cuisine + '\'' +
                ", vegetarian=" + vegetarian +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
