import { QueryClientProvider } from '@tanstack/react-query';
import { renderHook, act, waitFor } from '@testing-library/react';
import { http, HttpResponse } from 'msw';
import { server } from '../../mocks/node';
import { queryClient } from '../index';
import { useCreateChipIn } from './useCreateChipIn';

describe('useCreateChipIn test suite', () => {
  const createChipInHandler = http.post(
    'http://localhost:8080/racha/create',
    () => {
      return HttpResponse.json({
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
        created_at: '2024-07-31T00:55:14.190Z',
        inviteLink: 'string',
      });
    }
  );

  beforeAll(() => {
    server.listen();
    server.use(...[createChipInHandler]);
  });

  it('should create a new chip in', async () => {
    const { result } = renderHook(() => useCreateChipIn(), {
      wrapper: ({ children }) => (
        <QueryClientProvider client={queryClient}>
          {children}
        </QueryClientProvider>
      ),
    });

    act(() => {
      result.current.createChipIn({
        name: 'Chip in test',
        description: 'Chip in description',
        goal: 100,
        password: '123456',
        ownerId: 1,
      });
    });

    await waitFor(() => expect(result.current.isSuccess).toBeTruthy());
  });
});
