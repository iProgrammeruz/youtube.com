package com.braindevs.dto.channel;

import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class ChannelShortInfoDto {
    private String id;
    private String name;
    private String photoUrl;
}
