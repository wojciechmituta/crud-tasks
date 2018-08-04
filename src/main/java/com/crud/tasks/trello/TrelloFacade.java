package com.crud.tasks.trello;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloCard;
import com.crud.tasks.mapper.TrelloMapper;
import com.crud.tasks.service.TrelloService;
import com.crud.tasks.trello.model.CreatedTrelloCardDto;
import com.crud.tasks.trello.model.TrelloBoardDto;
import com.crud.tasks.trello.model.TrelloCardDto;
import com.crud.tasks.trello.validator.TrelloValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class TrelloFacade {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrelloFacade.class);

    @Autowired
    TrelloService trelloService;

    @Autowired
    TrelloMapper trelloMapper;

    @Autowired
    TrelloValidator trelloValidator;

    public List<TrelloBoardDto> fetchTrelloBoards() {
        List<TrelloBoard> trelloBoards = trelloMapper.mapToBoards(trelloService.fetchTrelloBoards());
        List<TrelloBoard> filteredBoards = trelloValidator.validateTrelloBoards(trelloBoards);

        return trelloMapper.mapToBoardsDto(filteredBoards);
    }

    public CreatedTrelloCardDto createCard(final TrelloCardDto trelloCardDto) {
        TrelloCard trelloCard = trelloMapper.maptoCard(trelloCardDto);
        trelloValidator.validateCard(trelloCard);
        return trelloService.createTrelloCard(trelloMapper.mapToCardDto(trelloCard));
    }
}
