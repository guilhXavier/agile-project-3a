import React from 'react';
import { useForm, Field } from '../../hooks/useForm/useForm';
import Input from '../../components/input/input';
import { Button } from '../../components/button/button';
import { useLogin, LoginForm } from '../../api/useLogin/useLogin';
import { useNavigate } from 'react-router-dom';
import { SectionStyled } from './login.styled';
import { useStore } from '../../store';

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

  const { setUser } = useStore();

  const { form, validation, get, set, isValid } = useForm<'email' | 'password'>(
    schema
  );

  const { isError, isSuccess, data } = useLogin(
    Object.fromEntries(form.entries()) as unknown as LoginForm,
    isValid && isSubmitted
  );

  const navigate = useNavigate();

  const goToRegister = () => {
    navigate('/register');
  };

  React.useEffect(() => {
    setIsSubmitted(false);
  }, [isError, isSuccess]);

  React.useEffect(() => {
    if (isSuccess && data) {
      setUser(data);
      navigate('/listing');
    }
  }, [isSuccess]);

  const handleSubmit = (event: React.FormEvent): void => {
    event.preventDefault();
    setIsSubmitted(true);
  };

  const renderForm = (): React.ReactElement => (
    <SectionStyled>
      <section className="left-section">
        <img
          src="https://img.freepik.com/fotos-gratis/o-investimento-bancario-bem-sucedido-traz-riqueza-e-prosperidade-financeira-geradas-pela-inteligencia-artificial_188544-130792.jpg?t=st=1720645123~exp=1720648723~hmac=6cb390929fe967b032f172f5cceea1ba374611302750236c4c8b26911d273344&w=1380"
          alt="pig_image"
        />
      </section>
      <section className="right-section">
        <header>
          <h1>Rachadinha</h1>
          <p>Que bom te ver aqui! Organize seu esquema, já!</p>
        </header>
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
          <span className="divider"></span>
          <Button variant="neutral" text="Cadastre-se" onClick={goToRegister} />
        </form>
        <footer>
          <p>
            &#169;{' '}
            <a
              href="https://www.instagram.com/aprimeiradeads/"
              target="_blank"
              rel="noreferrer"
            >
              aprimeiradeads
            </a>{' '}
            2024
          </p>
        </footer>
      </section>
    </SectionStyled>
  );

  return <div>{renderForm()}</div>;
};
