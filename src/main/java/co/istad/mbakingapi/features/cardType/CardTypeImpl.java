package co.istad.mbakingapi.features.cardType;

import co.istad.mbakingapi.domain.CardType;
import co.istad.mbakingapi.features.cardType.dto.CardTypeResponse;
import co.istad.mbakingapi.mapper.CardTypeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardTypeImpl implements CardTypeService{
    private final CardTypeRepository cardTypeRepository;
    private final CardTypeMapper cardTypeMapper;
    @Override
    public List<CardTypeResponse> findAllCardTypes() {
        List<CardType> cardTypes = cardTypeRepository.findAll();
        return cardTypeMapper.toListCardTypesResponse(cardTypes);
    }

    @Override
    public CardTypeResponse findCardTypeByName(String name) {
        CardType cardType = cardTypeRepository.findByNameIgnoreCase(name).orElseThrow(
                ()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Card type does not exist"
                )
        );
        return cardTypeMapper.toCardTypeResponse(cardType);
    }
}
