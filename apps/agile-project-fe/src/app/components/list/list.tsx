import React, { FC } from 'react';

export interface ListProps {}

const List: React.FC<ListProps> = () => (
  <div data-testid="list">List Component</div>
);

export default List;
