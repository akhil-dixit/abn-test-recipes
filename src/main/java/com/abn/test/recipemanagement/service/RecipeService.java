package com.abn.test.recipemanagement.service;

import com.abn.test.recipemanagement.model.Recipe;
import com.abn.test.recipemanagement.model.RecipeRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;

public interface RecipeService {

    Page<Recipe> findAllRecipe(String recipeId, String recipeName, Integer servings, String includedIngredient,
                               String excludedIngredient, String category, String cuisine, Boolean vegetarian,
                               String keyword, int page, int size, String sortBy, Sort.Direction direction);

    Recipe addRecipe(RecipeRequest recipeRequest);

    HttpStatus deleteRecipe(String recipeId);

    Recipe updateRecipeField(String recipeId, String fieldName, String newValue);

    Recipe updateRecipe(Recipe recipe);
}
