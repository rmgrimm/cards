import {inject} from "aurelia-framework";
import {SignifiedListViewModel, signifiedRepository} from "cards-frontend-aurelia";

@inject(signifiedRepository)
export class SignifiedList extends SignifiedListViewModel {

  constructor(repository) {
    super(repository)
  }

}
