import { Form } from './form';

export default {
  component: Form,
  title: 'Form',
  tags: ['autodocs'],
};

export const Primary = () => <Form onSubmit={() => console.log('here')} />;
