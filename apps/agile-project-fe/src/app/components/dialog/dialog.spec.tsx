import { screen, render } from '@testing-library/react';

import { Dialog } from './dialog';

describe('Dialog component', () => {
  it('should render the dialog', () => {
    render(
      <Dialog
        title="Dialog title"
        isVisible={true}
        handleClose={() => {
          // do nothing
        }}
      >
        Dialog content
      </Dialog>
    );

    expect(screen.getByText('Dialog title')).toBeTruthy();
    expect(screen.getByText('Dialog content')).toBeTruthy();
  });
});
