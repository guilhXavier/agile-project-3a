import { render } from '@testing-library/react';

import { Button } from './button';

describe('Button component', () => {
  it('should render successfully', () => {
    const { baseElement } = render(
      <Button
        variant="confirm"
        text="Confirm"
        onClick={() => {
          console.log('here');
        }}
      />
    );

    expect(baseElement).toBeTruthy();
  });
});
