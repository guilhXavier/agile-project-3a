import Input from './input';

export default {
  component: Input,
  title: 'Input',
  tags: ['autodocs'],
  argTypes: {
    variant: {
      options: ['text', 'email', 'password'],
      control: { type: 'select' },
    },
  },
};

export const Primary = {};
