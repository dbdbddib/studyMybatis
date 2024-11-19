package com.softagape.mustacheajax.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseKaToHeader {
    private String resultCode;
    private String resultMsg;
}
