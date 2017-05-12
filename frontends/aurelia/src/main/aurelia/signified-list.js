import {inject} from "aurelia-framework";
import {signifiedRepository} from "cards-frontend-aurelia";

@inject(signifiedRepository)
export class SignifiedList {

  repository;
  displayLocales;
  currentPage;

  constructor(signifiedRepository) {
    this.repository = signifiedRepository;
    this.displayLocales = this.repository.localeArray;
    // TODO(rmgrimm): Stop using the generated name and use a better method name
    this.currentPage = this.repository.findPagedArray_rl28od$$default();

  }

}
