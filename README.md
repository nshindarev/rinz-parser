# Science theme searcher
Parser for ELibrary Database

This project is aimed to simplify theme-search in different knowledge bases, such as [elibrary](https://elibrary.ru/defaultx.asp).

You may set some theme as input string and output will contain most famous authors from different author groups. 
*Attention:* search requires some limitations, because of elibrary parse limitations.

# Настройка базы данных
1) Установить PgAdmin для управления БД PostgreSQL

2) Создать сервер с параметрами:
- name: postgres
- host name/address: localhost
- password: postgres


2) Создать базу данных: 

- name: postgres_sts
- пкм по базе -> query tool -> вставить SQL скрипт, расположенный по ссылке:
https://github.com/nshindarev/science-theme-searcher/blob/master/rinzParser/src/main/resources/scripts/tablesScripts.sql

3) Для каждой таблицы (сначала одиночные таблицы: Author, Affiliation, Cluster, Keyword, Link, Publication, AuthorToAuthor; после - все остальные) импортировать данные, находящиеся в папке "dbSnapshot"

(Импорт: ПКМ на имя таблицы -> Import/Export -> В переключателе сверху всплывшего окна выбрать "Import" -> в поле "File" выбрать одноименный таблице файл из папки dbSnaphot -> проставть значение "Header" в True -> нажать "Ok")

# Запуск (требует предварительной настройки базы)

keyword - строка, ключевое слово, по которому будет осуществляться поиск

parser - true/false, отвечает за включение/отключения модуля получения данных с eLibrary.ru

searchLimit - число, отвечает за количество сущностей на каждом уровне поиска (по умолчанию - 20)

searchLevel - число, число, отвечает за количество уровней поиска (по умолчанию - 1)

authorsSynonymy - true/false, отвечает за включение/отключения модуля поиска синонимичных авторов и их удаление из БД

affiliationsSynonymy - true/false, отвечает за включение/отключения модуля поиска синонимичных аффилиаций и их удаление из БД

clustererNew - true/false, отвечает за включение/отключения модуля кластеризации (разбиение графа на группы соавторов и представление итогового графа)

resultType - none/metric/year, отвечает за включение/отключения модуля нахождения итогового списка публикаций с учетом их сортировки по цитированию (metric) или годам (year)

## Пример запуска из терминала/командной строки (в папке с запускаемым файлом STS.jar):

java -jar STS.jar -keyword "социоинженерные атаки" -parser false -searchLimit 20 -searchLevel 1 -authorsSynonymy true -affiliationsSynonymy false -clustererNew true -resultType metric

Файл запуска находится по ссылке:
https://drive.google.com/file/d/1Gpt_f9XziuXLgZtc8wblCEloRqmyPoH3/view?usp=sharing
