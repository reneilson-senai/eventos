package com.senai.eventos.serializers;

import jakarta.validation.constraints.NotEmpty;

public record TokenRefreshDTO(
    @NotEmpty
    String refreshToken
) {
    
}
