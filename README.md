# 🍔 Lanchonete do Bairro - Clientes

##### versão 1.0.0-POC

<img src="https://img.shields.io/badge/Java-17-important.svg?logo=java" alt="shield referente a versao do java"><img src="https://img.shields.io/badge/Build Tool-Gradle-green.svg?logo=gradle" alt="shield referente ao uso de gradle">[<img src="https://img.shields.io/badge/dockerhub-images-blue.svg?logo=docker" alt="shield referente ao docker hub">](https://hub.docker.com/repository/docker/danielmaria/postech-clientes/general) <img src="https://img.shields.io/badge/Orchestrated by-Kubernetes-green.svg?logo=kubernetes" alt="shield referente ao uso de kubernetes">
[<img src="https://img.shields.io/badge/dockerhub-images-blue.svg?logo=docker" alt="shield referente ao docker hub">](https://hub.docker.com/repository/docker/danielmaria/postech-clientes/general)

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=postech-lanchonete_postech-clientes&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=postech-lanchonete_postech-clientes)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=postech-lanchonete_postech-clientes&metric=coverage)](https://sonarcloud.io/summary/new_code?id=postech-lanchonete_postech-clientes)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=postech-lanchonete_postech-clientes&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=postech-lanchonete_postech-clientes)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=postech-lanchonete_postech-clientes&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=postech-lanchonete_postech-clientes)

____
<p align="justify">
    A API de Clientes oferece operações essenciais para a gestão de clientes. Ela permite a criação de novos clientes e a busca de clientes existentes por meio do CPF. Essa simplicidade na criação e recuperação de informações de clientes facilita a integração com outras partes do sistema, contribuindo para uma gestão eficaz da base de clientes.
</p>

## Pipeline
<p align="justify">
    Esta pipeline automatizada para um projeto Java realiza uma série de etapas cruciais. Inicialmente, o job "code_check" é acionado a cada push no repositório ou manualmente. Neste ponto, são configurados o ambiente Java com JDK 17, e os pacotes do SonarCloud e Gradle são gerenciados em cache. O projeto é construído e analisado usando o Gradle, e os resultados são enviados para o SonarCloud. Em seguida, o job "build_image" é executado apenas em caso de push na branch principal (main) e após o término bem-sucedido do job "code_check". Aqui, a imagem Docker do aplicativo é construída e publicada no Docker Hub, sendo a versão da imagem derivada do hash do commit mais recente. Por fim, o job "deploy_ec2" é acionado nas mesmas condições do "build_image" e após a conclusão bem-sucedida do job "build_image". Este job simula a atualização do aplicativo na EC2, puxando a imagem mais recente do Docker Hub, excluindo o contêiner antigo (simulado), executando um novo contêiner Docker (simulado) e realizando etapas simuladas de limpeza.
</p>

<p align="center">
  <img src="https://github.com/postech-lanchonete/postech-clientes/assets/20681811/bddf7055-e20f-4585-acc9-59ef9c0cd82c" />
</p>


## Arquitetura limpa
<p align="justify">
  A arquitetura limpa é um conceito de design de software que promove a separação de responsabilidades em camadas bem definidas. A arquitetura limpa visa manter a independência das camadas, facilitando a manutenção, teste e evolução do software. Ela foi utilizada neste projeto pois permite que este seja eveluido de forma mais simples futuramente.
</p>
A estrutura geral do projeto pode ser vista a seguir:

```sh
├── main
|  ├── java
|  |  └── br
|  |     └── com
|  |        └── postech.pagamento
|  |           ├── business <- 2. Application Business Rules
|  |           |  ├── exceptions
|  |           |  └── usecases
|  |           ├── core <- 1. Enterprise Business Rules
|  |           |  ├── entities
|  |           |  └── enums
|  |           ├── drivers <- 4. Frameworks & Drivers
|  |           |  └── web
|  |           └── adapters <- 3. Interface Adapters
|  |              ├── adapter
|  |              ├── controllers
|  |              ├── dto
|  |              ├── gateways
|  |              ├── handler
|  |              └── repositories
```
<details>
  <summary>Explicação das camadas</summary>
As camadas do projeto foram divididas seguindo a logica proposta pela arquitetura limpa, sendo 4 as prinipais assinaladas na árvore de estrutura acima.

1. **Core:** Esta camada contém regras de negócios de alto nível e princípios que são específicos para a organização ou domínio de negócios.
2. **Business**: Aqui, você encontra as regras de negócios da aplicação, que são específicas para a aplicação em si, independentemente do domínio de negócios. É onde a lógica de negócios central é implementada.
3. **Adapters**: Esta camada lida com a interação entre a aplicação e o mundo exterior. Inclui adaptadores para interfaces de usuário, bancos de dados, serviços externos e qualquer outra coisa que não seja parte da aplicação em si.
4. **Drivers**: É onde você integra frameworks, bibliotecas e drivers externos necessários para executar a aplicação. Isso inclui o banco de dados, frameworks web, ferramentas de comunicação e assim por diante. É a fronteira entre a aplicação e o ambiente externo.

</details>

## OpenAPI

<p align="justify">
  O OpenAPI é uma especificação que define um padrão para descrever APIs RESTful de maneira padronizada, independente de linguagem e de plataforma. Com o OpenAPI é possível documentar de forma estruturada todos os *endpoints*, parâmetros, respostas e demais detalhes de uma API, facilitando a compreensão e a utilização por parte de desenvolvedores e consumidores. Já o Swagger é uma ferramenta que utiliza a especificação OpenAPI para gerar automaticamente a documentação interativa da API. Através do Swagger UI é possível acessar a documentação da API em um formato amigável, que permite explorar os <b>endpoints</b>, testar as requisições e visualizar as respostas esperadas. O uso do OpenAPI e do Swagger traz diversos benefícios, como a padronização da documentação, a facilidade de integração entre sistemas e a promoção de uma documentação atualizada e de qualidade. Para acessar a documentação gerada pelo Swagger, basta acessar a URL específica fornecida pelo projeto que é <code>/swagger-ui/index.html#/</code>
</p>

## Verificação de integridade (Health Check)

<p align="justify">
  O Health Check é um mecanismo que permite verificar o estado de saúde de um sistema ou serviço em tempo real. É utilizado para monitorar e validar se o sistema está funcionando corretamente, fornecendo informações sobre sua disponibilidade e desempenho.
</p>
<p align="justify">
  No contexto deste projeto, o Health Check foi implementado para garantir a integridade e a estabilidade do sistema, permitindo que os administradores ou ferramentas de monitoramento verifiquem a saúde da aplicação de forma automatizada. O projeto utiliza o Health Check para fornecer um endpoint específico, <code>/actuator/health</code>, que retorna informações sobre o estado do sistema, incluindo o status atual. Isso é útil para identificar problemas e tomar ações corretivas, garantindo a disponibilidade e a qualidade do serviço. Ao utilizar o Health Check, o projeto busca facilitar o monitoramento e o diagnóstico de problemas, contribuindo para uma melhor experiência do usuário e um ambiente mais confiável.
</p>

<details>
  <summary>Alguns endpoints úteis</summary>

```sh
/actuator/health: Informações de saúde da aplicação, incluindo o status do banco de dados.
```

```sh
/actuator/info: Informações gerais sobre a aplicação, incluindo informações sobre o banco de dados.
```

```sh
/actuator/metrics: Métricas da aplicação, como tempo de execução de consultas no banco de dados.
```

</details>


## Swagger

A documentação técnica da API pode ser acessada na URI [/swagger-ui/index.html](http://localhost:31001/swagger-ui/index.html), quando o sistema está rodando.

