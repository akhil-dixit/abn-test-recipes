package com.abn.test.recipemanagement.model;

public enum NonVegIngredients {
    EGG ("Egg"),
    FISH ("Fish"),
    CHICKEN ("Chicken"),
    PIG ("Pig"),
    SHRIMP ("Shrimp"),
    HAM ("Ham"),
    DUCK ("Duck"),
    BEEF ("Beef"),
    BACON ("Bacon"),
    LAMB("Lamb"),
    GOAT ("Goat"),
    PORK ("Pork"),
    STEAK ("Steak"),
    SAUSAGE ("Sausage"),
    OCTOPUS ("Octopus"),
    PEPPERONI ("Pepperoni"),
    MEAT ("Meat"),
    TUNA ("Tuna"),
    MUTTON ("Mutton"),
    SALMON ("Salmon"),
    PRAWN ("Prawn");

    private final String value;

    NonVegIngredients(String value) {
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
