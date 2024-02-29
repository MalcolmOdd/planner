package com.l2o.planner.dto;

import java.time.Instant;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.annotation.Nonnull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Task {
    @Nonnull
    public UUID tasktypeId;
    @Nonnull
    public Instant start;
    @Nonnull
    public Instant end;
}
