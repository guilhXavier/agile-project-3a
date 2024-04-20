import { act, renderHook } from '@testing-library/react';
import { Field, useForm } from './useForm';

describe('useForm', () => {
  const schema = {
    email: { type: Field.Email, isRequired: true },
    password: { type: Field.Password, isRequired: true },
  };

  it('should return a form object with a get and set method based on a schema', () => {
    const { result } = renderHook(() => useForm(schema));

    const formKeys = Array.from(result.current.form.keys());

    expect(formKeys).toContain('email');
    expect(formKeys).toContain('password');
  });

  it("should modify the form object's value based on a key", () => {
    const expectedEmail = 'fake@email.com';

    const { result } = renderHook(() => useForm(schema));

    act(() => {
      result.current.set('email', 'fake@email.com');
    });

    const email = result.current.get('email');

    expect(email).toBe(expectedEmail);
  });

  it('should retrieve an empty string from the form object if it does not exist', () => {
    const { result } = renderHook(() => useForm(schema));

    const email = result.current.get('email');

    expect(email).toBe('');
  });
});
