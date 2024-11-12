package com.it.backend.mapper;

import com.it.backend.dto.response.RateResponse;
import com.it.backend.entity.Rate;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface RateMapper {

    RateResponse toRateResponse(Rate rate);

    Set<RateResponse> toRatesResponse(Set<Rate> rates);
}
