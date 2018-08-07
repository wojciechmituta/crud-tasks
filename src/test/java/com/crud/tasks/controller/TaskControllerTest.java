package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbService service;

    @MockBean
    private TaskMapper taskMapper;

    @MockBean
    TaskController taskController;

    @Test
    public void shouldFetchEmptyTasksLists() throws Exception {
        //Given
        List<Task> taskList = new ArrayList<>();
        when(service.getAllTasks()).thenReturn(taskList);
        //When & Then
        mockMvc.perform(get("/v1/task/getTasks")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void shouldFetchTasksLists() throws Exception {
        //Given
        List<Task> taskList = new ArrayList<>();
        taskList.add(new Task(1L, "test", "content"));
        taskList.add(new Task(2L, "test2", "content2"));
        List<TaskDto> taskDtoList = Arrays.asList(
                new TaskDto(1L, "test", "content"),
                new TaskDto(2L, "test2", "content2"));

        when(taskMapper.mapToTaskDtoList(taskList)).thenReturn(taskDtoList);
        when(service.getAllTasks()).thenReturn(taskList);
        //When & Then
        mockMvc.perform(get("/v1/task/getTasks")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("test")))
                .andExpect(jsonPath("$[0].content", is("content")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].title", is("test2")))
                .andExpect(jsonPath("$[1].content", is("content2")));
    }

    @Test
    public void testGetTaskById() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(1L, "test", "content");

        when(taskController.getTask(1L)).thenReturn(taskDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);
        //When & Then
        mockMvc.perform(get("/v1/task/getTask?taskId=1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("test")))
                .andExpect(jsonPath("$.content", is("content")));
    }

    @Test
    public void testDeleteTask() throws Exception {
        //Given & When & Then
        mockMvc.perform(delete("/v1/task/deleteTask?taskId=1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(1L, "test", "content");
        TaskDto taskDtoUpdate = new TaskDto(1L, "test_update", "content_update");

        when(taskController.updateTasks(taskDto)).thenReturn(taskDtoUpdate);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDtoUpdate);

        //When & Then

        mockMvc.perform(put("/v1/task/updateTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("test")))
                .andExpect(jsonPath("$.content", is("content")));
    }
}