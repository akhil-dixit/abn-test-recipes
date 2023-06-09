package com.abn.test.recipemanagement.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class RecipeRequest {

    private String recipeName;
    private String ownerName;
    private int servings;
    private List<String> ingredients;
    private String instructions;
    private int preparationTime;
    private String category;
    private String cuisine;
}
