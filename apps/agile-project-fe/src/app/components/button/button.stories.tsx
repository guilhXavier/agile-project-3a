import { Button, ButtonProps } from './button';

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

export const Primary = ({
  text = 'Default',
  variant = 'neutral',
}: ButtonProps) => (
  <Button
    variant={variant}
    text={text}
    onClick={() => {
      console.log('here');
    }}
  />
);
