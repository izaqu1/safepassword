#  SafePassword

**Verifique se uma senha aparece em bases conhecidas de vazamentos de dados.**

O SafePassword é uma aplicação web desenvolvida em Java e Spring Boot que consulta o serviço Pwned Passwords, do Have I Been Pwned, para verificar se uma senha aparece em uma base de senhas comprometidas.

O projeto tem como objetivo praticar desenvolvimento backend, integração com APIs externas, construção de interfaces web e boas práticas de segurança.

##  Funcionalidades

- Consulta de senhas na base Pwned Passwords.
- Comunicação entre frontend e backend por meio de uma API REST.
- Cálculo local do hash SHA-1 da senha.
- Uso do modelo *k-anonymity*: somente os cinco primeiros caracteres do hash são enviados para consulta.
- Exibição da quantidade de ocorrências encontradas.
- Tratamento de falhas na comunicação com o serviço externo.

##  Tecnologias utilizadas

- **Java** — linguagem de programação.
- **Spring Boot** — framework para a aplicação web.
- **Maven** — gerenciamento do projeto e das dependências.
- **HTML e CSS** — estrutura e estilo da interface.
- **JavaScript** — envio das consultas ao backend.
- **Pwned Passwords API** — serviço externo de consulta.

##  Como executar o projeto

### Pré-requisitos

- JDK compatível com a versão configurada no projeto.
- Maven, ou o Maven Wrapper incluído no repositório.
- Conexão com a internet para consultar a API externa.

### Executando pelo Eclipse

1. Importe o projeto Maven no Eclipse.
2. Aguarde a resolução das dependências.
3. Localize `SafepasswordApplication.java`.
4. Clique com o botão direito e selecione **Run As → Java Application** ou a opção de execução Spring Boot disponível.
5. Abra o navegador em `http://localhost:8080`.

### Executando pelo terminal

Na pasta raiz do projeto, execute:

No Windows:

```bat
mvnw.cmd spring-boot:run
```

No Linux ou macOS:

```bash
./mvnw spring-boot:run
```

Se o Maven Wrapper não estiver disponível, instale o Maven e execute `mvn spring-boot:run`.

##  Segurança e privacidade

O SafePassword utiliza o modelo *k-anonymity* oferecido pela API Pwned Passwords. A senha é transformada em hash localmente, e apenas o prefixo do hash é enviado ao serviço para consulta.

**Importante:** isso reduz a exposição da senha, mas não representa anonimato absoluto. O serviço externo ainda recebe a solicitação e metadados de conexão.

O projeto é educacional e não deve ser considerado uma auditoria completa de segurança. Não utilize senhas pessoais durante testes ou desenvolvimento.

##  Próximas melhorias

- [ ] Criar testes automatizados para o serviço de consulta.
- [ ] Melhorar a validação das entradas e o tratamento de erros.
- [ ] Implementar limites de requisições.
- [ ] Melhorar a acessibilidade e a experiência da interface.
- [ ] Documentar a API e seus possíveis erros.
- [ ] Configurar integração contínua para executar os testes.

##  Referências

- [Pwned Passwords — documentação oficial](https://haveibeenpwned.com/API/v3#PwnedPasswords)
- [Spring Boot — documentação oficial](https://docs.spring.io/spring-boot/index.html)

##  Sobre o projeto

Projeto de estudo desenvolvido para praticar Java, Spring Boot, integração com APIs e desenvolvimento web.
