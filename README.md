This is a Scala PlayFramework App
You must run it in debug mode for now

First install sbt
For windows that is at
https://www.scala-sbt.org/0.13/docs/Installing-sbt-on-Windows.html

Then go into the project folder and run


### `sbt run`

This will setup port 9000

Once running, the first time you hit the app, I will load the data into memory, that takes about 30 seconds, then all calls are less then 300 ms


To run the test, just run

### `sbt test`