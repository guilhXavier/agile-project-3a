import { useMutation } from '@tanstack/react-query';
import { baseAxios, queryClient } from '..';
import { useStore } from '../../store';

export interface ChipInForm {
  name: string;
  description: string;
  password: string;
  goal: number;
  ownerId: number;
}

export interface UseCreateChipIn {
  createChipIn: (chipInForm: ChipInForm) => void;
  isSuccess: boolean;
  isError: boolean;
}

export const useCreateChipIn = (): UseCreateChipIn => {
  const { token } = useStore();

  const { mutate, isSuccess, isError } = useMutation({
    mutationKey: ['chipIn'],
    mutationFn: (chipInForm: ChipInForm) =>
      baseAxios.post('/racha/create', chipInForm, {
        headers: {
          'rachadinha-login-token': token,
        },
      }),
    onSuccess: () => {
      queryClient.refetchQueries({ queryKey: ['findChipInsByOwnerId'] });
    },
  });

  return { createChipIn: mutate, isSuccess, isError };
};
