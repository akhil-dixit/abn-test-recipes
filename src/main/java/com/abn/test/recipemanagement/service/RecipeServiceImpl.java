package com.abn.test.recipemanagement.service;

import com.abn.test.recipemanagement.model.NonVegIngredients;
import com.abn.test.recipemanagement.model.Recipe;
import com.abn.test.recipemanagement.model.RecipeRequest;
import com.abn.test.recipemanagement.model.UpdateRecipe;
import com.abn.test.recipemanagement.repository.RecipeRepository;
import com.abn.test.recipemanagement.utils.Constants;
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
        logger.info("Fetching all recipes");
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
        logger.info("Adding new recipe: " + recipe.getRecipeName());
        return recipeRepository.addRecipe(recipe);
    }

    @Override
    public HttpStatus deleteRecipe(String recipeId) {
        logger.info("Deleting the recipe with id: " + recipeId);
        return recipeRepository.deleteRecipe(recipeId);
    }

    @Override
    public Recipe updateRecipeField(String recipeId, String fieldName, String newValue) {
        if (fieldName.equalsIgnoreCase(Constants.VEGETARIAN) ||
                fieldName.equalsIgnoreCase(Constants.UPDATEDAT) ||
                fieldName.equalsIgnoreCase(Constants.CREATEDAT) ||
                fieldName.equalsIgnoreCase(Constants.RECIPEID)
        ){
            throw new IllegalArgumentException(fieldName + ": this field cannot be modified");
        }
        else
            logger.info("Updating " + fieldName + " for recipe " + recipeId);
            return recipeRepository.updateRecipeField(recipeId,fieldName,newValue);
    }

    public Recipe updateRecipe(UpdateRecipe updateRecipe) {
        Recipe recipe = new Recipe(updateRecipe.getRecipeId(),updateRecipe.getRecipeName(),
                updateRecipe.getOwnerName(),updateRecipe.getServings(),updateRecipe.getIngredients(),
                updateRecipe.getInstructions(),updateRecipe.getPreparationTime(),
                updateRecipe.getCategory(),updateRecipe.getCuisine(),true,LocalDateTime.now(),LocalDateTime.now());
        if(Arrays.stream(NonVegIngredients.values())
                .map(NonVegIngredients::getValue)
                .anyMatch(updateRecipe.getIngredients()::contains)){
            recipe.setVegetarian(false);
        }

        logger.info("Updating the recipe with id: " + recipe.getRecipeId());
        return recipeRepository.updateRecipe(recipe);
    }
}
