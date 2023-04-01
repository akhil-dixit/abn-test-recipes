package com.abn.test.recipemanagement.service;

import com.abn.test.recipemanagement.model.Recipe;
import com.abn.test.recipemanagement.model.RecipeRequest;
import com.abn.test.recipemanagement.model.UpdateRecipe;
import com.abn.test.recipemanagement.repository.RecipeRepository;
import com.abn.test.recipemanagement.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@ActiveProfiles("test")
public class RecipeServiceTest {

    @InjectMocks
    private RecipeServiceImpl recipeService;

    @Mock
    private RecipeRepository recipeRepository;

    private Recipe recipe;

    @BeforeEach
    void setup(){
        recipe = new Recipe("642309a437439177aa87b70b","Chicken curry","Rahul",4,
                Arrays.asList("Sunflower oil", "Onion", "Garlic", "Ginger", "Chicken",
                        "Tomato", "Yogurt", "Coriander", "Almond"),
                "Heat the oil in a flameproof casserole dish or large frying pan over a medium heat. Add the onion and a generous pinch of salt and fry for 8-10 mins, or until the onion has turned golden brown and sticky. Add the garlic and ginger, cooking for a further minute. Chop the chicken into chunky 3cm pieces, add to the pan and fry for 5 mins before stirring through the spice paste and tomatoes, along with 250ml water. Bring to the boil, lower to a simmer and cook on a gentle heat uncovered for 25-30 mins or until rich and slightly reduced. Stir though the yogurt, coriander and ground almonds, season and serve with warm naan or fluffy basmati rice.",
                50,"Main Course","Indian",false, LocalDateTime.now(),LocalDateTime.now()
        );
    }

    @Test
    public void testAddRecipeIsNotVeg() {
        RecipeRequest recipeRequest = new RecipeRequest("Chicken curry","Rahul",4,
                Arrays.asList("Sunflower oil", "Onion", "Garlic", "Ginger", "Chicken",
                        "Tomato", "Yogurt", "Coriander", "Almond"),
                "Heat the oil in a flameproof casserole dish or large frying pan over a medium heat. Add the onion and a generous pinch of salt and fry for 8-10 mins, or until the onion has turned golden brown and sticky. Add the garlic and ginger, cooking for a further minute. Chop the chicken into chunky 3cm pieces, add to the pan and fry for 5 mins before stirring through the spice paste and tomatoes, along with 250ml water. Bring to the boil, lower to a simmer and cook on a gentle heat uncovered for 25-30 mins or until rich and slightly reduced. Stir though the yogurt, coriander and ground almonds, season and serve with warm naan or fluffy basmati rice.",
                50,"Main Course","Indian"
        );

        given(recipeRepository.addRecipe(any(Recipe.class))).willReturn(recipe);

        Recipe recipeResponse = recipeService.addRecipe(recipeRequest);
        assertThat(recipeResponse.isVegetarian()).isFalse();
    }

    @Test
    public void testUpdateRecipeFieldNotAllowed() {
        given(recipeRepository.updateRecipeField(any(),any(),any()))
                .willThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class, () -> {
            recipeService.updateRecipeField(recipe.getRecipeId(),"recipeId","newValue");
        });
    }

    @Test
    public void testUpdateRecipeIsNotVeg() {
        UpdateRecipe updateRecipe = new UpdateRecipe("642309a437439177aa87b70b","Chicken curry","Rahul",4,
                Arrays.asList("Sunflower oil", "Onion", "Garlic", "Ginger", "Chicken",
                        "Tomato", "Yogurt", "Coriander", "Almond"),
                "Heat the oil in a flameproof casserole dish or large frying pan over a medium heat. Add the onion and a generous pinch of salt and fry for 8-10 mins, or until the onion has turned golden brown and sticky. Add the garlic and ginger, cooking for a further minute. Chop the chicken into chunky 3cm pieces, add to the pan and fry for 5 mins before stirring through the spice paste and tomatoes, along with 250ml water. Bring to the boil, lower to a simmer and cook on a gentle heat uncovered for 25-30 mins or until rich and slightly reduced. Stir though the yogurt, coriander and ground almonds, season and serve with warm naan or fluffy basmati rice.",
                50,"Main Course","Indian"
        );

        given(recipeRepository.updateRecipe(any(Recipe.class))).willReturn(recipe);

        Recipe recipeResponse = recipeService.updateRecipe(updateRecipe);
        assertThat(recipeResponse.isVegetarian()).isFalse();
    }
}
