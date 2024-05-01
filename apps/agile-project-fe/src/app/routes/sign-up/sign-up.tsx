import React from 'react';
import Input from '../../components/input/input';

export const SignUp: React.FC = () => {
  const renderForm = (): React.ReactElement => (
    <section>
        <form>
          <Input variant="email" />
        </form>
      </section>
  );

  return <div>Sign Up</div>;
};
