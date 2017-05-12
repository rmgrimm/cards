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
    this.currentPage = this.repository.findPagedArray();
  }

}
