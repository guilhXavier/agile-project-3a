import { UseMutateFunction, useMutation } from '@tanstack/react-query';
import { AxiosResponse } from 'axios';
import { baseAxios } from '..';
import { useStore } from '../../store';
import { useNavigate } from 'react-router-dom';

export interface UserForm {
  name: string;
  email: string;
  password: string;
  confirmPassword: string;
}

export interface UseRegisterReturn {
  isPending: boolean;
  isSuccess: boolean;
  isError: boolean;
  mutate: UseMutateFunction<AxiosResponse, unknown, UserForm, unknown>;
}

export const useRegister = (): UseRegisterReturn => {
  const { setUser, setToken } = useStore();

  const navigate = useNavigate();

  const { mutate, isPending, isSuccess, isError } = useMutation({
    mutationKey: ['register'],
    mutationFn: (userForm: UserForm) =>
      baseAxios.post('/user/signup', userForm),
    onSuccess: (
      data: AxiosResponse<{ email: string; id: string; name: string }>
    ) => {
      setToken(data.headers['rachadinha-login-token']);
      setUser(data.data);
      navigate('/listing');
    },
  });

  return {
    mutate,
    isPending,
    isSuccess,
    isError,
  };
};
