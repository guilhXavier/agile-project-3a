import React from 'react';
import { Field, useForm } from '../../hooks/useForm/useForm';
import Input from '../../components/input/input';
import { Button } from '../../components/button/button';
import { useRegister, UserForm } from '../../api/useRegister/useRegister';

const schema = {
  username: {
    type: Field.Text,
  },
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
  confirmPassword: {
    type: Field.Password,
    validation: {
      predicate: (value: string) => value.length >= 8,
      message: 'Password must be at least 8 characters',
    },
  },
};

export const SignUp: React.FC = () => {
  const { mutate } = useRegister();

  const { form, validation, get, set } = useForm<
    'username' | 'email' | 'password' | 'confirmPassword'
  >(schema);

  const handleSubmit = (): void => {
    mutate(Object.fromEntries(form.entries()) as unknown as UserForm);
  };

  const renderForm = (): React.ReactElement => (
    <section>
      <form onSubmit={handleSubmit}>
        <Input
          id="username"
          inputType="text"
          label="Nome de usuário"
          placeholder="Fulano da Silva"
          value={get('username')}
          onInput={(event) => set('username', event.currentTarget.value)}
        />
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
        <Input
          id="confirmPassword"
          inputType="password"
          label="Confirme a senha"
          placeholder="********"
          value={get('confirmPassword')}
          onInput={(event) => set('confirmPassword', event.currentTarget.value)}
          isValid={validation.get('confirmPassword')?.isValid}
          validationMessage={validation.get('confirmPassword')?.message}
        />
        <Button variant="confirm" text="Enviar" />
      </form>
    </section>
  );

  return <div>{renderForm()}</div>;
};
