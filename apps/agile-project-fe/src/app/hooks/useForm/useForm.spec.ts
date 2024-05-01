import { act, renderHook } from '@testing-library/react';
import { Field, useForm } from './useForm';

describe('useForm', () => {
  const schema = {
    email: { type: Field.Email },
    password: {
      type: Field.Password,
      validation: {
        predicate: (value: string) => value.length > 5,
        message: 'Invalid password',
      },
    },
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

  it('should return a validation object based on a schema', () => {
    const { result } = renderHook(() => useForm(schema));

    const validationKeys = Array.from(result.current.validation.keys());

    expect(validationKeys).toContain('password');
  });

  it('should return a validation object with a message if the value is invalid', () => {
    const { result } = renderHook(() => useForm(schema));

    act(() => {
      result.current.set('password', '123');
    });

    const passwordValidation = result.current.validation.get('password');

    expect(passwordValidation?.isValid).toBe(false);
    expect(passwordValidation?.message).toBe('Invalid password');
  });

  it('should return a validation object with an empty message if the value is valid', () => {
    const { result } = renderHook(() => useForm(schema));

    act(() => {
      result.current.set('password', '123456');
    });

    const passwordValidation = result.current.validation.get('password');

    expect(passwordValidation?.isValid).toBe(true);
    expect(passwordValidation?.message).toBe('');
  });

  it('should return a form and validation object for a numeric field based on a schema', () => {
    const schema = {
      price: {
        type: Field.Number,
        validation: {
          predicate: (value: string) => Number(value) > 5,
          message: 'Price must be greater than 5',
        },
      },
    };

    const { result } = renderHook(() => useForm(schema));

    const formKeys = Array.from(result.current.form.keys());

    expect(formKeys).toContain('price');

    const validationKeys = Array.from(result.current.validation.keys());

    expect(validationKeys).toContain('price');

    act(() => {
      result.current.set('price', '5.50');
    });

    const priceValidation = result.current.validation.get('price');

    expect(priceValidation?.isValid).toBe(true);
  });
});
