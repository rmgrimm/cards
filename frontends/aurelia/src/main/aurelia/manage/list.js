import {inject} from "aurelia-framework";
import {SignifiedListSettings, SignifiedListViewModel, signifiedRepository} from "cards-frontend-aurelia";

@inject(signifiedRepository, SignifiedListSettings)
export class SignifiedList extends SignifiedListViewModel {

  constructor(repository, settings) {
    super(repository, settings)
  }

}
