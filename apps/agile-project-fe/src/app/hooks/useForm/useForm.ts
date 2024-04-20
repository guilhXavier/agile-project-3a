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
    isRequired?: boolean;
  };
};

export interface UseForm {
  form: Map<string, string>;
  get: (key: string) => string;
  set: (key: string, value: string) => void;
}

export const useForm = (schema: Schema): UseForm => {
  const [form, setForm] = React.useState(new Map<string, string>());

  React.useEffect(() => {
    const initialForm = new Map<string, string>();
    const keys = Object.keys(schema);

    keys.forEach((key) => {
      initialForm.set(key, '');
    });

    setForm(initialForm);
  }, []);

  const get = (key: string) => form.get(key) || '';

  const set = (key: string, value: string) => {
    const newForm = new Map(form);

    newForm.set(key, value);

    setForm(newForm);
  };

  return { form, get, set };
};
