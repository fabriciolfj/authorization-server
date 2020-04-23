# authorization-server

Client credentials grant -> pega o token com base no nome do client, sem necessidade do usuário final
é indicado para comunicação entre backends.

Exemplo authorization code: 
```
http://localhost:8081/oauth/authorize?response_type=code&client_id=foodanalytics&state=abc&redirect_uri=http://aplicacao-cliente
```

Implicit Grant -> é similar ao authorization_code, mas ele não usa o code, ele já retorna o access_token.

Exemplo implicit grant:

```
http://localhost:8081/oauth/authorize?response_type=token&client_id=mobile&state=abc&redirect_uri=http://aplicacao-cliente
```