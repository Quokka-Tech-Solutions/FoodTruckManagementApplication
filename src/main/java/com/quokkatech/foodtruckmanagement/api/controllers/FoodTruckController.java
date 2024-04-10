package com.quokkatech.foodtruckmanagement.api.controllers;

import com.quokkatech.foodtruckmanagement.api.request.FoodTruckRequest;
import com.quokkatech.foodtruckmanagement.api.response.FoodTruckResponse;
import com.quokkatech.foodtruckmanagement.api.response.UserSessionResponse;
import com.quokkatech.foodtruckmanagement.application.exceptions.TruckAlreadyExistsException;
import com.quokkatech.foodtruckmanagement.application.services.FoodTruckService;
import com.quokkatech.foodtruckmanagement.domain.entities.FoodTruck;
import com.quokkatech.foodtruckmanagement.domain.entities.User;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/truckRegistrations")
public class FoodTruckController {

    private final FoodTruckService foodTruckService;

    private static final Logger logger = LoggerFactory.getLogger(UserSessionController.class);
    @Autowired
    public FoodTruckController(FoodTruckService foodTruckService){
        this.foodTruckService=foodTruckService;
    }

    @PostMapping("")
    public ResponseEntity<FoodTruckResponse> registerFoodTruck(@RequestBody FoodTruckRequest foodTruckRequest){
        try {
            ModelMapper modelMapper = new ModelMapper();
            FoodTruck foodTruck = modelMapper.map(foodTruckRequest, FoodTruck.class);
            foodTruckService.createFoodTruck(foodTruck);

            FoodTruckResponse foodTruckResponse = new FoodTruckResponse(foodTruckRequest.getTruckId(), foodTruckRequest.getTruckName(), foodTruckRequest.getUser());

            return ResponseEntity.status(HttpStatus.CREATED).body(foodTruckResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new FoodTruckResponse(foodTruckRequest.getTruckId(), foodTruckRequest.getTruckName(), foodTruckRequest.getUser()));
        }

    }

    @GetMapping("/{truckId}")
    public ResponseEntity<FoodTruckResponse> getFoodTruckById(@PathVariable Long truckId){
        logger.info("FoodTruckController.findById - Finding truck with ID: {}", truckId);

        try{
            Optional<FoodTruck> foodTruckOptional = foodTruckService.findById(truckId);
            if (foodTruckOptional.isPresent()){
                FoodTruck foodTruck = foodTruckOptional.get();
                FoodTruckResponse foodTruckResponse = new FoodTruckResponse(foodTruck.getTruckId(),foodTruck.getName(),foodTruck.getUser());
                return ResponseEntity.ok(foodTruckResponse);
            }
            else {
                return ResponseEntity.notFound().build();
            }


        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
    @GetMapping("/truck/{name}")
    public ResponseEntity<FoodTruckResponse> getFoodTruckByName(@PathVariable String name){
        logger.info("FoodTruckController.findByName - Finding truck with name: {}", name);
        try{
            Optional<FoodTruck> foodTruckOptional = foodTruckService.findByName(name);
            if (foodTruckOptional.isPresent()){
                FoodTruck foodTruck = foodTruckOptional.get();
                FoodTruckResponse foodTruckResponse = new FoodTruckResponse(foodTruck.getTruckId(),foodTruck.getName(),foodTruck.getUser());
                return ResponseEntity.ok(foodTruckResponse);
            }
            else {
                return ResponseEntity.notFound().build();
            }
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }


    }

    //can access at endpoint /truckRegistrations?userId=<user_id>
    @GetMapping()
    public ResponseEntity<List<FoodTruckResponse>> getAllFoodTrucksByUserId(@RequestParam(required = true) Long userId){
        logger.info("FoodTruckController.getAllFoodTrucksByUserId- finding all trucks with userId: {}", userId);


        List<FoodTruck> foodTrucks = foodTruckService.getAllFoodTrucksByUserId(userId);

        List<FoodTruckResponse> foodTruckResponses = new ArrayList<>();
        for (FoodTruck t : foodTrucks){
            foodTruckResponses.add(new FoodTruckResponse(t.getTruckId(),t.getName(),t.getUser()));
        }
        return ResponseEntity.ok(List.of(foodTruckResponses.toArray(new FoodTruckResponse[0])));
    }

    @PutMapping("/truck/{truckId}")
    public ResponseEntity<FoodTruckResponse> updateFoodTruck(@PathVariable Long truckId, @RequestBody FoodTruckRequest foodTruckRequest) {
        try {
            ModelMapper modelMapper = new ModelMapper();
            FoodTruck updatedFoodTruck = modelMapper.map(foodTruckRequest, FoodTruck.class);
            Optional<FoodTruck> existingFoodTruckOptional = foodTruckService.findById(truckId);

            if (existingFoodTruckOptional.isPresent()) {
                FoodTruck updatedTruck = foodTruckService.updateFoodTruck(truckId, updatedFoodTruck);
                FoodTruckResponse foodTruckResponse = new FoodTruckResponse(updatedTruck.getTruckId(), updatedTruck.getName(), updatedTruck.getUser());
                return ResponseEntity.ok(foodTruckResponse);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/truck/{truckId}")
    public ResponseEntity<Void> deleteFoodTruck (@PathVariable Long truckId){
        logger.info("FoodTruckController.deleteFoodTruck - Deleting truck with ID: {}", truckId);

        try{
            foodTruckService.deleteFoodTruck(truckId);
            return ResponseEntity.noContent().build();
        }
        catch (Exception e){
            logger.error("Error deleting food truck with ID: {}", truckId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
