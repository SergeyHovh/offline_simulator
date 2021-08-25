package com.company;

import java.util.ArrayList;

public class OfflineSimulatorRunner {
    public static void main(String[] args) {
        ArrayList<Material> allMaterials = new ArrayList<>();

        Material iron = new Material(10, 1);
        iron.setName("iron");
        iron.setType(Material.Type.ORE);

        Material ironBar = new Material(0, 5);
        ironBar.setName("iron bar");
        ironBar.addMaterialToRecipe(iron, 5);
        ironBar.setType(Material.Type.SMELT);

        Material ironBolt = new Material(0, 10);
        ironBolt.setName("iron bolt");
        ironBolt.addMaterialToRecipe(ironBar, 1);
        ironBolt.setType(Material.Type.CRAFT);

        Material copper = new Material(0, 1);
        copper.setName("copper");
        copper.setType(Material.Type.ORE);

        Material copperBar = new Material(0, 5);
        copperBar.setName("copper bar");
        copperBar.addMaterialToRecipe(copper, 5);
        copperBar.setType(Material.Type.SMELT);

        Material plate = new Material(0, 30);
        plate.setName("plate");
        plate.addMaterialToRecipe(ironBolt, 1);
        plate.addMaterialToRecipe(copperBar, 3);
        plate.setType(Material.Type.CRAFT);


        allMaterials.add(iron);
        allMaterials.add(ironBar);
        allMaterials.add(ironBolt);
        allMaterials.add(copper);
        allMaterials.add(copperBar);
        allMaterials.add(plate);

        OfflineSimulator simulator = new OfflineSimulator(70, allMaterials);
//        simulator.addBooster(new Booster(Material.Type.SMELT, 2, 30));
//        simulator.addBooster(new Booster(Material.Type.ORE, 2, 30));
//        simulator.addBooster(new Booster(Material.Type.CRAFT, 2, 30));
        simulator.simulate();

    }
}
