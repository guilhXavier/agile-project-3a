import React from 'react';
import { StyledCard } from './card.styled';

export interface CardProps {
  children: React.ReactNode;
  key: string;
}

export const Card: React.FC<CardProps> = ({ children, key }) => (
  <StyledCard key={key}>{children}</StyledCard>
);
