package com.abn.test.recipemanagement.repository;

import com.abn.test.recipemanagement.model.Recipe;
import com.abn.test.recipemanagement.model.RecipeRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;

public interface RecipeRepository {

    Page<Recipe> findAllRecipe(String recipeId, String recipeName, Integer servings, String includedIngredient,
                               String excludedIngredient, String category, String cuisine, Boolean vegetarian,
                               String keyword, PageRequest pageable);

    Recipe addRecipe(Recipe recipe);

    HttpStatus deleteRecipe(String recipeId);

    Recipe updateRecipeField(String recipeId, String fieldName, String newValue);

    Recipe updateRecipe(Recipe recipe);
}
