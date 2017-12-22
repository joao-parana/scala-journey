# Parent POM

```
Scala - 2.11
Java  - 1.8
```

Veja a seguir como fazer **build/run** 

### Build

Basta fazer isso:

```bash
mvn -Drat.ignoreErrors=true \
    -Dcheckstyle.skip \
    -Dmaven.test.skip=true \
    -Denforcer.skip=true \
    -Dspark.daily="-SNAPSHOT" \
    -N clean install
```

o parâmetro `spark.daily` é opcional.

