# Todo
Another todo app... written in clojure.


## Prerequisites

- You will need [Leiningen][1] 1.7.0 or above installed.
- postgresql for local testing

[1]: https://github.com/technomancy/leiningen

## Local

### Create postgresql database
Create a local postgresql database
```
$ initdb pg
$ postgres -D pg
$ createdb todo
```

### Running

To start a web server for the application, run:
```
$ lein ring server
```

or in your clojure repl:
```clojure
;; in clojure repl
=> (use 'core.repl')
=> (start-server)
```

## License

Copyright © 2015 Mark Glagola MIT
