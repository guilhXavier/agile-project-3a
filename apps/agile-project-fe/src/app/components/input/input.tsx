import React from 'react';
import styled from 'styled-components';

export interface InputProps {
  id: string;
  inputType: 'text' | 'email' | 'password';
  label: string;
  placeholder: string;
  onInput: (event: React.FormEvent<HTMLInputElement>) => void;
}

const StyledInput = styled.div`
  display: flex;
  flex-direction: column;
  max-width: 256px;

  label {
    padding-bottom: 0.5em;
  }
`;

export const Input: React.FC<InputProps> = ({
  id,
  inputType,
  label,
  placeholder,
  onInput,
}) => (
  <StyledInput>
    <label htmlFor={id}>{label}:</label>
    <input
      id={id}
      type={inputType}
      placeholder={placeholder}
      onInput={onInput}
    />
  </StyledInput>
);

export default Input;
