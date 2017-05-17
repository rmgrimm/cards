import {PLATFORM} from "aurelia-pal";

export class Top {
  configureRouter(config, router) {
    config.title = 'Cards';

    config.map([
      { route: '', redirect: 'cards' },
      { route: 'cards/',         name: 'main-display', moduleId: PLATFORM.moduleName('./main-display'),  title: ''},
      { route: 'cards/quiz/:id', name: 'quiz',         moduleId: PLATFORM.moduleName('./quiz/run-quiz'), title: 'Quiz'}
    ]);

    this.router = router;
  }
}
