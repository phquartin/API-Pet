package dev.phquartin.pets.exception;

import lombok.*;

@Builder
public record ErrorResponse (String timestamp, Integer status, String message, String path){
}
