import { render } from '@testing-library/react';

import { Form } from './form';

describe('Form component', () => {
  it('should render successfully', () => {
    const { baseElement } = render(
      <Form
        onSubmit={() => {
          console.log('here');
        }}
      />
    );

    expect(baseElement).toBeTruthy();
  });
});
