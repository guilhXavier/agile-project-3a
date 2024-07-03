import { QueryClientProvider } from '@tanstack/react-query';
import { Login } from './login';
import { queryClient } from '../../api';

export default {
  component: Login,
  title: 'Login Page',
  tags: ['autodocs'],
  decorators: [
    (Story: React.FC): React.ReactElement => (
      <QueryClientProvider client={queryClient}>
        <Story />
      </QueryClientProvider>
    ),
  ],
};

export const Primary = {};
