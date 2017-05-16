import {PageObjectWelcome} from "./welcome.po";
import {PageObjectSkeleton} from "./skeleton.po";
import {config} from "../protractor.conf";

describe('cards app', function() {
  let poWelcome;
  let poSkeleton;

  beforeEach(async () => {
    poSkeleton = new PageObjectSkeleton();
    poWelcome = new PageObjectWelcome();

    await browser.loadAndWaitForAureliaPage(`http://localhost:${config.port}`);
  });

  it('should load the page and display the initial page title', async () => {
    await expect(poSkeleton.getCurrentPageTitle()).toBe('Word List | Cards');
  });

  it('should navigate to welcome page', async () => {
    await poSkeleton.navigateTo('#/app/welcome');
    await expect(poWelcome.getGreeting()).toBe('Welcome to the Aurelia Navigation App');
  });

  it('should automatically write down the fullname', async () => {
    await poSkeleton.navigateTo('#/app/welcome');
    await poWelcome.setFirstname('John');
    await poWelcome.setLastname('Doe');

    // binding is not synchronous,
    // therefore we should wait some time until the binding is updated
    await browser.wait(
      ExpectedConditions.textToBePresentInElement(
        poWelcome.getFullnameElement(), 'JOHN DOE'
      ), 200
    );
  });

  it('should show alert message when clicking submit button', async () => {
    await poSkeleton.navigateTo('#/app/welcome');
    await expect(poWelcome.openAlertDialog()).toBe(true);
  });

  it('should navigate to signified list page', async () => {
    await poSkeleton.navigateTo('#/app/words');
    await expect(poSkeleton.getCurrentPageTitle()).toBe('Github Users | Cards');
  });
});
