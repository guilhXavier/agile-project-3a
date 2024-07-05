import React from 'react';
import { useForm, Field } from '../../hooks/useForm/useForm';
import Input from '../../components/input/input';
import { Button } from '../../components/button/button';
import { useLogin, LoginForm } from '../../api/useLogin/useLogin';

const schema = {
  email: {
    type: Field.Email,
    validation: {
      predicate: (value: string) => value.includes('@'),
      message: 'Invalid email address',
    },
  },
  password: {
    type: Field.Password,
    validation: {
      predicate: (value: string) => value.length >= 8,
      message: 'Password must be at least 8 characters',
    },
  },
};

export const Login: React.FC = () => {
  const [isSubmitted, setIsSubmitted] = React.useState<boolean>(false);

  const { form, validation, get, set, isValid } = useForm<'email' | 'password'>(
    schema
  );

  const { isError, isSuccess } = useLogin(
    Object.fromEntries(form.entries()) as unknown as LoginForm,
    isValid && isSubmitted
  );

  React.useEffect(() => {
    // Show error message
    setIsSubmitted(false);
  }, [isError]);

  React.useEffect(() => {
    // Redirect
    setIsSubmitted(false);
  }, [isSuccess]);

  const handleSubmit = (event: React.FormEvent): void => {
    event.preventDefault();
    setIsSubmitted(true);
  };

  const renderForm = (): React.ReactElement => (
    <section>
      <form onSubmit={handleSubmit}>
        <Input
          id="email"
          inputType="email"
          label="Email"
          placeholder="fulano@gmail.com"
          value={get('email')}
          onInput={(event) => set('email', event.currentTarget.value)}
          isValid={validation.get('email')?.isValid}
          validationMessage={validation.get('email')?.message}
        />
        <Input
          id="password"
          inputType="password"
          label="Senha"
          placeholder="********"
          value={get('password')}
          onInput={(event) => set('password', event.currentTarget.value)}
          isValid={validation.get('password')?.isValid}
          validationMessage={validation.get('password')?.message}
        />
        <Button variant="confirm" text="Entrar" />
      </form>
    </section>
  );

  return <div>{renderForm()}</div>;
};
