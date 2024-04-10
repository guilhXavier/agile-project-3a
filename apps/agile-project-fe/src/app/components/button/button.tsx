import React from 'react';
import styled from 'styled-components';

export interface ButtonProps {
  variant: 'confirm' | 'cancel' | 'neutral' | 'warning';
  text: string;
  onClick: () => void;
}

const StyledButton = styled.button`
  background-color: red;
`;

export const Button: React.FC<ButtonProps> = ({ variant, text, onClick }) => (
  <StyledButton>Test</StyledButton>
);
