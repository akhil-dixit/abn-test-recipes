package com.abn.test.recipemanagement.service;

import com.abn.test.recipemanagement.model.NonVegIngredients;
import com.abn.test.recipemanagement.model.Recipe;
import com.abn.test.recipemanagement.model.RecipeRequest;
import com.abn.test.recipemanagement.repository.RecipeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;

@Service
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    private final Logger logger = LoggerFactory.getLogger(RecipeServiceImpl.class);

    @Override
    public Page<Recipe> findAllRecipe(String recipeId, String recipeName, Integer servings, String includedIngredient,
                                      String excludedIngredient, String category, String cuisine, Boolean vegetarian,
                                      String keyword, int page, int size, String sortBy, Sort.Direction direction){
        return recipeRepository.findAllRecipe(
                recipeId, recipeName, servings, includedIngredient, excludedIngredient, category, cuisine,
                vegetarian, keyword, PageRequest.of(page, size, Sort.by(direction, sortBy))
        );
    }

    @Override
    public Recipe addRecipe(RecipeRequest recipeRequest) {
        Recipe recipe = new Recipe(recipeRequest.getRecipeName(),
                recipeRequest.getOwnerName(),recipeRequest.getServings(),recipeRequest.getIngredients(),
                recipeRequest.getInstructions(),recipeRequest.getPreparationTime(),
                recipeRequest.getCategory(),recipeRequest.getCuisine(),true);

        if(Arrays.stream(NonVegIngredients.values())
                .map(NonVegIngredients::getValue)
                .anyMatch(recipeRequest.getIngredients()::contains)){
            recipe.setVegetarian(false);
        }
        recipe.setCreatedAt(LocalDateTime.now());
        recipe.setUpdatedAt(LocalDateTime.now());
        return recipeRepository.addRecipe(recipe);
    }

    @Override
    public HttpStatus deleteRecipe(String recipeId) {
        return recipeRepository.deleteRecipe(recipeId);
    }

    @Override
    public Recipe updateRecipeField(String recipeId, String fieldName, String newValue) {
        if (fieldName.equalsIgnoreCase("vegetarian")){
            throw new IllegalArgumentException(fieldName + ": this field cannot be modified");
        }
        else
            return recipeRepository.updateRecipeField(recipeId,fieldName,newValue);
    }

    public Recipe updateRecipe(Recipe recipe) {
        if(Arrays.stream(NonVegIngredients.values())
                .map(NonVegIngredients::getValue)
                .anyMatch(recipe.getIngredients()::contains)){
            recipe.setVegetarian(false);
        }
        else
            recipe.setVegetarian(true);

        recipe.setCreatedAt(LocalDateTime.now());
        recipe.setUpdatedAt(LocalDateTime.now());
        return recipeRepository.updateRecipe(recipe);
    }
}
