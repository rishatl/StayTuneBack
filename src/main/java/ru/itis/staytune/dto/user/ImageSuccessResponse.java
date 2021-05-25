package ru.itis.staytune.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImageSuccessResponse {
    private String imagePath;
}
