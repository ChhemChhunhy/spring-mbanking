package co.istad.mbakingapi.features.cardType;

import co.istad.mbakingapi.features.cardType.dto.CardTypeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cardTypes")
@RequiredArgsConstructor
public class CardTypeController {
    private final CardTypeService cardTypeService;
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<CardTypeResponse> findAllCardTypes(){
        return cardTypeService.findAllCardTypes();
    }

    @GetMapping("/{name}")
    @ResponseStatus(HttpStatus.OK)
    CardTypeResponse findCardTypeByName(@PathVariable String name){
        return cardTypeService.findCardTypeByName(name);
    }

}
