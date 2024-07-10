import React from 'react';
import styled, { FlattenSimpleInterpolation, css } from 'styled-components';

type ButtonVariant = 'confirm' | 'cancel' | 'neutral' | 'warning';
export interface ButtonProps {
  variant: ButtonVariant;
  text: string;
  onClick?: () => void;
}

interface StyledButtonProps {
  variant: ButtonVariant;
}

const DefaultButtonStyle = css`
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 8px 0;
  width: 360px;
  height: 40px;
  border-radius: 0.6em;
  border: none;
  cursor: pointer;
  font-size: 16px;
`;

const ConfirmButtonStyle = css`
  background-color: #1b850a;
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
  ${DefaultButtonStyle}
  ${({ variant }) => variantConfig[variant]}
`;

export const Button: React.FC<ButtonProps> = ({ variant, text, onClick }) => (
  <StyledButton variant={variant} onClick={onClick}>
    {text}
  </StyledButton>
);
