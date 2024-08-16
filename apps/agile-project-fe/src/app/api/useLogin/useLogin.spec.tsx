import { http, HttpResponse } from 'msw';
import { renderHook, waitFor } from '@testing-library/react';
import { QueryClientProvider } from '@tanstack/react-query';
import { useLogin } from './useLogin';
import { server } from '../../mocks/node';
import { queryClient } from '../index';

describe('useLogin test suite', () => {
  const loginHandler = http.post('http://localhost:8080/login', () => {
    return new HttpResponse(null, { status: 200 });
  });

  beforeAll(() => {
    server.listen();
    server.use(...[loginHandler]);
  });

  it('should login successfully', async () => {
    const { result } = renderHook(
      () => useLogin({ email: 'email@x.com', password: 'password' }, true),
      {
        wrapper: ({ children }) => (
          <QueryClientProvider client={queryClient}>
            {children}
          </QueryClientProvider>
        ),
      }
    );

    await waitFor(() => expect(result.current.isSuccess).toBe(true));
  });
});
