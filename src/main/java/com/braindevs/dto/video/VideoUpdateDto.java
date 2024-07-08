package com.braindevs.dto.video;
import com.braindevs.enums.VideoType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class VideoUpdateDto {

    private String videoId;
    private String previewAttachId;
    private String attachId;
    @Size(min = 3, message = "name must be at least 3 characters")
    private String title;
    private Integer categoryId;
    @Size(min = 3, message = "name must be at least 3 characters")
    private VideoType type;
    @NotNull(message = "description mustn't be null")
    private String description;

}
