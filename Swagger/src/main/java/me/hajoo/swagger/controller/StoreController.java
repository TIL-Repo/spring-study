package me.hajoo.swagger.controller;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.hajoo.swagger.dto.OrderPetDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "store", description = "Access to Petstore orders")
@RestController
public class StoreController {

    @Operation(summary = "find order", description = "find order example")
    @ApiResponses({
            @ApiResponse(code = 200, message = "successful operation"),
            @ApiResponse(code = 400, message = "Invalid Order"),
            @ApiResponse(code = 404, message = "Order not found")
    })
    @GetMapping("/store/order/{orderId}")
    public ResponseEntity<OrderPetDto> findOrderByOrderId(@Parameter(name = "orderId", description = "ID of pet that needs to be fetched", required = true) @PathVariable Long orderId) {
        return ResponseEntity.ok(new OrderPetDto());
    }

    @Operation(summary = "order", description = "order example")
    @ApiResponses({
            @ApiResponse(code = 200, message = "successful operation"),
            @ApiResponse(code = 400, message = "Invalid Order")
    })
    @PostMapping("/store/order")
    public ResponseEntity<OrderPetDto> createOrder(@Parameter(name = "body", required = true) @RequestBody OrderPetDto orderPet) {
        return ResponseEntity.ok(new OrderPetDto());
    }

    @Operation(summary = "delete order", description = "delete order example")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 404, message = "Order not found")
    })
    @DeleteMapping("/store/order/{orderId}")
    public ResponseEntity<String> deleteOrder(@Parameter(name = "orderId", description = "ID of the order that needs to be deleted", required = true) @PathVariable Long orderId) {
        return ResponseEntity.ok("test");
    }
}
