import { useQuery } from '@tanstack/react-query';
import { baseAxios } from '..';
import { AxiosResponse } from 'axios';

export interface LoginForm {
  email: string;
  password: string;
}

export interface UseLoginReturn {
  data: AxiosResponse<string, string>;
  isError: boolean;
  isSuccess: boolean;
  isLoading: boolean;
}

export const useLogin = (loginForm: LoginForm, isFormValid: boolean) => {
  const { data, isError, isSuccess, isLoading } = useQuery({
    queryKey: ['login'],
    queryFn: () => baseAxios.post('/login', loginForm),
    enabled: isFormValid,
  });

  return { data, isError, isSuccess, isLoading };
};
