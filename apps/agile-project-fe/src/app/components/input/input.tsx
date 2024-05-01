import React from 'react';
import styled from 'styled-components';

export interface InputProps {
  id: string;
  inputType: 'text' | 'email' | 'password';
  label: string;
  value: string;
  placeholder: string;
  onInput: (event: React.FormEvent<HTMLInputElement>) => void;
  isValid?: boolean;
  validationMessage?: string;
  isRequired?: boolean;
}

const StyledInput = styled.div`
  display: flex;
  flex-direction: column;
  max-width: 256px;

  label {
    padding-bottom: 0.5em;
  }

  span {
    color: red;
    padding-top: 0.5em;
  }
`;

export const Input: React.FC<InputProps> = ({
  id,
  inputType,
  value,
  label,
  placeholder,
  onInput,
  validationMessage,
  isValid = true,
  isRequired = true,
}) => {
  const [isTouched, setIsTouched] = React.useState(false);

  return (
    <StyledInput>
      <label htmlFor={id}>{label}:</label>
      <input
        id={id}
        type={inputType}
        placeholder={placeholder}
        value={value}
        required={isRequired}
        onInput={onInput}
        onBlur={() => setIsTouched(true)}
      />
      {!isValid && isTouched && <span>{validationMessage}</span>}
    </StyledInput>
  );
};

export default Input;
