Large Entity Classes
====================

This project generates large entity classes and assembles them.
You can check how long it takes to assemble them.

Build large generated classes
-----------------------------

Build with Doma 2.19.2:
```sh
$ ./gradlew :project-v2.19.2:clean :project-v2.19.2:assemble --stacktrace
```

Build with Doma 2.34.0:
```sh
$ ./gradlew :project-v2.34.0:clean :project-v2.34.0:assemble --stacktrace
```

Build with Doma 2.35.0:
```sh
$ ./gradlew :project-v2.35.0:clean :project-v2.35.0:assemble --stacktrace
```

Build with the latest SNAPSHOT version of Doma:
```sh
$ ./gradlew :project-latest:clean :project-latest:assemble --stacktrace
```
