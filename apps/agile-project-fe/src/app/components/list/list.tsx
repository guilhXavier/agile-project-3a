import React from 'react';
import { ChipIn } from '../../types';

export interface ListProps {
  items: Array<ChipIn>;
}

export const List: React.FC<ListProps> = ({ items }) => (
  <div>
    {Array.isArray(items) ? (
      items.map((item, index) => (
        <div key={index}>
          <p>{item.amount}</p>
          <p>{item.user.name}</p>
        </div>
      ))
    ) : (
      <p>No items to display</p>
    )}
  </div>
);

export default List;
