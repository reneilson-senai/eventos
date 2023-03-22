package com.senai.eventos.domain;

import com.senai.eventos.domain.file.FileInfo;

import jakarta.persistence.Embedded;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public class FotoModel {
    @Embedded
    private FileInfo foto;
}
