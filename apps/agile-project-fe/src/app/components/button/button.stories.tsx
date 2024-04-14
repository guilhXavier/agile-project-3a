import { Button } from './button';

export default {
  component: Button,
  title: 'Button',
  tags: ['autodocs'],
  argTypes: {
    variant: {
      options: ['confirm', 'cancel', 'neutral', 'warning'],
      control: { type: 'select' },
    },
  },
};

export const Primary = (args) => (
  <Button
    {...args}
    text="Confirm"
    onClick={() => {
      console.log('here');
    }}
  />
);
