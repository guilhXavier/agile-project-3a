import { useQuery } from '@tanstack/react-query';
import { baseAxios } from '..';
import { ChipIn } from '../../types';
import { useStore } from '../../store';
import { AxiosResponse } from 'axios';

interface UseGetChipInReturn {
  data?: ChipIn;
  isLoading: boolean;
  isSuccess: boolean;
}

export const useGetChipInById = (id: string): UseGetChipInReturn => {
  const { token } = useStore();

  const { data, isLoading, isSuccess } = useQuery({
    queryKey: ['chipIn', id],
    queryFn: (): Promise<AxiosResponse<ChipIn>> =>
      baseAxios.get(`/racha/${id}`, {
        headers: { 'rachadinha-login-token': token },
      }),
    enabled: !!id,
  });

  return { data: data?.data as ChipIn, isLoading, isSuccess };
};
