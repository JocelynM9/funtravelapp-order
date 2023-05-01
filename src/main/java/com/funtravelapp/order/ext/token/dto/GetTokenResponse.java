package com.funtravelapp.order.ext.token.dto;

import com.funtravelapp.order.dto.Response;
import com.funtravelapp.order.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetTokenResponse {

    private Response.Error error;
    private User data;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Error {
        private String message;
    }
}
