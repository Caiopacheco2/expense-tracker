# Controle de Gastos (Spring Boot)

Projeto demo para portfólio. API REST com Spring Boot + JPA (H2 em memória) e frontend HTML simples para testar.

## Como rodar

Pré-requisitos: Java 17+ e Maven.

```bash
mvn spring-boot:run
```

App: http://localhost:8080

Swagger UI: http://localhost:8080/swagger-ui.html

H2 Console: http://localhost:8080/h2-console
(JDBC: jdbc:h2:mem:expenses, user sa, senha password)

## Endpoints

- GET /api/transactions (filtros startDate, endDate, category, type)
- POST /api/transactions
- PUT /api/transactions/{id}
- DELETE /api/transactions/{id}
- GET /api/summary/monthly?year=YYYY&month=M

### Exemplos (cURL)

```bash
# Criar receita
curl -X POST http://localhost:8080/api/transactions   -H "Content-Type: application/json"   -d '{"description":"Salário","amount":5000,"date":"2025-09-01","category":"SALARY","type":"INCOME"}'

# Criar despesa
curl -X POST http://localhost:8080/api/transactions   -H "Content-Type: application/json"   -d '{"description":"Mercado","amount":320.50,"date":"2025-09-03","category":"FOOD","type":"EXPENSE"}'

# Listar com filtro de período e tipo
curl "http://localhost:8080/api/transactions?startDate=2025-09-01&endDate=2025-09-30&type=EXPENSE"

# Resumo mensal
curl "http://localhost:8080/api/summary/monthly?year=2025&month=9"
```

---

# ▶️ Como executar
```bash
mvn spring-boot:run
# ou
mvn clean package && java -jar target/expense-tracker-0.0.1-SNAPSHOT.jar
```

## 🧪 Passos de validação rápida

- Abrir http://localhost:8080/ e lançar 2–3 transações (receitas e despesas).
- Conferir listagem e filtros.
- Em “Resumo mensal”, selecionar o mês e clicar “Atualizar”.
- Abrir http://localhost:8080/swagger-ui.html e testar os endpoints.

## 🧰 (Opcional) Trocar H2 por Postgres

Adicione dependência `org.postgresql:postgresql` e troque as configs:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/expenses
    username: postgres
    password: postgres
  jpa:
    hibernate:
      ddl-auto: update
```

