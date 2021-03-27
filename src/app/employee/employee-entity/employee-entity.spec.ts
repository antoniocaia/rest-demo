import { Employee } from './employee-entity';

describe('Employee', () => {
  it('should create an instance', () => {
    expect(new Employee()).toBeTruthy();
  });
});
