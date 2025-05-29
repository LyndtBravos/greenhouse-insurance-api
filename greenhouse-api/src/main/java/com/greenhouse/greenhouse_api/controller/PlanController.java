package com.greenhouse.greenhouse_api.controller;

import com.greenhouse.greenhouse_api.model.Plan;
import com.greenhouse.greenhouse_api.service.PlanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plan/")
public class PlanController {

    private final PlanService service;

    public PlanController(PlanService planService) {
        service = planService;
    }

    @GetMapping("get/all")
    public ResponseEntity<List<Plan>> getAllPlans() {
        List<Plan> plans = service.getAllPlans();
        return new ResponseEntity<>(plans, plans == null ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }

    @GetMapping("get/plan/{planID}")
    public ResponseEntity<Plan> getClientWithPlanID(@PathVariable int planID) {
        Plan plan = service.findPlanWithPlanID(planID);
        return new ResponseEntity<>(plan, plan == null ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }

    @GetMapping("get/user/{clientID}")
    public ResponseEntity<List<Plan>> getClientWithUserID(@PathVariable int clientID) {
        List<Plan> plans = service.findPlanWithClientID(clientID);
        return new ResponseEntity<>(plans, plans == null ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }

    @PostMapping("createPlan")
    public ResponseEntity<String> createPlan(@RequestBody Plan plan) {
        return service.createPlan(plan);
    }

    @PutMapping("update")
    public ResponseEntity<String> updatePlan(@RequestBody Plan plan) {
        return service.updatePlan(plan);
    }

    @DeleteMapping("delete/{planID}")
    public ResponseEntity<String> deleteClient(@PathVariable int planID){
        return service.deletePlan(planID);
    }
}