
# time-tracker

## 1. Clone the Repository

```dtd
git clone -b new_version git@github.com:Olga-Tysevich/tracker.git 
```
### Ensure that the project branch is new_version!!!

Create a .env file in the tracker directory based on .env-example.
Generate JWT keys (for example, using - https://jwt-keys.21no.de/),
specify the expiration time of the keys,
and fill in your database paths and credentials.


## 2. Run the Databases

Navigate to the tracker directory and run the following command to start the databases:
```shell
docker-compose up --build 
```

The service is accessible at the URL: http://localhost:8080/api/tracker
