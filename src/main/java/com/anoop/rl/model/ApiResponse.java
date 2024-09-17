package com.anoop.rl.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "api_responses")
public class ApiResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "response_id")
    private Long responseId;

    @Column(name = "endpoint", nullable = false)
    private String endpoint;

    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "status_code", nullable = false)
    private int statusCode;

    @Column(name = "details", nullable = false)
    private String details;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    public ApiResponse(String endpoint, String message, int statusCode, String details) {
        this.endpoint = endpoint;
        this.message = message;
        this.statusCode = statusCode;
        this.details = details;
        this.timestamp = LocalDateTime.now();
    }

    public ApiResponse() {
    }
}
