import React from 'react';
import { http, HttpResponse } from 'msw';
import { Listing } from './listing';
import { QueryClientProvider } from '@tanstack/react-query';
import { queryClient } from '../../api';

export default {
  component: Listing,
  title: 'Listing Page',
  tags: ['autodocs'],
  decorators: [
    (Story: React.FC): React.ReactElement => (
      <QueryClientProvider client={queryClient}>
        <Story />
      </QueryClientProvider>
    ),
  ],
};

export const Primary = {
  parameters: {
    msw: {
      handlers: {
        chipIns: http.get('http://localhost:8080/racha/findByOwner/1', () => {
          return HttpResponse.json([
            {
              id: 0,
              name: 'string',
              description: 'string',
              goal: 0,
              balance: 0,
              owner: {
                id: 0,
                name: 'string',
                email: 'string',
              },
              status: 'OPEN',
              created_at: '2024-07-31T01:28:32.728Z',
              inviteLink: 'string',
            },
            {
              id: 1,
              name: 'string',
              description: 'string',
              goal: 0,
              balance: 0,
              owner: {
                id: 0,
                name: 'string',
                email: 'string',
              },
              status: 'OPEN',
              created_at: '2024-07-31T01:28:32.728Z',
              inviteLink: 'string',
            },
          ]);
        }),
      },
    },
  },
};
