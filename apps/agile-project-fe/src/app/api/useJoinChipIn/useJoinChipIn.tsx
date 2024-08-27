import { useMutation, useQuery } from '@tanstack/react-query';
import { baseAxios, queryClient } from '..';
import { ChipIn } from '../../types';
import { useStore } from '../../store';
import { AxiosResponse } from 'axios';

export const useJoinChipIn = (
  chipInInvite: string
): { joinChipIn: (password: string) => void } => {
  const { token } = useStore();

  const { data } = useQuery({
    queryKey: ['chipInPreview'],
    queryFn: (): Promise<AxiosResponse<ChipIn>> =>
      baseAxios.get(`racha/invite/${chipInInvite}`, {
        headers: {
          'rachadinha-login-token': token,
        },
      }),
    enabled: !!chipInInvite,
  });

  const { mutate } = useMutation({
    mutationKey: ['joinChipIn'],
    mutationFn: (password: string) =>
      baseAxios.post(`/racha/join`, null, {
        headers: {
          'rachadinha-login-token': token,
        },
        params: {
          idRacha: data?.data.id,
          pass: password,
        },
      }),
    onSuccess: () => {
      queryClient.refetchQueries({ queryKey: ['findChipInsByOwnerId'] });
    },
  });

  return {
    joinChipIn: mutate,
  };
};
