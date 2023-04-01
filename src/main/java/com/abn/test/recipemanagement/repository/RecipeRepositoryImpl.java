package com.abn.test.recipemanagement.repository;

import com.abn.test.recipemanagement.model.Recipe;
import com.abn.test.recipemanagement.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.FindAndReplaceOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.core.query.Query;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class RecipeRepositoryImpl implements RecipeRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Page<Recipe> findAllRecipe(String recipeId, String recipeName, Integer servings, String includedIngredient,
                                      String excludedIngredient, String category, String cuisine, Boolean vegetarian,
                                      String keyword, PageRequest pageable){
        Query query = new Query().with(pageable);
        if(null != recipeId)
            query.addCriteria(Criteria.where(Constants.RECIPEID).is(recipeId));
        if(null != recipeName)
            query.addCriteria(Criteria.where(Constants.RECIPENAME).is(recipeName));
        if(null != servings)
            query.addCriteria(Criteria.where(Constants.SERVINGS).is(servings));
        if(null != category)
            query.addCriteria(Criteria.where(Constants.CATEGORY).is(category));
        if(null != cuisine)
            query.addCriteria(Criteria.where(Constants.CUISINE).is(cuisine));
        if(null != vegetarian)
            query.addCriteria(Criteria.where(Constants.VEGETARIAN).is(vegetarian));
        if(null != keyword)
            query.addCriteria(Criteria.where(Constants.INSTRUCTIONS).regex(keyword,"i"));
        if(null != includedIngredient)
            query.addCriteria(Criteria.where(Constants.INGREDIENTS).in(includedIngredient));
        if(null != excludedIngredient)
            query.addCriteria(Criteria.where(Constants.INGREDIENTS).nin(excludedIngredient));
        List<Recipe> recipeList = mongoTemplate.find(query, Recipe.class);
        return PageableExecutionUtils.getPage(
                recipeList,
                pageable,
                () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), Recipe.class));
    }

    @Override
    public Recipe addRecipe(Recipe recipe) {
        mongoTemplate.save(recipe);
        return recipe;
    }

    @Override
    public HttpStatus deleteRecipe(String recipeId) {
        Query query = new Query();
        query.addCriteria(Criteria.where(Constants.RECIPEID).is(recipeId));
        Recipe recipe = mongoTemplate.findOne(query, Recipe.class);
        if(null != recipe){
            mongoTemplate.remove(recipe);
            return HttpStatus.OK;
        }
        else
            return HttpStatus.NOT_FOUND;
    }

    public Recipe updateRecipeField(String recipeId, String fieldName, String newValue) {
        Query query = new Query();
        query.addCriteria(Criteria.where(Constants.RECIPEID).is(recipeId));
        Update updateField = new Update();
        if (fieldName.equalsIgnoreCase(Constants.SERVINGS) ||
                fieldName.equalsIgnoreCase(Constants.PREPARATIONTIME)){
            int newIntValue = Integer.parseInt(newValue);
            updateField.set(fieldName,newIntValue);
        }
        else
            updateField.set(fieldName,newValue);

        updateField.set(Constants.UPDATEDAT,LocalDateTime.now());

        FindAndModifyOptions options = new FindAndModifyOptions();
        options.returnNew(true);

        return mongoTemplate.findAndModify(query,updateField,options,Recipe.class);

    }

    public Recipe updateRecipe(Recipe recipe) {
        Query query = new Query();
        query.addCriteria(Criteria.where(Constants.RECIPEID).is(recipe.getRecipeId()));
        FindAndReplaceOptions options = new FindAndReplaceOptions();
        options.returnNew();

        return mongoTemplate.findAndReplace(query,recipe,options);
    }
}
