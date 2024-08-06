import { http, HttpResponse } from 'msw';
import { QueryClientProvider } from '@tanstack/react-query';
import { renderHook, waitFor } from '@testing-library/react';
import { server } from '../../mocks/node';
import { useFindChipInsByOwnerId } from './useFindChipInsByOwnerId';
import { queryClient } from '../index';

describe('useFindChipInsByOwnerId test suite', () => {
  const findChipInsHandler = http.get(
    'http://localhost:8080/racha/findByOwner/1',
    () => {
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
      ]);
    }
  );

  beforeAll(() => {
    server.listen();
    server.use(...[findChipInsHandler]);
  });

  it('should find all chip ins', async () => {
    const { result } = renderHook(() => useFindChipInsByOwnerId('1'), {
      wrapper: ({ children }) => (
        <QueryClientProvider client={queryClient}>
          {children}
        </QueryClientProvider>
      ),
    });

    await waitFor(() => expect(result.current.isSuccess).toBe(true));
  });
});
