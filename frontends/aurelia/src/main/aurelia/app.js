import {PLATFORM} from "aurelia-pal";

export class App {
  configureRouter(config, router) {
    config.title = 'Cards';
    config.map([
      { route: 'welcome',         name: 'welcome',        moduleId: PLATFORM.moduleName('./welcome'),        nav: true, title: 'Welcome' },
      { route: 'users',           name: 'users',          moduleId: PLATFORM.moduleName('./users'),          nav: true, title: 'Github Users' },
      { route: 'child-router',    name: 'child-router',   moduleId: PLATFORM.moduleName('./child-router'),   nav: true, title: 'Child Router' },
      { route: ['', 'word-list'], name: 'signified-list', moduleId: PLATFORM.moduleName('./signified-list'), nav: true, title: 'Word List' },
      { route: 'start-quiz',      name: 'start-quiz',     moduleId: PLATFORM.moduleName('./start-quiz'),     nav: true, title: 'Start Quiz'}
    ]);

    this.router = router;
  }
}
