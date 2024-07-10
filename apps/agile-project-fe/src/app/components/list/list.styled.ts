import styled from 'styled-components';

export const StyledList = styled.ul`
  list-style: none;
  padding: 0;
  margin: 0;
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: 1rem;
  li {
    border: 1px solid #ccc;
    border-radius: 0.5rem;
    padding: 1rem;
    min-width: 200px;
    max-width: 300px;
    text-align: center;
  }
`;
