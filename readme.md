# Pharos-Asset-Match

## How to run

To run the project using Docker Compose, follow these steps:

1. **Build the Spring Boot JAR**

   From the root directory of the project, build the Spring Boot project using Maven:

   ```bash
   mvn clean package
   ```

   After the build completes, copy the generated `springboot-template-0.0.1-SNAPSHOT.jar` from the `target` directory to the `deploy` directory:

   ```bash
   cp target/springboot-template-0.0.1-SNAPSHOT.jar deploy/AssetMatch.jar
   ```

2. **Copy SQL Files**

   Ensure that both `data_gen.sql` and `asset_match.sql` are present in the `deploy` directory.

3. **Run Docker Compose**

   Open a terminal, navigate to the `deploy` directory, and run:

   ```
   docker-compose up -d
   ```

   This command will build and start all services defined in `deploy/docker-compose.yml`:
   - **Postgres** database (with initialization from `asset_match.sql`)
   - **Flask** service
   - **Spring Boot** service (running `AssetMatch.jar` and using `data_gen.sql`)

4. **Access the Services**

The Service will be available at [http://localhost:18080](http://localhost:18080)
