package com.company;

import java.util.ArrayList;
import java.util.HashMap;

public class OfflineSimulator {
    private final int offlineTime;
    private ArrayList<Material> materials;

    private ArrayList<Booster> boosters = new ArrayList<>();

    public OfflineSimulator(int offlineTime, ArrayList<Material> materials) {
        this.offlineTime = offlineTime;
        this.materials = materials;
    }

    public void addBooster(Booster booster) {
        boosters.add(booster);
    }

    // magic
    public void simulate() {
        for (int t = 0; t < offlineTime; t++) {
            for (Material material : materials) {
                System.out.print(String.format("%.2f", material.initialAmount) + "\t");

                for (Booster booster : boosters) {
                    if (booster.getType() == material.getType()) {
                        if (t < booster.getDuration()) {
                            material.getType().setMult(booster.multiplier);
                        } else {
                            material.getType().setMult(1);
                        }
                    }
                }
                HashMap<String, Integer> hasReq = new HashMap<>();
                for (String reqMaterial : material.requirements.keySet()) {
                    float required = material.requirements.get(reqMaterial);
                    float owns = 0;
                    for (Material material1 : materials) {
                        String key = reqMaterial.split("_")[0];
                        if (material1.getName().split("_")[0].equals(key)) {
                            owns += Math.floor(material1.initialAmount);
                        }
                    }
                    int hasReqMat = H(owns - required);
                    hasReq.put(reqMaterial, hasReqMat);
                }
                int material_continue_work;

                int has_all_materials = 1;
                int material_already_started = 0;

                // if has requirements, not an ore
                if (material.requirements.size() > 0) {
                    if (material.initialAmount % 1 > 0.0001 && material.initialAmount % 1 < 0.9999) {
                        material_already_started = 1;
                    }

                    if (material.shouldStart == 0) {
                        material.startProductionTime += material.getType().getMult();
                        if (material.startProductionTime >= material.productionTime) {
                            material.startProductionTime = 0;
                            material.shouldStart = 1;
                        }
                    }
                    for (String req : material.requirements.keySet()) {
                        has_all_materials *= hasReq.get(req);
                    }
                    material_continue_work = material.shouldStart ^ 1 | material_already_started | has_all_materials;
                } else {
                    // if ore - produce
                    material_continue_work = 1;
                }

                // income
                if (material.productionTime != -1) { // if producing
                    float produced = (material.getType().getMult() / material.productionTime) * material_continue_work;
                    material.initialAmount += produced;
                }

                // spending
                int a = (has_all_materials & material_already_started) ^ 1;
                for (String req : material.requirements.keySet()) {
                    a *= hasReq.get(req);
                }

                for (String req : material.requirements.keySet()) {
                    if (material.productionTime != -1) {
                        float used = a * material.requirements.get(req);
                        if (used > 0) {
                            for (Material material1 : materials) {
                                String key = req.split("_")[0];
                                if (material1.getName().split("_")[0].equals(key)) {
                                    double floor = (int) material1.initialAmount;
                                    material1.initialAmount -= Math.min(used, floor);
                                    used-=Math.min(used, floor);
                                    if (used <= 0) break;
                                }
                            }
                        }
                    }
                }
                material.shouldStart *= (has_all_materials & material_already_started) ^ 1;
            }
            System.out.println();
        }

        // final print/output, maybe add to the warehouse
//        for (Material material : materials) {
//            System.out.print(String.format("%.2f", material.initialAmount) + "\t");
//        }
    }

    private static int H(float v) {
        return v >= 0 ? 1 : 0;
    }
}
