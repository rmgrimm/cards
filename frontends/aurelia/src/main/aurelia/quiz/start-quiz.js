import {inject} from "aurelia-framework";
import {signifiedRepository} from "cards-frontend-aurelia";

@inject(signifiedRepository)
export class StartQuiz {

  repository;

  constructor(signifiedRepository) {
    this.repository = signifiedRepository;
  }

}
