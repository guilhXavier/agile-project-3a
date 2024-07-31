import { useQuery } from '@tanstack/react-query';
import { baseAxios } from '..';
import { ChipIn } from '../../types';

export const useFindChipInsByOwnerId = (
  ownerId: string
): {
  data: Array<ChipIn> | undefined;
  isLoading: boolean;
  isSuccess: boolean;
} => {
  const { data, isLoading, isSuccess } = useQuery({
    queryKey: ['findChipInsByOwnerId', ownerId],
    queryFn: (): Promise<Array<ChipIn>> =>
      baseAxios.get(`/racha/findByOwner/${ownerId}`),
  });

  return { data, isLoading, isSuccess };
};
