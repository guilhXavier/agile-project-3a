import { render } from '@testing-library/react';

import { Login } from './login';

describe('Login Page Test Suite', () => {
  it('renders the login page', () => {
    const { baseElement } = render(<Login />);
    expect(baseElement).toBeTruthy();
  });
});
