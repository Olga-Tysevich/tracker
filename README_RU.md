
# time-tracker

## 1. Клонировать репозиторий

```dtd
git clone https://git-revinci.krainet.by/belrw/belrw-library.git
```
### Переключить ветку проекта new_version!!!

Создать в каталоге tracker файл **.env** на основе **.env-example**
Сгенерировать jwt ключи (например с помощью - https://jwt-keys.21no.de/),
указать время жизни ключей
Прописать свои пути и данные для бд


## 2. Запуск баз данных

Перейти в каталог tracker и выполнить следующую команду для запуска базы данных:
```shell
docker-compose up --build 
```

Сервис доступен по URL http://localhost:8500/admin/
