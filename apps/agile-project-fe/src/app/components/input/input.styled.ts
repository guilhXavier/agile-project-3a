import styled from 'styled-components';

export const StyledInput = styled.div`
  display: flex;
  flex-direction: column;
  max-width: 256px;
  font-family: 'Roboto', sans-serif;

  input {
    background-color: #f2f2f2;
    padding: 0.5em;
    margin: 0.5em 0 1em;
    border: 1px solid #ccc;
    border-radius: 0.4em;

    font-family: 'Roboto', sans-serif;
    font-size: 14px;

    &:focus {
      outline: none;
      border-color: black;
    }
  }

  label {
    font-size: 12px;
  }

  span {
    color: red;
    padding-top: 0.5em;
  }
`;
