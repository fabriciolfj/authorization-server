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

PKCE -> uma forma de proteger o client id e client secret, no fluxo authorization_code, quando a api está publica
(chave de prova para troca de código)

Descritivo: 
* No momento de solicitar o code, ele manda o code challenge para o backend
* backend quarda esse code
* quando o client for solicitar o access token (aonde passa o code), ele tem que mandar o code verifier
* backend valida o code verifier , pega o codigo roda o algoritimo sha256, converte para base64, o resultado compara com o code challenge guardado, se for igual manda o token access, se for diferente nega.
