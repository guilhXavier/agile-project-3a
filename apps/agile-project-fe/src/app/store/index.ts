import { create } from 'zustand';
import { User } from '../types';

interface Store {
  user: User | null;
  setUser: (user: User) => void;
  logout: () => void;
}

export const useStore = create<Store>((set) => ({
  user: null,
  setUser: (user: User) => set({ user }),
  logout: () => set({ user: null }),
}));
