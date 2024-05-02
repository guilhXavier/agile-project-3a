import { QueryClientProvider } from '@tanstack/react-query';
import { SignUp } from './sign-up';
import { queryClient } from '../../api';

export default {
  component: SignUp,
  title: 'Sign Up Page',
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
