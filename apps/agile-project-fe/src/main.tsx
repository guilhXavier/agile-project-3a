import { StrictMode } from 'react';
import * as ReactDOM from 'react-dom/client';
import {
  Route,
  RouterProvider,
  createBrowserRouter,
  createRoutesFromElements,
} from 'react-router-dom';
import { QueryClientProvider } from '@tanstack/react-query';
import { Login } from './app/routes/login/login';
import { SignUp } from './app/routes/sign-up/sign-up';
import { queryClient } from './app/api';
import { Listing } from './app/routes/listing/listing';
import { Detail } from './app/routes/detail/detail';

const router = createBrowserRouter(
  createRoutesFromElements(
    <>
      <Route path="/" element={<Login />} />
      <Route path="register" element={<SignUp />} />
      <Route path="listing" element={<Listing />} />
      <Route path="detail/:chipInId" element={<Detail />} />
    </>
  )
);

const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);

root.render(
  <StrictMode>
    <QueryClientProvider client={queryClient}>
      <RouterProvider router={router} />
    </QueryClientProvider>
  </StrictMode>
);
