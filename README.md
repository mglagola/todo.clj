# Todo
Another todo app... written in clojure.


## Prerequisites

- You will need [Leiningen][1] 1.7.0 or above installed.
- postgresql for local testing
- [Heroku CLI][2] for deploying

## Local

### Create postgresql database
Create a local postgresql database
```
$ cd path/to/todo.clj

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

## Deploying to Heroku
The following will deploy to heroku assuming you have the [heroku cli][2] installed:

```
$ cd path/to/todo.clj

$ heroku create YOUR_APP_NAME
$ heroku addons:create heroku-postgresql:dev -a YOUR_APP_NAME
$ git push heroku master
$ heroku ps:scale web=1 -a YOUR_APP_NAME
```


## License

Copyright © 2015 Mark Glagola MIT

[1]: https://github.com/technomancy/leiningen
[2]: https://devcenter.heroku.com/articles/heroku-command
