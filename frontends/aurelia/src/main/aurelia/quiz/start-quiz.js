import {inject} from "aurelia-framework";
import {BindingEngine} from "aurelia-binding";
import {signifiedRepository, StartQuizSettings, StartQuizViewModel} from "cards-frontend-aurelia";

@inject(signifiedRepository, StartQuizSettings, BindingEngine)
export class StartQuiz extends StartQuizViewModel {

  constructor(signifiedRepository, settings, bindingEngine) {
    super(signifiedRepository, settings, bindingEngine);
  }

}
