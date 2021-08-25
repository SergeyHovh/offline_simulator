package com.company;

import java.util.ArrayList;
import java.util.HashMap;

public class OfflineSimulator {
    private final int offlineTime;
    private ArrayList<Material> materials;
    private float multiplier = 1;

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
                HashMap<Material, Integer> hasReq = new HashMap<>();
                for (Material reqMaterial : material.requirements.keySet()) {
                    float required = material.requirements.get(reqMaterial);
                    float owns = reqMaterial.initialAmount;
                    int hasReqMat = H(owns - required);
                    hasReq.put(reqMaterial, hasReqMat);
                }
                int material_continue_work;

                int has_all_materials = 1;

                // if has requirements, not an ore
                if (material.requirements.size() > 0) {
                    if (material.shouldStart == 0) {
                        material.startProductionTime += material.getType().getMult();
                        if (material.startProductionTime >= material.productionTime) {
                            material.startProductionTime = 0;
                            material.shouldStart = 1;
                        }
                    }
                    for (Material req : material.requirements.keySet()) {
                        has_all_materials *= hasReq.get(req);
                    }
                    material_continue_work = (material.shouldStart ^ 1) | has_all_materials;
                } else {
                    // if ore - produce
                    material_continue_work = 1;
                }

                // income
                material.initialAmount += (material.getType().getMult() / material.productionTime) * material_continue_work;

                // spending
                int a = material.shouldStart;
                for (Material req : material.requirements.keySet()) {
                    a *= hasReq.get(req);
                }

                for (Material req : material.requirements.keySet()) {
                    req.initialAmount -= a * material.requirements.get(req);
                }
                material.shouldStart *= has_all_materials ^ 1;
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
