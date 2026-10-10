
package com.safepassword;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Duration;
import java.util.HexFormat;

import org.springframework.stereotype.Service;

@Service
public class PwnedPasswordService {

    private final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(5))
            .followRedirects(HttpClient.Redirect.NEVER)
            .build();

    public int verificar(String senha) throws Exception {

        // 1. Calcula o SHA-1 localmente.
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        byte[] bytes = digest.digest(
                senha.getBytes(StandardCharsets.UTF_8));

        String hash = HexFormat.of().withUpperCase()
                .formatHex(bytes);

        // 2. Separa o prefixo e o sufixo do hash.
        String prefixo = hash.substring(0, 5);
        String sufixoProcurado = hash.substring(5);

        // 3. Envia somente o prefixo para a API.
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(
                    "https://api.pwnedpasswords.com/range/" + prefixo))
                .timeout(Duration.ofSeconds(10))
                .header("User-Agent", "SafePassword-Educational-Project")
                .header("Add-Padding", "true")
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(
                request,
                HttpResponse.BodyHandlers.ofString(
                        StandardCharsets.UTF_8));

        if (response.statusCode() != 200) {
            throw new IllegalStateException(
                    "A API de consulta está indisponível.");
        }

        // 4. Compara os sufixos localmente.
        for (String linha : response.body().split("\\r?\\n")) {
            String[] partes = linha.split(":", 2);

            if (partes.length != 2) {
                continue;
            }

            if (partes[0].equalsIgnoreCase(sufixoProcurado)) {
                return Integer.parseInt(partes[1].trim());
            }
        }

        // Não encontrou correspondência na base consultada.
        return 0;
    }
}
