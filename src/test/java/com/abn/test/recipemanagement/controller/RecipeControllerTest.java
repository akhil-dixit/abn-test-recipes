package com.abn.test.recipemanagement.controller;

import com.abn.test.recipemanagement.model.Recipe;
import com.abn.test.recipemanagement.model.RecipeRequest;
import com.abn.test.recipemanagement.model.UpdateRecipe;
import com.abn.test.recipemanagement.service.RecipeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.given;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class RecipeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private RecipeService recipeService;

    private List<Recipe> recipeList = new ArrayList<>();
    private Recipe recipe;

    @BeforeEach
    void setup(){
        recipe = new Recipe("642316f35e2e45154d19a92e","Stroopwafels","Karin Engelbrecht",20,
                Arrays.asList("Active dry yeast", "Lukewarm milk", "Unsalted butter", "Superfine sugar", "Dutch cake flour",
                        "Cooking spray", "Salt", "Dutch molasses syrup", "Brown sugar", "Ground cinnamon"),
                "Gather the ingredients. In a medium bowl, dissolve the yeast in the lukewarm milk. If using active dry yeast, dissolve in 1/4 cup lukewarm milk and let the mixture sit 10 minutes to hydrate. Add the butter and superfine sugar. Stir to combine. Whisk the flour and salt together, then stir into the yeast-butter-sugar mixture. Cover the dough with a warm, moist dish towel and allow to rise in a warm place for 1 hour. Add the golden syrup to a small saucepan on medium-low heat. when the syrup is warm, stir in the brown sugar, butter, and cinnamon. Stir continuously until the sugar is melted. Remove from the heat and set aside to cool to lukewarm. Form dough into balls about 1 1/2-inches in diameter. Transfer the dough balls to a parchment-lined or greased cookie sheet; they should not touch each other. Cover with a warm, moist dish towel and allow to rise for 15 minutes. Heat the waffle iron and spray both sides lightly with cooking spray. Place 1 dough ball in the iron and bake until golden. This should take about 1 1/2 minutes in an electric waffle iron or 2 to 3 minutes in a stovetop version. Working quickly and use a clean kitchen towel to protect your hands from the heat, cut the waffles in half horizontally (a serrated bread knife works best). Spoon and spread about 1 tablespoon of syrup filling in the center of the waffle. Cover with the second sandwich half, pressing down lightly to help spread the filling inside. Let cool slightly, then serve.",
                145,"Snack","Dutch",true, LocalDateTime.now(),LocalDateTime.now()
                );
    }

    @Test
    public void testFindAllRecipe() throws Exception {
        recipeList.add(recipe);
        Page<Recipe> recipePageMockResponse = new PageImpl<>(recipeList);

        given(recipeService.findAllRecipe(any(),any(),anyInt(),any(),any(),any(),any(),anyBoolean(),any(),anyInt(),anyInt(),any(),any()))
                .willReturn(recipePageMockResponse);


        mockMvc.perform(get("/api/recipes")).andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testFindAllRecipeWithParam() throws Exception {
        recipeList.add(recipe);
        Page<Recipe> recipePageMockResponse = new PageImpl<>(recipeList);

        given(recipeService.findAllRecipe(any(),any(),anyInt(),any(),any(),any(),any(),anyBoolean(),any(),anyInt(),anyInt(),any(),any()))
                .willReturn(recipePageMockResponse);


        mockMvc.perform(get("/api/recipes")
                        .param("recipeId",recipe.getRecipeId())
                        .param("recipeName",recipe.getRecipeName())
                        .param("category",recipe.getCategory())
                        .param("cuisine",recipe.getCuisine())
                        .param("includedIngredient","Salt")
                        .param("keyword","yeast")
                ).andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testAddRecipe() throws Exception {
        RecipeRequest recipeRequest = new RecipeRequest("Stroopwafels","Karin Engelbrecht",20,
                Arrays.asList("Active dry yeast", "Lukewarm milk", "Unsalted butter", "Superfine sugar", "Dutch cake flour",
                        "Cooking spray", "Salt", "Dutch molasses syrup", "Brown sugar", "Ground cinnamon"),
                "Gather the ingredients. In a medium bowl, dissolve the yeast in the lukewarm milk. If using active dry yeast, dissolve in 1/4 cup lukewarm milk and let the mixture sit 10 minutes to hydrate. Add the butter and superfine sugar. Stir to combine. Whisk the flour and salt together, then stir into the yeast-butter-sugar mixture. Cover the dough with a warm, moist dish towel and allow to rise in a warm place for 1 hour. Add the golden syrup to a small saucepan on medium-low heat. when the syrup is warm, stir in the brown sugar, butter, and cinnamon. Stir continuously until the sugar is melted. Remove from the heat and set aside to cool to lukewarm. Form dough into balls about 1 1/2-inches in diameter. Transfer the dough balls to a parchment-lined or greased cookie sheet; they should not touch each other. Cover with a warm, moist dish towel and allow to rise for 15 minutes. Heat the waffle iron and spray both sides lightly with cooking spray. Place 1 dough ball in the iron and bake until golden. This should take about 1 1/2 minutes in an electric waffle iron or 2 to 3 minutes in a stovetop version. Working quickly and use a clean kitchen towel to protect your hands from the heat, cut the waffles in half horizontally (a serrated bread knife works best). Spoon and spread about 1 tablespoon of syrup filling in the center of the waffle. Cover with the second sandwich half, pressing down lightly to help spread the filling inside. Let cool slightly, then serve.",
                145,"Snack","Dutch"
        );

        given(recipeService.addRecipe(any(RecipeRequest.class))).willReturn(recipe);

        mockMvc.perform(post("/api/recipes/addRecipe").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(recipeRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recipeName").value(recipe.getRecipeName()))
                .andDo(print());
    }

    @Test
    public void testDeleteRecipe() throws Exception {
        given(recipeService.deleteRecipe(any())).willReturn(HttpStatus.OK);

        mockMvc.perform(delete("/api/recipes/deleteRecipe/642316f35e2e45154d19a92e"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testUpdateRecipeField() throws Exception {
        String newValue = "Akhil";
        recipe.setOwnerName(newValue);
        given(recipeService.updateRecipeField(any(),any(),any())).willReturn(recipe);

        mockMvc.perform(get("/api/recipes/updateRecipeField")
                        .param("recipeId",recipe.getRecipeId())
                        .param("fieldName","ownerName")
                        .param("newValue",newValue))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testUpdateRecipeFieldForIntField() throws Exception {
        String newValue = "5";
        recipe.setServings(Integer.parseInt(newValue));
        given(recipeService.updateRecipeField(any(),any(),any())).willReturn(recipe);

        mockMvc.perform(get("/api/recipes/updateRecipeField")
                        .param("recipeId",recipe.getRecipeId())
                        .param("fieldName","servings")
                        .param("newValue",newValue))
                .andExpect(status().isOk())
                .andReturn();
    }
    
    @Test
    public void testUpdateRecipe() throws Exception {
        UpdateRecipe updateRecipe = new UpdateRecipe("642316f35e2e45154d19a92e","Stroopwafels","Karin Engelbrecht",20,
                Arrays.asList("Active dry yeast", "Lukewarm milk", "Unsalted butter", "Superfine sugar", "Dutch cake flour",
                        "Cooking spray", "Salt", "Dutch molasses syrup", "Brown sugar", "Ground cinnamon"),
                "Gather the ingredients. In a medium bowl, dissolve the yeast in the lukewarm milk. If using active dry yeast, dissolve in 1/4 cup lukewarm milk and let the mixture sit 10 minutes to hydrate. Add the butter and superfine sugar. Stir to combine. Whisk the flour and salt together, then stir into the yeast-butter-sugar mixture. Cover the dough with a warm, moist dish towel and allow to rise in a warm place for 1 hour. Add the golden syrup to a small saucepan on medium-low heat. when the syrup is warm, stir in the brown sugar, butter, and cinnamon. Stir continuously until the sugar is melted. Remove from the heat and set aside to cool to lukewarm. Form dough into balls about 1 1/2-inches in diameter. Transfer the dough balls to a parchment-lined or greased cookie sheet; they should not touch each other. Cover with a warm, moist dish towel and allow to rise for 15 minutes. Heat the waffle iron and spray both sides lightly with cooking spray. Place 1 dough ball in the iron and bake until golden. This should take about 1 1/2 minutes in an electric waffle iron or 2 to 3 minutes in a stovetop version. Working quickly and use a clean kitchen towel to protect your hands from the heat, cut the waffles in half horizontally (a serrated bread knife works best). Spoon and spread about 1 tablespoon of syrup filling in the center of the waffle. Cover with the second sandwich half, pressing down lightly to help spread the filling inside. Let cool slightly, then serve.",
                145,"Snack","Dutch");
        given(recipeService.updateRecipe(any(UpdateRecipe.class))).willReturn(recipe);

        mockMvc.perform(put("/api/recipes/updateRecipe")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(updateRecipe)))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
