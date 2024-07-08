import React from 'react';
import { Dialog, DialogProps } from './dialog';

export default {
  component: Dialog,
  title: 'Dialog',
  tags: ['autodocs'],
  argTypes: {
    variant: {
      options: ['visible', 'invisible'],
      control: { type: 'select' },
    },
  },
};

export const Primary = ({
  isVisible = true,
  title = 'This is a dialog',
  handleClose = () => {
    // do nothing
  },
  children = 'This is a dialog',
}: DialogProps) => {
  const [isOpen, setIsOpen] = React.useState(isVisible);

  return (
    <>
      <button onClick={() => setIsOpen(!isOpen)}>Open Dialog</button>
      <Dialog
        isVisible={isOpen}
        title={title}
        handleClose={() => setIsOpen(!isOpen)}
      >
        {children}
      </Dialog>
    </>
  );
};
