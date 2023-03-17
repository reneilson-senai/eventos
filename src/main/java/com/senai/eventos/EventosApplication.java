package com.senai.eventos;

import com.senai.eventos.services.FilesStorageService;
import jakarta.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EventosApplication implements CommandLineRunner {

  @Resource
  FilesStorageService storageService;

  public static void main(String[] args) {
    SpringApplication.run(EventosApplication.class, args);
  }

  @Override
  public void run(String[] arg) throws Exception {
    storageService.init();
  }
}
