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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/trucks")
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

            FoodTruckResponse foodTruckResponse = new FoodTruckResponse(foodTruckRequest.getTruckId(), foodTruckRequest.getTruckName(), foodTruckRequest.getUser(), foodTruckRequest.getMenu());

            return ResponseEntity.status(HttpStatus.CREATED).body(foodTruckResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new FoodTruckResponse(foodTruckRequest.getTruckId(), foodTruckRequest.getTruckName(), foodTruckRequest.getUser(),foodTruckRequest.getMenu()));
        }

    }

    @GetMapping("/truckId/{truckId}")
    public ResponseEntity<FoodTruckResponse> getFoodTruckById(@PathVariable Long truckId){
        logger.info("FoodTruckController.findById - Finding truck with ID: {}", truckId);

        try{
            Optional<FoodTruck> foodTruckOptional = foodTruckService.findById(truckId);
            if (foodTruckOptional.isPresent()){
                FoodTruck foodTruck = foodTruckOptional.get();
                FoodTruckResponse foodTruckResponse = new FoodTruckResponse(foodTruck.getTruckId(),foodTruck.getName(),foodTruck.getUser(),foodTruck.getMenu());
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
    @GetMapping("/truckName/{name}")
    public ResponseEntity<FoodTruckResponse> getFoodTruckByName(@PathVariable String name){
        logger.info("FoodTruckController.findByName - Finding truck with name: {}", name);
        try{
            Optional<FoodTruck> foodTruckOptional = foodTruckService.findByName(name);
            if (foodTruckOptional.isPresent()){
                FoodTruck foodTruck = foodTruckOptional.get();
                FoodTruckResponse foodTruckResponse = new FoodTruckResponse(foodTruck.getTruckId(),foodTruck.getName(),foodTruck.getUser(),foodTruck.getMenu());
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

    //can access at endpoint /trucks?userId=<user_id>
    @GetMapping()
    public ResponseEntity<List<FoodTruckResponse>> getAllFoodTrucksByUserId(@RequestParam(required = true) Long userId, @RequestParam(required = false) String name){
        logger.info("FoodTruckController.getAllFoodTrucksByUserId- finding all trucks with userId: {}", userId);


        List<FoodTruck> foodTrucks = foodTruckService.getAllFoodTrucksByUserId(userId);

        List<FoodTruckResponse> foodTruckResponses = new ArrayList<>();
        for (FoodTruck t : foodTrucks){
            foodTruckResponses.add(new FoodTruckResponse(t.getTruckId(),t.getName(),t.getUser(),t.getMenu()));
        }
        if (name == null){
            return ResponseEntity.ok(List.of(foodTruckResponses.toArray(new FoodTruckResponse[0])));
        }
        else {
            List<FoodTruckResponse> filteredFoodTruckResponses = foodTruckResponses.stream()
                    .filter(ftr -> ftr.getTruckName().equalsIgnoreCase(name))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(filteredFoodTruckResponses);
        }
    }

    @PutMapping("/{truckId}")
    public ResponseEntity<FoodTruckResponse> updateFoodTruck(@PathVariable Long truckId, @RequestBody FoodTruckRequest foodTruckRequest) {
        try {
            ModelMapper modelMapper = new ModelMapper();
            FoodTruck updatedFoodTruck = modelMapper.map(foodTruckRequest, FoodTruck.class);
            Optional<FoodTruck> existingFoodTruckOptional = foodTruckService.findById(truckId);

            if (existingFoodTruckOptional.isPresent()) {
                FoodTruck updatedTruck = foodTruckService.updateFoodTruck(truckId, updatedFoodTruck);
                FoodTruckResponse foodTruckResponse = new FoodTruckResponse(updatedTruck.getTruckId(), updatedTruck.getName(), updatedTruck.getUser(),updatedTruck.getMenu());
                return ResponseEntity.ok(foodTruckResponse);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{truckId}")
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
    @GetMapping("/{userId}")
    public ResponseEntity<List<FoodTruckResponse>> getAllFoodTrucksByUser(@PathVariable Long userId){
        try{

            List<FoodTruck>foodTrucks = foodTruckService.getAllFoodTrucksByUserId(userId);
            List<FoodTruckResponse> foodTruckResponses = new ArrayList<>();
            ModelMapper modelMapper = new ModelMapper();
            for (FoodTruck t : foodTrucks){
                foodTruckResponses.add(modelMapper.map(t,FoodTruckResponse.class));
            }
            if (!foodTruckResponses.isEmpty()) {
                return ResponseEntity.ok(foodTruckResponses);
            }
            else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
