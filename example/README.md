# Example usage
1. First step is to build the install distribution
```bash
./gradlew clean :installDist
```
2. Run **Watermelon** providing the process direcotory, that contains **process.json** file
```bash
./build/install/watermelon/bin/watermelon example
```
3. Checkout the **example/output** directory, You should see generated images