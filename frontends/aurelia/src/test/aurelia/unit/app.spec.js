import {App} from "app";

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
    sut = new App();
    sut.configureRouter(mockedRouter, mockedRouter);
  });

  it('contains a router property', () => {
    expect(sut.router).toBeDefined();
  });

  it('configures the router title', () => {
    expect(sut.router.title).toEqual('Cards');
  });

  it('should have a signifier list route', () => {
    expect(sut.router.routes).toContainEqual({ route: ['', 'word-list'], name: 'signified-list',  moduleId: './signified-list', nav: true, title: 'Word List' });
  });

  it('should have a quiz route', () => {
    expect(sut.router.routes).toContainEqual({ route: 'start-quiz', name: 'start-quiz', moduleId: './start-quiz', nav: true, title: 'Start Quiz' });
  });

});
