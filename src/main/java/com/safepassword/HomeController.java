
package com.safepassword;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class HomeController {

    private final PwnedPasswordService service;

    public HomeController(PwnedPasswordService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @PostMapping("/api/password-check")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> verificar(
            @RequestBody Map<String, String> dados) {

        String senha = dados.get("password");

        if (senha == null || senha.isEmpty() || senha.length() > 1024) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Senha inválida.");
        }

        try {
            int ocorrencias = service.verificar(senha);

            Map<String, Object> resultado = Map.of(
                    "comprometida", ocorrencias > 0,
                    "ocorrencias", ocorrencias
            );

            return ResponseEntity.ok(resultado);

        } catch (Exception e) {
            // Não registrar a senha nem o conteúdo da requisição.
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(Map.of(
                            "erro",
                            "Não foi possível consultar a base. Tente novamente."
                    ));
        }
    }
}
