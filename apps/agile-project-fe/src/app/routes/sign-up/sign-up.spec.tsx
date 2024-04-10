import { render } from '@testing-library/react';

import { SignUp } from './sign-up';

describe('Sign Up Page Test Suite', () => {
  it('should render the sign up page', () => {
    render(<SignUp />);
  });
});
