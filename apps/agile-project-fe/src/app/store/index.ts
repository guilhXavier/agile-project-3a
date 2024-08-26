import { create } from 'zustand';
import { User } from '../types';

interface Store {
  user: User | null;
  setUser: (user: User) => void;
  token: string;
  setToken: (token: string) => void;
  logout: () => void;
}

export const useStore = create<Store>((set) => ({
  user: JSON.parse(localStorage.getItem('user') || 'null'),
  setUser: (user: User) => {
    set({ user });
    localStorage.setItem('user', JSON.stringify(user));
  },
  token: localStorage.getItem('token') || '',
  setToken: (token: string) => {
    set({ token });
    localStorage.setItem('token', token);
  },
  logout: () => set({ user: null }),
}));
