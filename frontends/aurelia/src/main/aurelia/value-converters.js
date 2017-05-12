
export class SortValueConverter {
  toView(array, propertyName, direction) {
    const factor = direction === 'descending' ? -1 : 1;
    return array.sort((a, b) => {
      if (typeof a === typeof b && typeof a === 'number') {
        return (a[propertyName] - b[propertyName]) * factor;
      } else if (a < b) {
        return -1
      } else if (a === b) {
        return 0
      } else {
        return 1
      }
    });
  }
}
