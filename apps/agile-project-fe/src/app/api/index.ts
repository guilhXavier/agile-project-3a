import { QueryClient } from '@tanstack/react-query';
import axios from 'axios';

export const queryClient = new QueryClient();

export const baseAxios = axios.create({
  baseURL: 'http://locahost:8080',
  headers: { Accept: 'application/json' },
});
