package com.ursominhoco.cdist.dto;

import lombok.Data;

@Data
public class AuthRequestDTO {
    private String email;
    private String senha;
}