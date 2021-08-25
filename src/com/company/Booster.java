package com.company;

public class Booster {
    Material.Type type;
    float multiplier;
    int duration;

    public Booster(Material.Type type, float multiplier, int duration) {
        this.type = type;
        this.multiplier = multiplier;
        this.duration = duration;
    }

    public Material.Type getType() {
        return type;
    }

    public void setType(Material.Type type) {
        this.type = type;
    }

    public float getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(float multiplier) {
        this.multiplier = multiplier;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
