/* eslint-disable */
import List, { ListProps } from './list';

export default {
  component: List,
  title: 'List',
  tags: ['autodocs'],
  argTypes: {
    items: {
      control: 'object',
      type: { name: 'Array<ChipIn>', required: true },
      description: 'List of items',
      defaultValue: [
        { name: 'ChipIn 1', amount: 100, user: { name: 'User 1' } },
        { name: 'ChipIn 2', amount: 200, user: { name: 'User 2' } },
      ],
    },
  },
};

export const Primary = ({ items = [] }: ListProps) => <List items={items} />;
