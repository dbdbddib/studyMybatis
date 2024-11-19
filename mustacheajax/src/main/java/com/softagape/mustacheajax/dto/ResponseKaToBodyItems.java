package com.softagape.mustacheajax.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseKaToBodyItems {
    private List<KaToAreaBased> item;
}
