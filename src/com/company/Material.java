package com.company;

import java.util.HashMap;
import java.util.Map;

public class Material {
    public enum Type {
        ORE(1), SMELT(1), CRAFT(1);

        float mult;

        Type(float mult) {
            this.mult = mult;
        }

        public void setMult(float mult) {
            this.mult = mult;
        }

        public float getMult() {
            return mult;
        }
    }

    float initialAmount;
    float productionTime;
    float startProductionTime = 0;
    Map<String, Float> requirements = new HashMap<>();
    Type type;
    int shouldStart = 1;
    private String name;

    public Material(float initialAmount, float productionTime, Map<String, Float> requirements) {
        this.initialAmount = initialAmount;
        this.productionTime = productionTime;
        this.requirements = requirements;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public Material(float initialAmount, float productionTime) {
        this.initialAmount = initialAmount;
        this.productionTime = productionTime;
    }

    public void addMaterialToRecipe(Material material, float amount) {
        requirements.put(material.getName().split("_")[0], amount);
    }

    public void setInitialAmount(float initialAmount) {
        this.initialAmount = initialAmount;
    }

    public void setProductionTime(float productionTime) {
        this.productionTime = productionTime;
    }

    public void setStartProductionTime(float startProductionTime) {
        this.startProductionTime = startProductionTime;
    }

    public void setRequirements(Map<String, Float> requirements) {
        this.requirements = requirements;
    }

    public void setShouldStart(int shouldStart) {
        this.shouldStart = shouldStart;
    }

    public String getName() {
        return name;
    }
}
