
INSERT INTO worker (NAME, BIRTHDAY, LEVEL, SALARY)
SELECT
    'Працівник ' || ID,
    DATEADD(YEAR, ID - 1, DATE '1980-01-01'),
    CASE
        WHEN ID % 4 = 0 THEN 'Trainee'
        WHEN ID % 4 = 1 THEN 'Junior'
        WHEN ID % 4 = 2 THEN 'Middle'
        ELSE 'Senior'
    END,
    100 + (ID % 4 * 1000)
FROM
    generate_series(1, 10) AS g(ID);

-- Вставка клієнтів
INSERT INTO client (NAME)
SELECT
    'Клієнт ' || ID
FROM
    generate_series(1, 5) AS g(ID);

-- Вставка проєктів
INSERT INTO project (CLIENT_ID, START_DATE, FINISH_DATE)
SELECT
    (ID % 5) + 1,
    DATEADD(MONTH, -1 * (ID - 1), CURRENT_DATE),
    DATEADD(MONTH, (ID % 100), CURRENT_DATE)
FROM
    generate_series(1, 10) AS g(ID);

INSERT INTO PROJECT_WORKER (PROJECT_ID, WORKER_ID)
SELECT  ID, ROUND (RAND() * 9 + 1)
FROM  PROJECT;