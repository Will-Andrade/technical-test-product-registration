# 🛍️ Aplicação de Gerenciamento de Produtos

Seja bem-vindo ou bem-vinda ao meu sistema de gerenciamento de produtos! Este projeto 
é uma aplicação Java para gerenciar um catálogo de produtos. Ele permite criar e 
listar produtos criados a partir de seu nome, valor e disponibilidade em estoque.

## Funcionalidades
- 📄 Leitura de arquivo CSV de produtos
- 🔄 Formatação de produtos e exportação para novo arquivo CSV
- 📊 Processamento de dados como nome, descrição, preço e disponibilidade de produtos

## Tecnologias
- Java 21: Linguagem principal utilizada para o desenvolvimento da aplicação.
- Java I/O: Para leitura e gravação de arquivos CSV.
- Streams API: Para manipulação e transformação dos dados.

## Requisitos para Rodar a Aplicação
- Java 21 ou superior instalado na sua máquina
- IDE de sua escolha (por exemplo, IntelliJ IDEA, Eclipse) ou um simples editor de texto.

## Como Começar

- Primeiro será necessário clonar o repositório
```bash
git clone git@github.com:Will-Andrade/technical-test-product-registration.git
cd technical-test-product-registration
```

- Certifique-se de que a estrutura do projeto é a seguinte:
```bash
/src
   /main
       /java
           /com
               /andradev
                   /productreglist
                       - ProductApp.java
                       - Product.java
                       - CsvReader.java
```

## Rodando a Aplicação
Navegue até a pasta do projeto e execute o seguinte comando para compilar e rodar a 
aplicação:
```bash
javac src/main/java/com/example/productmanagement/ProductApp.java
java -cp src/main/java com.example.productmanagement.ProductApp
```
