package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;

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
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    TaskController taskController;

    @MockBean
    TaskMapper taskMapper;

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

        when(service.getAllTasks()).thenReturn(taskList);
        when(taskMapper.mapToTaskDtoList(any())).thenReturn(taskDtoList);
        //when(taskController.getTasks()).thenReturn(taskDtoList);

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
        Task task = new Task(1L, "test", "content");

        when(taskMapper.mapToTaskDto(any())).thenReturn(taskDto);
        when(service.getTaskById(1L)).thenReturn(task);

        //When & Then
        mockMvc.perform(get("/v1/task/getTask?taskId=1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("test")))
                .andExpect(jsonPath("$.content", is("content")));
    }

    @Test
    public void shouldDeleteTask() throws Exception {
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

        Task task = new Task(1L, "test", "content");
        Task taskUpdate = new Task(1L, "test_update", "content_update");


        when(taskMapper.mapToTaskDto(any(Task.class))).thenReturn(taskDto);
        when(taskMapper.mapToTask(any(TaskDto.class))).thenReturn(taskUpdate);
        when(service.saveTask(any(Task.class))).thenReturn(taskUpdate);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDtoUpdate);

        //When & Then

        mockMvc.perform(put("/v1/task/updateTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("test_update")))
                .andExpect(jsonPath("$.content", is("content_update")));
    }

    @Test
    public void testCreateTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(1L, "test", "content");

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        mockMvc.perform(post("/v1/task/createTask")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isOk());
    }
}