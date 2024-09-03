import React from 'react';
import { StyledCard } from './card.styled';

export interface CardProps {
  children: React.ReactNode;
  key: string;
  onClick?: () => void;
}

export const Card: React.FC<CardProps> = ({ children, key, onClick }) => (
  <StyledCard key={key} onClick={onClick}>
    {children}
  </StyledCard>
);
