import {MainDisplay} from "main-display";

class RouterStub {
  routes;

  configure(handler) {
    handler(this);
  }

  map(routes) {
    this.routes = routes;
  }
}

describe('the App module', () => {
  let sut;
  let mockedRouter;

  beforeEach(() => {
    mockedRouter = new RouterStub();
    sut = new MainDisplay();
    sut.configureRouter(mockedRouter, mockedRouter);
  });

  it('contains a router property', () => {
    expect(sut.router).toBeDefined();
  });

  it('configures the router title', () => {
    expect(sut.router.title).toEqual(undefined);
  });

  it('should have a signifier list route', () => {
    expect(sut.router.routes).toContainEqual({ route: 'words', name: 'signified-list',  moduleId: './manage/list', nav: true, title: 'Word List' });
  });

  it('should have a quiz route', () => {
    expect(sut.router.routes).toContainEqual({ route: 'setup-quiz', name: 'setup-quiz', moduleId: './quiz/start-quiz', nav: true, title: 'Start Quiz' });
  });

});
