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
