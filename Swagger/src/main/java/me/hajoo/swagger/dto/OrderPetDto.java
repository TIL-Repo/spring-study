package me.hajoo.swagger.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;

@ApiModel("Order")
@Getter
public class OrderPetDto {

    @Schema(example = "0", required = true)
    private Long id;
    @Schema(example = "0")
    private Long petId;
    @Schema(example = "0")
    private Long quantity;
    @Schema(example = "")
    private LocalDateTime shipDate;
    @Schema(example = "placed")
    private String status;
    @Schema(example = "true")
    private Boolean complete;
}
