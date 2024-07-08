import { QueryClientProvider } from '@tanstack/react-query';
import { Login } from './login';
import { queryClient } from '../../api';
import {
  reactRouterParameters,
  withRouter,
} from 'storybook-addon-remix-react-router';

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
    withRouter,
  ],
  parameters: {
    reactRouter: reactRouterParameters({
      location: {
        pathParams: {},
      },
      routing: {
        path: '/login',
      },
    }),
  },
};

export const Primary = {};
