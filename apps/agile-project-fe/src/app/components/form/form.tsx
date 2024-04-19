import React from 'react';
import styled from 'styled-components';
import { Button } from '../button/button';
import { Input } from '../input/input';

export interface FormProps {
  onSubmit: (value: string) => void;
}

const StyledForm = styled.form`
  display: flex;
  flex-direction: column;
`;

export const Form: React.FC<FormProps> = ({ onSubmit }) => {
  const [value] = React.useState('');

  const handleSubmit = (event: React.FormEvent) => {
    event.preventDefault();
    onSubmit(value);
  };

  return (
    <StyledForm onSubmit={handleSubmit}>
      <Input variant="text" />
      <Button variant="confirm" text="Submit" onClick={() => onSubmit(value)} />
    </StyledForm>
  );
};
