package com.funtravelapp.order.responseMapper;

import com.funtravelapp.order.dto.Response;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@NoArgsConstructor
public class ResponseMapper {

    public static ResponseEntity<?> ok(String errorMessage, Object body) {
        return ResponseEntity.ok(Response.builder()
                .error(Response.Error.builder()
                        .message(errorMessage)
                        .build())
                .data(body)
                .build());
    }

    public static ResponseEntity<?> badRequest(String errorMessage, Object body) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.builder()
                .error(Response.Error.builder()
                        .message(errorMessage)
                        .build())
                .data(body)
                .build());
    }

}
