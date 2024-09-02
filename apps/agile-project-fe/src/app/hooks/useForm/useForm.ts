import React from 'react';

export enum Field {
  Email = 'email',
  Password = 'password',
  Number = 'number',
  Text = 'text',
}

export type Schema = {
  [key: string]: {
    type: Field;
    validation?: {
      predicate: (value: string) => boolean;
      message: string;
    };
  };
};

export type Validation = {
  isValid: boolean;
  message: string;
};

export interface UseForm<T> {
  form: Map<T, string>;
  validation: Map<string, Validation>;
  get: (key: T) => string;
  set: (key: T, value: string) => void;
  reset: () => void;
  isValid: boolean;
}

export const useForm = <T>(schema: Schema): UseForm<T> => {
  const [form, setForm] = React.useState(new Map<T, string>());
  const [validation, setValidation] = React.useState(
    new Map<string, Validation>()
  );
  const [isValid, setIsValid] = React.useState<boolean>(false);

  React.useEffect(() => {
    const initialForm = new Map<T, string>();
    const initialValidation = new Map<string, Validation>();

    const keys = Object.keys(schema);

    keys.forEach((key) => {
      initialForm.set(key as T, '');
      initialValidation.set(key, { isValid: false, message: '' });
    });

    setForm(initialForm);
    setValidation(initialValidation);
  }, []);

  React.useEffect(() => {
    const formKeys = Array.from(form.keys());
    const updatedValidation = new Map<string, Validation>(validation);

    formKeys.forEach((key) => {
      const field = schema[key as string];

      const value = form.get(key) || '';

      const isValid = field.validation?.predicate(value) ?? true;

      const message = isValid ? '' : field.validation?.message ?? '';

      updatedValidation.set(key as string, {
        isValid,
        message,
      });
    });

    const isFormValid = Array.from(updatedValidation.values()).every(
      (value) => value.isValid
    );

    setIsValid(isFormValid);
    setValidation(updatedValidation);
  }, [form]);

  const get = (key: T) => form.get(key) || '';

  const set = (key: T, value: string) => {
    const newForm = new Map(form);

    newForm.set(key, value);

    setForm(newForm);
  };

  const reset = () => {
    const resetForm = new Map<T, string>();
    const resetValidation = new Map<string, Validation>();

    form.forEach((_, key) => {
      resetForm.set(key, '');
      resetValidation.set(key as string, { isValid: true, message: '' });
    });

    setForm(resetForm);
    setValidation(resetValidation);
  };

  return { form, validation, get, set, isValid, reset };
};
