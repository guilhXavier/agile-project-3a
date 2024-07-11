import { render, screen, fireEvent } from '@testing-library/react';
import { composeStories } from '@storybook/react';

import * as stories from './input.stories';

const { Primary, Invalid } = composeStories(stories);

describe('Input component', () => {
  it('should render successfully', () => {
    const inputFn = vitest.fn();

    render(<Primary onInput={inputFn} />);

    expect(screen.getByLabelText('Input:')).toBeTruthy();
  });

  it('should call onInput when input is changed', () => {
    const inputFn = vitest.fn();

    render(<Primary onInput={inputFn} />);

    const input = screen.getByLabelText('Input:') as HTMLInputElement;

    input.value = 'Hello, world!';
    fireEvent.input(input);

    expect(inputFn).toHaveBeenCalled();
  });

  it('should render validation message when isInvalid is true', () => {
    render(<Invalid />);

    expect(screen.getByText('Invalid input')).toBeTruthy();
  });
});
