import React from 'react';
import styled, { FlattenSimpleInterpolation, css } from 'styled-components';

type ButtonVariant = 'confirm' | 'cancel' | 'neutral' | 'warning';
export interface ButtonProps {
  variant: ButtonVariant;
  text: string;
  onClick: () => void;
}

interface StyledButtonProps {
  variant: ButtonVariant;
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

const variantConfig: Record<ButtonVariant, FlattenSimpleInterpolation> = {
  confirm: ConfirmButtonStyle,
  cancel: CancelButtonStyle,
  warning: WarningButtonStyle,
  neutral: NeutralButtonStyle,
};

const StyledButton = styled.button<StyledButtonProps>`
  ${({ variant }) => variantConfig[variant]}
`;

export const Button: React.FC<ButtonProps> = ({ variant, text, onClick }) => (
  <StyledButton variant={variant} onClick={onClick}>
    {text}
  </StyledButton>
);
