import { useQuery } from '@tanstack/react-query';
import { AxiosResponse } from 'axios';
import { baseAxios } from '..';
import { ChipIn } from '../../types';

export interface UseFindChipInsByOwnerIdReturn {
  data?: AxiosResponse<Array<ChipIn>>;
  isLoading: boolean;
  isSuccess: boolean;
}

export const useFindChipInsByOwnerId = (
  ownerId: string
): UseFindChipInsByOwnerIdReturn => {
  const { data, isLoading, isSuccess } = useQuery({
    queryKey: ['findChipInsByOwnerId', ownerId],
    queryFn: (): Promise<AxiosResponse<Array<ChipIn>>> =>
      baseAxios.get(`/racha/list/owner/${ownerId}`),
    enabled: !!ownerId,
  });

  return { data, isLoading, isSuccess };
};
