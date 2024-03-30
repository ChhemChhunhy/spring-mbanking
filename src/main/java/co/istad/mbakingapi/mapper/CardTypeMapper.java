package co.istad.mbakingapi.mapper;

import co.istad.mbakingapi.domain.CardType;
import co.istad.mbakingapi.features.cardType.dto.CardTypeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CardTypeMapper {
    List<CardTypeResponse> toListCardTypesResponse(List<CardType> cardTypeList);

    CardTypeResponse toCardTypeResponse(CardType cardType);
}
