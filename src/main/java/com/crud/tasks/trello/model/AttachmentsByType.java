package com.crud.tasks.trello.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AttachmentsByType {

    @JsonProperty("trello")
    private Trello trello;
}
