package com.crud.tasks.trello;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloCard;
import com.crud.tasks.domain.TrelloList;
import com.crud.tasks.mapper.TrelloMapper;
import com.crud.tasks.trello.model.TrelloBoardDto;
import com.crud.tasks.trello.model.TrelloCardDto;
import com.crud.tasks.trello.model.TrelloListDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class TrelloMapperTestSuite {

    @InjectMocks
    TrelloMapper trelloMapper;

    @Test
    public void testMapToBoards() {
        //Given
        List<TrelloListDto> trelloListDto = new ArrayList<>();
        List<TrelloBoardDto> trelloBoardListDto = new ArrayList<>();
        trelloBoardListDto.add(new TrelloBoardDto("1","test", trelloListDto));
        trelloBoardListDto.add(new TrelloBoardDto("2", "test1", trelloListDto));
        //When
        List<TrelloBoard> trelloBoards = trelloMapper.mapToBoards(trelloBoardListDto);
        //Then
        Assert.assertEquals(2, trelloBoards.size());
        Assert.assertEquals("test", trelloBoards.get(0).getName());
        Assert.assertEquals("1", trelloBoards.get(0).getId());
        Assert.assertEquals("test1", trelloBoards.get(1).getName());
        Assert.assertEquals("2", trelloBoards.get(1).getId());
    }

    @Test
    public void testMapToBoardsDto() {
        //Given
        List<TrelloList> trelloList = new ArrayList<>();
        List<TrelloBoard> trelloBoard = new ArrayList<>();
        trelloBoard.add(new TrelloBoard("1", "test", trelloList));
        trelloBoard.add(new TrelloBoard("2", "test1", trelloList));
        //When
        List<TrelloBoardDto> trelloBoardDto = trelloMapper.mapToBoardsDto(trelloBoard);
        //Then
        Assert.assertEquals(2, trelloBoardDto.size());
        Assert.assertEquals("test", trelloBoardDto.get(0).getName());
        Assert.assertEquals("1", trelloBoardDto.get(0).getId());
        Assert.assertEquals("test1", trelloBoardDto.get(1).getName());
        Assert.assertEquals("2", trelloBoardDto.get(1).getId());
    }

    @Test
    public void testMapToList() {
        //Given
        List<TrelloListDto> trelloListDto = new ArrayList<>();
        trelloListDto.add(new TrelloListDto("1", "test", true));
        //When
        List<TrelloList> trelloLists = trelloMapper.mapToList(trelloListDto);
        //Then
        Assert.assertEquals("1", trelloLists.get(0).getId());
        Assert.assertEquals("test", trelloLists.get(0).getName());
        Assert.assertEquals(true, trelloLists.get(0).isClosed());
        Assert.assertEquals(1, trelloListDto.size());
    }

    @Test
    public void testMapToListDto() {
        //Given
        List<TrelloList> trelloList = new ArrayList<>();
        trelloList.add(new TrelloList("1", "test", true));
        //When
        List<TrelloListDto> trelloListDto = trelloMapper.mapToListDto(trelloList);
        //Then
        Assert.assertEquals("1", trelloListDto.get(0).getId());
        Assert.assertEquals("test", trelloListDto.get(0).getName());
        Assert.assertEquals(true, trelloListDto.get(0).isClosed());
        Assert.assertEquals(1, trelloListDto.size());
    }

    @Test
    public void testMapToCardDto() {
        //Given
        TrelloCard trelloCard = new TrelloCard("name", "description", "pos", "listID");
        //When
        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard);
        //Then
        Assert.assertEquals("name", trelloCardDto.getName());
        Assert.assertEquals("description", trelloCardDto.getDescription());
        Assert.assertEquals("pos", trelloCardDto.getPos());
        Assert.assertEquals("listID", trelloCardDto.getListId());
    }

    @Test
    public void testMapToCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("name", "description", "pos", "listID");
        //When
        TrelloCard trelloCard = trelloMapper.maptoCard(trelloCardDto);
        //Then
        Assert.assertEquals("name", trelloCard.getName());
        Assert.assertEquals("description", trelloCard.getDescription());
        Assert.assertEquals("pos", trelloCard.getPos());
        Assert.assertEquals("listID", trelloCard.getListId());
    }
}
