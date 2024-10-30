# First version of a simple time tracker. See the new version of the project:
[Go to the new_version branch](https://github.com/Olga-Tysevich/tracker/tree/new_version)

# time-tracker

## 1. Clone the Repository

```dtd
git clone -b new_version git@github.com:Olga-Tysevich/tracker.git 
```
Generate JWT keys (for example, using - https://jwt-keys.21no.de/),
specify the expiration time of the keys,
and fill in your database paths and credentials.

## 2. Run the Databases

Navigate to the tracker directory and run the following command to start the databases:
```shell
docker-compose up --build 
```

The service is accessible at the URL: http://localhost:8080/api/tracker
