import React from 'react';
import styled from 'styled-components';

type InputVariant = 'text' | 'email' | 'password';

export interface InputProps {
  variant: InputVariant;
  placeholder?: string;
}

const variantConfig: Record<InputVariant, { type: InputVariant }> = {
  text: { type: 'text' },
  email: { type: 'email' },
  password: { type: 'password' },
};

const StyledInput = styled.div``;

export const Input: React.FC<InputProps> = ({ variant, placeholder }) => {
  const { type } = variantConfig[variant] || {
    type: 'text',
  };

  return (
    <StyledInput>
      <input type={type} placeholder={placeholder} />
    </StyledInput>
  );
};

export default Input;
