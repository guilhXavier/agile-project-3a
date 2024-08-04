import { useMutation, useQuery } from '@tanstack/react-query';
import { baseAxios } from '..';

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
  const { mutate, isSuccess, isError } = useMutation({
    mutationKey: ['chipIn'],
    mutationFn: (chipInForm: ChipInForm) =>
      baseAxios.post('/racha/create', chipInForm),
  });

  return { createChipIn: mutate, isSuccess, isError };
};
