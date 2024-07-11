import React from 'react';
import { ChipIn } from '../../types';
import { StyledList } from './list.styled';

export interface ListProps {
  items: Array<ChipIn>;
}

export const List: React.FC<ListProps> = ({ items }) => (
  <StyledList>
    <ul>
      {items.map((item, index) => (
        <li key={index}>
          <p>Id: {item.id}</p>
          <p>User: {item.user.name}</p>
          <p>Amount: {item.amount}</p>
        </li>
      ))}
    </ul>
  </StyledList>
);

export default List;
