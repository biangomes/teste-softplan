import { render, screen } from '@testing-library/react';
import App from './App';

test('renders person registration form', () => {
  render(<App />);
  const titleElement = screen.getByText(/cadastro de pessoa/i);
  expect(titleElement).toBeInTheDocument();
});
