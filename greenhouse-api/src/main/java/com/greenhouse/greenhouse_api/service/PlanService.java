package com.greenhouse.greenhouse_api.service;

import com.greenhouse.greenhouse_api.model.Plan;
import com.greenhouse.greenhouse_api.repository.PlanRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanService {

    @Autowired
    private PlanRepo planRepo;

    public List<Plan> getAllPlans() {
        return planRepo.findAll();
    }

    public Plan findPlanWithPlanID(int planID) {
        return planRepo.findPlanWithPlanID(planID);
    }

    public List<Plan> findPlanWithClientID(int userID) {
        return planRepo.findPlanWithClientID(userID);
    }

    public ResponseEntity<String> createPlan(Plan plan) {
        return planRepo.insert(plan);
    }

    public ResponseEntity<String> updatePlan(Plan plan) {
        return planRepo.update(plan);
    }

    public ResponseEntity<String> deletePlan(int planId) {
        return planRepo.delete(planId);
    }
}