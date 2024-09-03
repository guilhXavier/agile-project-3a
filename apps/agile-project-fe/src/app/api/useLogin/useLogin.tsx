import React from 'react';
import { useQuery } from '@tanstack/react-query';
import { baseAxios } from '..';
import { User } from '../../types';
import { useStore } from '../../store';
import { useNavigate } from 'react-router-dom';

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
  const { setUser, setToken } = useStore();

  const navigate = useNavigate();

  const { data, isError, isSuccess, isLoading } = useQuery({
    queryKey: ['login'],
    queryFn: () => baseAxios.post('/user/login', loginForm),
    enabled: isFormValid,
  });

  React.useEffect(() => {
    if (isSuccess) {
      localStorage.setItem('token', data.headers['rachadinha-login-token']);
      setToken(data.headers['rachadinha-login-token']);
      setUser(data.data);
      navigate('/listing');
    }
  }, [isSuccess]);

  return { data: data?.data as User, isError, isSuccess, isLoading };
};
