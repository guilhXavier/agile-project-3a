import React from 'react';
import styled from 'styled-components';

type InputVariant = 'text' | 'email' | 'password';

export interface InputProps {
  variant: InputVariant;
}

const StyledInput = styled.div``;

const variantConfig: Record<
  InputVariant,
  { type: InputVariant; placeholder: string }
> = {
  text: { type: 'text', placeholder: 'Digite o texto aqui' },
  email: { type: 'email', placeholder: 'Digite o email' },
  password: { type: 'password', placeholder: 'Digite a senha' },
};

export const Input: React.FC<InputProps> = ({ variant }) => {
  const { type, placeholder } = variantConfig[variant] || {
    type: 'text',
    placeholder: 'default',
  };

  return (
    <StyledInput>
      <input type={type} placeholder={placeholder} />
    </StyledInput>
  );
};

export default Input;
