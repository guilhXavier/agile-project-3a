import React from 'react';
import styled, { css } from 'styled-components';

export interface ButtonProps {
  variant: 'confirm' | 'cancel' | 'neutral' | 'warning';
  text: string;
  onClick: () => void;
}

interface StyledButtonProps {
  variant: 'confirm' | 'cancel' | 'neutral' | 'warning';
}

const ConfirmButtonStyle = css`
  background-color: green;
  color: white;
`;

const CancelButtonStyle = css`
  background-color: red;
  color: white;
`;

const WarningButtonStyle = css`
  background-color: yellow;
  color: black;
`;

const NeutralButtonStyle = css`
  background-color: light-grey;
  color: black;
`;

const StyledButton = styled.button<StyledButtonProps>`
  ${({ variant }) => {
    switch (variant) {
      case 'confirm':
        return ConfirmButtonStyle;
      case 'cancel':
        return CancelButtonStyle;
      case 'warning':
        return WarningButtonStyle;
      default:
        return NeutralButtonStyle;
    }
  }}
`;

export const Button: React.FC<ButtonProps> = ({ variant, text, onClick }) => (
  <StyledButton variant={variant} onClick={onClick}>
    {text}
  </StyledButton>
);
