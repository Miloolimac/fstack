package com.example.demo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/")
    public String index(@RequestParam(defaultValue = "visitante") String nombre){
        LocalDateTime ahora = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-mm-yyyy HH:mm:ss");
        
        return "<html>"+
        "<body style='font-family:Arial'>"+
        "<h1>!Hola "+nombre+"</h1>"+
        "<p>Este es nuestro primer poryecto con Spring </p>"+
        "<p>La hora de creacion es :"+ahora.format(formato)+"</p>"+
        "</body>"+
        "</html>";
    }
}
//       http://localhost:8080/?nombre=nombrequequieranponer
