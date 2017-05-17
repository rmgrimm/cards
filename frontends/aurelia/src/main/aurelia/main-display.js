import {PLATFORM} from "aurelia-pal";

export class MainDisplay {
  configureRouter(config, router) {
    config.map([
      { route: '', redirect: 'words' },
      { route: 'welcome',     name: 'welcome',        moduleId: PLATFORM.moduleName('./welcome'),         nav: true, title: 'Welcome'},
      { route: 'words',       name: 'signified-list', moduleId: PLATFORM.moduleName('./manage/list'),     nav: true, title: 'Word List' },
      { route: 'setup-quiz',  name: 'setup-quiz',     moduleId: PLATFORM.moduleName('./quiz/start-quiz'), nav: true, title: 'Start Quiz' },
    ]);

    this.router = router;
  }
}
