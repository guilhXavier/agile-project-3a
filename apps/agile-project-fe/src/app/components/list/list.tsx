import React from 'react';
import { ChipIn } from '../../types';

export interface ListProps {
  items: Array<ChipIn>;
}

export const List: React.FC<ListProps> = ({ items }) => (
  <ul>
    {Array.isArray(items) ? (
      items.map((item, index) => (
        <li key={index}>
          <p>Id: {item.id}</p>
          <p>User: {item.user.name}</p>
          <p>Amount: {item.amount}</p>
        </li>
      ))
    ) : (
      <li>No items to display</li>
    )}
  </ul>
);

export default List;
