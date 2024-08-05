import { useQuery } from '@tanstack/react-query';
import { baseAxios } from '..';
import { User } from '../../types';

export interface LoginForm {
  email: string;
  password: string;
}

export interface UseLoginReturn {
  data?: User;
  isError: boolean;
  isSuccess: boolean;
  isLoading: boolean;
}

export const useLogin = (
  loginForm: LoginForm,
  isFormValid: boolean
): UseLoginReturn => {
  const { data, isError, isSuccess, isLoading } = useQuery({
    queryKey: ['login'],
    queryFn: (): Promise<User> => baseAxios.post('/login', loginForm),
    enabled: isFormValid,
  });

  return { data, isError, isSuccess, isLoading };
};
