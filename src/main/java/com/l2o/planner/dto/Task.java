package com.l2o.planner.dto;

import java.time.Instant;
import java.util.UUID;

import jakarta.annotation.Nonnull;

public class Task {
    @Nonnull
    public UUID tasktypeId;
    @Nonnull
    public Instant start;
    @Nonnull
    public Instant end;
}
