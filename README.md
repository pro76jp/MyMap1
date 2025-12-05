# MyMap1

## Database configuration
- Default profile uses in-memory H2 so the app starts without an external database.
- To use MySQL, run with the `mysql` Spring profile and provide credentials via environment variables:
  - `MYSQL_URL` (default `jdbc:mysql://localhost:3306/game_master_admin?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC`)
  - `MYSQL_USERNAME` (default `root`)
  - `MYSQL_PASSWORD` (default `password`)
- Example: `SPRING_PROFILES_ACTIVE=mysql MYSQL_PASSWORD=secret ./gradlew bootRun`

### MySQL quick start (Docker)
If you see `Communications link failure` with the MySQL profile, start a local MySQL instance first:

```
docker run --name game-master-mysql -e MYSQL_ROOT_PASSWORD=secret \
  -e MYSQL_DATABASE=game_master_admin -p 3306:3306 -d mysql:8

# Then run the app with matching credentials
SPRING_PROFILES_ACTIVE=mysql MYSQL_PASSWORD=secret ./gradlew bootRun
```

You can also stick to the default H2 profile for quick UI testing without any database server.
