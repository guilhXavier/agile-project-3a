import { http, HttpResponse } from 'msw';
import { QueryClientProvider } from '@tanstack/react-query';
import { queryClient } from '../index';
import { act, renderHook, waitFor } from '@testing-library/react';
import { server } from '../../mocks/node';
import { useRegister } from './useRegister';

describe('useRegister test suite', () => {
  const signUpHandler = http.post('http://localhost:8080/user/cadastro', () => {
    return new HttpResponse(null, { status: 200 });
  });

  beforeAll(() => {
    server.listen();
    server.use(...[signUpHandler]);
  });

  it('should register the user', async () => {
    const { result } = renderHook(() => useRegister(), {
      wrapper: ({ children }) => (
        <QueryClientProvider client={queryClient}>
          {children}
        </QueryClientProvider>
      ),
    });

    act(() => {
      result.current.mutate({
        name: 'John Doe',
        email: 'jon@email.com',
        password: '12345',
        confirmPassword: '12345',
      });
    });

    await waitFor(() => expect(result.current.isSuccess).toBe(true));
  });
});
