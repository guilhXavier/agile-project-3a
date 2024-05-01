import { render, screen, fireEvent } from '@testing-library/react';
import { composeStory } from '@storybook/react';

import Meta, { Primary } from './input.stories';

const PrimaryStory = composeStory(Primary, Meta);

describe('Input', () => {
  it('should render successfully', () => {
    const inputFn = vitest.fn();

    render(<PrimaryStory onInput={inputFn} />);

    expect(screen.getByLabelText('Input:')).toBeTruthy();
  });

  it('should call onInput when input is changed', () => {
    const inputFn = vitest.fn();

    render(<PrimaryStory onInput={inputFn} />);

    const input = screen.getByLabelText('Input:') as HTMLInputElement;

    input.value = 'Hello, world!';
    fireEvent.input(input);

    expect(inputFn).toHaveBeenCalled();
  });
});
