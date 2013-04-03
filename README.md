# Cyclist, a Cyclic Dependencies Detection Library

Cyclist is a tiny Clojure library that detects cyclic dependencies between named
entities.


## Project Maturity

Cyclist is a young library. We do not rule out public API changes before
the final `1.0` release.



## Artifacts

Cyclist artifacts are [released to Clojars](https://clojars.org/clojurewerkz/cyclist). If you are using Maven, add the following repository
definition to your `pom.xml`:

``` xml
<repository>
  <id>clojars.org</id>
  <url>http://clojars.org/repo</url>
</repository>
```

### The Most Recent Release

With Leiningen:

    [clojurewerkz/cyclist "1.0.0-beta2"]


With Maven:

    <dependency>
      <groupId>clojurewerkz</groupId>
      <artifactId>cyclist</artifactId>
      <version>1.0.0-beta2</version>
    </dependency>



## Documentation

Cyclist operates on maps with two keys: `:name` and `:dependencies`:

``` clojure
{:name :a :dependencies #{:b}}
{:name :b :dependencies #{:c}}
{:name :c :dependencies #{}}
```

`clojurewerkz.cyclist.dependencies/has-cyclic-dependecies?` takes node and
a collection of all graph nodes and returns true if the graph has cyclic
dependencies:

``` clojure
(let [a {:name 'a :dependencies #{'b}}
      b {:name 'b :dependencies #{'c}}
      c {:name 'c :dependencies #{}}
      xs [a b c]]
  (cy/has-cyclic-dependencies? x xs))
;= false

(let [a {:name 'a :dependencies #{'b}}
      b {:name 'b :dependencies #{'a}}
      c {:name 'c :dependencies #{}}
      xs [a b c]]
  (cy/has-cyclic-dependencies? x xs))
;= true
```

`clojurewerkz.cyclist.dependencies/detect` takes a collection of graph nodes
and returns the first one that has cyclic dependencies:

``` clojure
(let [a {:name 'a :dependencies #{'b}}
      b {:name 'b :dependencies #{'c}}
      c {:name 'c :dependencies #{}}
      xs [a b c]]
  (cy/detect xs))
;= nil

(let [a {:name 'a :dependencies #{'b}}
      b {:name 'b :dependencies #{'a}}
      c {:name 'c :dependencies #{}}
      xs [a b c]]
  (cy/detect xs))
;= {:name 'a :dependencies #{'b}}

(let [a {:name 'a :dependencies #{'b}}
      b {:name 'b :dependencies #{'c}}
      c {:name 'c :dependencies #{'a}}
      xs [a b c]]
  (cy/detect xs))
;= {:name 'a :dependencies #{'b}}
```


## Community

To subscribe for announcements of releases, important changes and so on, please follow [@ClojureWerkz](https://twitter.com/#!/clojurewerkz) on Twitter.



## Supported Clojure versions

Cyclic is built from the ground up for Clojure 1.3 and up.


## Continuous Integration Status

[![Continuous Integration status](https://secure.travis-ci.org/michaelklishin/cyclist.png)](http://travis-ci.org/michaelklishin/cyclist)



## Cyclist Is a ClojureWerkz Project

Cyclist is part of the [group of Clojure libraries known as ClojureWerkz](http://clojurewerkz.org), together with
 * [Monger](http://clojuremongodb.info)
 * [Langohr](https://github.com/michaelklishin/langohr)
 * [Elastisch](https://github.com/clojurewerkz/elastisch)
 * [Welle](http://clojureriak.info)
 * [Neocons](http://clojureneo4j.info)
 * [Quartzite](https://github.com/michaelklishin/quartzite) and several others.


## Development

Cyclist uses [Leiningen
2](https://github.com/technomancy/leiningen/blob/master/doc/TUTORIAL.md). Make
sure you have it installed and then run tests against supported
Clojure versions using

    lein2 all test

Then create a branch and make your changes on it. Once you are done
with your changes and all tests pass, submit a pull request on GitHub.



## License

Copyright (C) 2013 Michael S. Klishin, Alex Petrov.

Double licensed under the [Eclipse Public License](http://www.eclipse.org/legal/epl-v10.html) (the same as Clojure) or
the [Apache Public License 2.0](http://www.apache.org/licenses/LICENSE-2.0.html).
