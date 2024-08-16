import styled from 'styled-components';

export const SectionStyled = styled.section`
  position: fixed;
  top: 0;
  left: 0;

  display: flex;
  flex-direction: row;
  height: 100vh;
  width: 100%;

  .left-section {
    flex: 1;
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100%;
  img {
      width: 100%;
      height: 100%;
      object-fit: cover;
      }
  }

  .right-section {
    display: flex;
    flex-direction: column;
    align-items: center;
    flex-grow: .009;
    height: 100%;

    font-family: 'Roboto', sans-serif;

    header {
      align-items: center;
      display: flex;
      flex-direction: column;
      h1 {
        font-size: 32px;
        margin-bottom: 1rem;
        margin-top: 150px;
        text-align: center;
      }

      p {
        text-align: center;
        font-size: 22px;
        width: 90%;
      }
    }

    footer {
      font-size: 14px;
      position: absolute;
      bottom: 0;
      margin-bottom: 1rem;

      a {
        color: #000;
        text-decoration: none;

        &:hover {
          text-decoration: underline;

        }
      }
    }

    form {
      display: flex;
      flex-direction: column;
      align-items: center;
      margin-top: 2rem;
      width: 80%;

      div {
        width: 100%;
        margin: 0.5rem 0;
      }

      button {
        width: 100%;
      }

      .divider {
        width: 100%;
        height: 1px;
        background-color: #e0e0e0;
        margin: 1rem 0;
    }
  }
`;
