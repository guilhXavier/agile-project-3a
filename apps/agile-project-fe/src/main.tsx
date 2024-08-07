import { StrictMode } from 'react';
import * as ReactDOM from 'react-dom/client';
import { Routes, Route, BrowserRouter } from 'react-router-dom';
import { QueryClientProvider } from '@tanstack/react-query';
import { Login } from './app/routes/login/login';
import { SignUp } from './app/routes/sign-up/sign-up';
import { queryClient } from './app/api';
import { Listing } from './app/routes/listing/listing';
import { Detail } from './app/routes/detail/detail';

const App = () => (
  <QueryClientProvider client={queryClient}>
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/login" element={<Login />} />
        <Route path="sign-up" element={<SignUp />} />
        <Route path="listing" element={<Listing />} />
        <Route path="detail/:chipInId" element={<Detail />} />
      </Routes>
    </BrowserRouter>
  </QueryClientProvider>
);

const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);

root.render(
  <StrictMode>
    <App />
  </StrictMode>
);
