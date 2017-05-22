# Cards

This is a ground-up rewrite of my previous (and never released) Android app. There
are multiple purposes, both in writing and running this code. The prominent
two are as follows:

1. Learn and practice newer technologies, such as [Kotlin][kotlin] and 
[Aurelia][aurelia].
2. Build an app to help maintain and expand vocabulary in foreign languages

In fact, the first point was the purpose of my original Android app -- to learn
Android while building an app for personal use.

## Goal

The goal of this project is to provide a system for training vocabulary of foreign
languages. Currently the vision is to include the following within the overall system:

1. A backend server to store dictionaries, users, learning progress, and manage
synchronization to front-end clients
2. A web app for creating and updating words and translations, and for training
3. An Android app for training in both online and offline modes

## Project structure

The project is structured with the following hierarchy. Except where noted, each
subproject targets both JVM and JS.

- **cards-root**: This project, which composes several submodules
  - **cards-parent**: The parent project, to define common settings and all library
  / plugin versions. (Remember, Maven inheritance is different from composition.)
  - **cards-base**: The main set of interfaces and logic for the system. This is
  shared by most of the other subprojects, and contains the important logic.
  - **cards-support**: Various support items, not necessarily specific to this
  *Cards* project.
    - **cards-support-aurelia**: Kotlin definitions for the Aurelia classes, along
    with project-agnostic helper methods to bring Aurelia support to feel more
    native to Kotlin. This subproject targets only JavaScript
    - **cards-support-kotlin**: Interfaces for things that seem like they should be
    part of the Kotlin stdlib, such as a random number generator.
    - **cards-support-kotlin-jvm**: Implementations of *cards-support-kotlin* that
    target the JVM. This also includes a few "hacks", such as a JVM-annotation named
    `@kotlin.js.JsName` to facilitate easier writing of shared Kotlin code.
    - **cards-support-kotlin-js**: Implementation of *cards-support-kotlin* that
    targets a JavaScript interpreter.
  - **cards-backends**: 
    - **cards-backend-in-memory**: An in-memory backend. Basically, this stores the
    data in Kotlin collections. It is intended for development, testing, and
    demonstration purposes.
    - _others planned_
  - **cards-frontends**:
    - **cards-frontend-aurelia**: A web UI, built using a combination of
    [Kotlin][kotlin] and [Aurelia][aurelia]
    - _others planned_


## Building

### Build requirements

In order to build, the only thing needed should be a copy of the Java JDK. All other
dependencies will be automatically retrieved either through the Maven wrapper,
or through NPM / Yarn.

### Platform notes

The following build instructions will refer to `./mvnw` for running the Maven
wrapper. This works for POSIX-like build hosts. On Windows hosts, replace
`./mvnw` with `mvnw.cmd`.

### Building

To build the entire project including all apps and tests, run the following from
the project root:

```bash
./mvnw clean install
```

## Links

* Aurelia: [homepage][aurelia] - [GitHub][aurelia-github]
* Kotlin: [homepage][kotlin]

[kotlin]: https://kotlinlang.org
[aurelia]: http://aurelia.io
[aurelia-github]: https://github.com/aurelia/framework

## License

This project and all subprojects are licensed under the MIT license:

>Copyright 2017 Robert Grimm
>
>Permission is hereby granted, free of charge, to any person obtaining a copy
>of this software and associated documentation files (the "Software"), to deal
>in the Software without restriction, including without limitation the rights to
>use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
>the Software, and to permit persons to whom the Software is furnished to do so,
>subject to the following conditions:
>
>The above copyright notice and this permission notice shall be included in
>all copies or substantial portions of the Software.
>
>THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
>IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
>FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
>AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
>LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
>OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
>SOFTWARE.
