package co.istad.mbakingapi.features.cardType;

import co.istad.mbakingapi.domain.CardType;
import co.istad.mbakingapi.features.cardType.dto.CardTypeResponse;

import java.util.List;

public interface CardTypeService {
    List<CardTypeResponse> findAllCardTypes();
    CardTypeResponse findCardTypeByName(String name);
}
