import { UseMutateFunction, useMutation } from '@tanstack/react-query';
import { AxiosResponse } from 'axios';
import { baseAxios } from '..';

export interface UserForm {
  username: string;
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
  const { mutate, isPending, isSuccess, isError } = useMutation({
    mutationKey: ['register'],
    mutationFn: (userForm: UserForm) =>
      baseAxios.post('/user/cadastro', userForm),
    onSuccess: (data) => {
      // TODO 27/04 Implement store
      console.log(data);
    },
  });

  return {
    mutate,
    isPending,
    isSuccess,
    isError,
  };
};
