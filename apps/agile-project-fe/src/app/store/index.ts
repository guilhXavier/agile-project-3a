import { create } from 'zustand';
import { User } from '../types';

export const useStore = create((set) => ({
  user: null,
  setUser: (user: User) => set({ user }),
  logout: () => set({ user: null }),
}));
