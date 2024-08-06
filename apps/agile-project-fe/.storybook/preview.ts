import { initialize, mswLoader } from 'msw-storybook-addon';

initialize({
  serviceWorker: { url: '/apiMockServiceWorker.js' },
});

const preview = {
  parameters: {},
  loaders: [mswLoader],
};

export default preview;
