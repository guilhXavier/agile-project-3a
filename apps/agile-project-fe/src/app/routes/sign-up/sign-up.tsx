import React from 'react';
import Input from '../../components/input/input';
import { Button } from '../../components/button/button';

export const SignUp: React.FC = () => {
  return (
    <div>
      <h1>Sign Up</h1>
      <p>Create your user here!</p>
      <label htmlFor="">Name:</label>
      <Input variant={'text'} placeholder="Name" />

      <label htmlFor="">Email:</label>
      <Input variant={'email'} placeholder="Email" />

      <label htmlFor="">Password:</label>
      <Input variant={'password'} placeholder="Password" />

      <label htmlFor="">Confirm Password:</label>
      <Input variant={'password'} placeholder="Confirm Password" />

      <Button
        variant={'confirm'}
        text="Sign Up"
        onClick={() => {
          console.log('Button clicked');
        }}
      />
      <Button
        variant={'cancel'}
        text="Cancel"
        onClick={() => {
          console.log('Button clicked');
        }}
      />
    </div>
  );
};
