import Input from './input';

export default {
  component: Input,
  title: 'Input',
  tags: ['autodocs'],
  argTypes: {
    id: { control: 'text' },
    label: { control: 'text' },
    inputType: {
      options: ['text', 'email', 'password'],
      control: { type: 'select' },
    },
    placeholder: { control: 'text' },
    isRequired: { control: 'boolean' },
  },
};

export const Primary = {
  args: {
    id: 'input',
    label: 'Input',
    inputType: 'text',
    placeholder: 'Text goes here',
    onInput: (event: React.FormEvent<HTMLInputElement>) => {
      console.log(event.currentTarget.value);
    },
  },
};

export const Invalid = {
  args: {
    id: 'input',
    label: 'Input',
    inputType: 'text',
    placeholder: 'Text goes here',
    isInvalid: true,
    validationMessage: 'Invalid input',
    onInput: (event: React.FormEvent<HTMLInputElement>) => {
      console.log(event.currentTarget.value);
    },
  },
};
