# authorization-server

Client credentials grant -> pega o token com base no nome do client, sem necessidade do usuário final
é indicado para comunicação entre backends.

Exemplo authorization code: 
```
http://localhost:8081/oauth/authorize?response_type=code&client_id=foodanalytics&state=abc&redirect_uri=http://aplicacao-cliente
```