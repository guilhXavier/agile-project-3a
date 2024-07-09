import React from 'react';
import { StyledInput } from './input.styled';
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
