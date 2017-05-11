import {inject} from "aurelia-framework";
import {signifiedRepository} from "cards-frontend-aurelia";

@inject(signifiedRepository)
export class SignifiedList {

  constructor(signifiedRepository) {
    this.repository = signifiedRepository;
  }

}
