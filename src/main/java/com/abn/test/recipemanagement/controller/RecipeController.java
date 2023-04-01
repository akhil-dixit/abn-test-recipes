package com.abn.test.recipemanagement.controller;

import com.abn.test.recipemanagement.model.Recipe;
import com.abn.test.recipemanagement.model.RecipeRequest;
import com.abn.test.recipemanagement.model.UpdateRecipe;
import com.abn.test.recipemanagement.service.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Recipe Management")
@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Find all recipes",
            description = "Find all recipes with or without various parameters"
    )
    public Page<Recipe> findAllRecipe(
            @RequestParam(required = false)
            @Parameter(description = "Used to filter recipes by id")
            String recipeId,
            @RequestParam(required = false)
            @Parameter(description = "Used to filter recipes by name")
            String recipeName,
            @RequestParam(required = false)
            @Parameter(description = "Used to filter recipes by servings")
            Integer servings,
            @RequestParam(required = false)
            @Parameter(description = "Used to filter recipes if ingredient is included")
            String includedIngredient,
            @RequestParam(required = false)
            @Parameter(description = "Used to filter recipes if ingredient is excluded")
            String excludedIngredient,
            @RequestParam(required = false)
            @Parameter(description = "Used to filter recipes by category")
            String category,
            @RequestParam(required = false)
            @Parameter(description = "Used to filter recipes by cuisine")
            String cuisine,
            @RequestParam(required = false)
            @Parameter(description = "Used to filter recipes based on veg or not")
            Boolean vegetarian,
            @RequestParam(required = false)
            @Parameter(description = "Used to filter recipes based on a 'text' in instructions")
            String keyword,
            @RequestParam(required = false, defaultValue = "0")
            @Parameter(description = "Displaying page")
            int page,
            @RequestParam(required = false, defaultValue = "10")
            @Parameter(description = "Page size")
            int size,
            @RequestParam(required = false, defaultValue = "createdAt")
            @Parameter(description = "Sort by using creationDate")
            String sortBy,
            @RequestParam(required = false, defaultValue = "DESC")
            @Parameter(description = "Sort by using direction")
            Sort.Direction direction
    ){
        return recipeService.findAllRecipe(recipeId,recipeName,servings,includedIngredient,excludedIngredient,
                category,cuisine,vegetarian,keyword,page,size,sortBy,direction);
    }

    @PostMapping(value = "/addRecipe",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Add a recipe",
            description = "Add a recipe with body. Returns created recipe with Id"
    )
    public Recipe addRecipe(@RequestBody RecipeRequest recipeRequest) {
        return recipeService.addRecipe(recipeRequest);
    }

    @DeleteMapping(value = "/deleteRecipe/{recipeId}")
    @Operation(
            summary = "Delete a recipe",
            description = "Delete a recipe with Id"
    )
    public HttpStatus deleteRecipe(
            @Parameter(description = "RecipeId for which the recipe will be deleted")
            @PathVariable String recipeId
    ) {
        return recipeService.deleteRecipe(recipeId);
    }

    @GetMapping(value = "/updateRecipeField",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Update a recipe field",
            description = "Update any field of the recipe with recipeId"
    )
    public Recipe updateRecipeField(
            @RequestParam
            @Parameter(description = "Filter the recipe by Id")
            String recipeId,
            @RequestParam
            @Parameter(description = "Field name which needs to be updated")
            String fieldName,
            @RequestParam
            @Parameter(description = "Value to be updated in the database")
            String newValue
            ) {
        return recipeService.updateRecipeField(recipeId,fieldName,newValue);
    }

    @PutMapping(value = "/updateRecipe",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Update a recipe",
            description = "Update the entire recipe with recipeId"
    )
    public Recipe updateRecipe(@RequestBody UpdateRecipe updateRecipe) {
        return recipeService.updateRecipe(updateRecipe);
    }
}
