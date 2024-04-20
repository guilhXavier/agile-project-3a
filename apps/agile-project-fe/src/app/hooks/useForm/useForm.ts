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

export interface UseForm {
  form: Map<string, string>;
  validation: Map<string, Validation>;
  get: (key: string) => string;
  set: (key: string, value: string) => void;
}

export const useForm = (schema: Schema): UseForm => {
  const [form, setForm] = React.useState(new Map<string, string>());
  const [validation, setValidation] = React.useState(
    new Map<string, Validation>()
  );

  React.useEffect(() => {
    const initialForm = new Map<string, string>();
    const initialValidation = new Map<string, Validation>();

    const keys = Object.keys(schema);

    keys.forEach((key) => {
      initialForm.set(key, '');
      initialValidation.set(key, { isValid: true, message: '' });
    });

    setForm(initialForm);
    setValidation(initialValidation);
  }, []);

  React.useEffect(() => {
    const formKeys = Array.from(form.keys());
    const updatedValidation = new Map<string, Validation>(validation);

    formKeys.forEach((key) => {
      const field = schema[key];

      const value = form.get(key) || '';

      const isValid = field.validation?.predicate(value) ?? true;

      const message = isValid ? '' : field.validation?.message ?? '';

      updatedValidation.set(key, {
        isValid,
        message,
      });
    });

    setValidation(updatedValidation);
  }, [form]);

  const get = (key: string) => form.get(key) || '';

  const set = (key: string, value: string) => {
    const newForm = new Map(form);

    newForm.set(key, value);

    setForm(newForm);
  };

  return { form, validation, get, set };
};
