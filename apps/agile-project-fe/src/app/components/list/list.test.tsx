import { render } from '@testing-library/react';
import '@testing-library/jest-dom';
import List from './list';

describe('List component', () => {
  test('should render successfully', () => {
    const { baseElement } = render(<List items={[]} />);

    expect(baseElement).toBeTruthy();
  });
});
