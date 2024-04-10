import { Button } from './button';

export default {
  component: Button,
  title: 'Button',
  tags: ['autodocs'],
};

export const Primary = () => (
  <Button
    variant="confirm"
    text="Confirm"
    onClick={() => {
      console.log('here');
    }}
  />
);
