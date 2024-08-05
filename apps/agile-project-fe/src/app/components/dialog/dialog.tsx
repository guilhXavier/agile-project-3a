import React from 'react';
import styled from 'styled-components';
import { Button } from '../button/button';

export interface DialogProps {
  isVisible: boolean;
  title: string;
  handleClose: () => void;
  onConfirm?: () => void;
  onCancel?: () => void;
  children: React.ReactNode;
}

const StyledDialogCard = styled.div<{ isVisible: boolean }>`
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 300px;
  height: auto;
  font-size: 12px;
  border-radius: 10px;
  padding: 20px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  z-index: 100;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-align: center;
  opacity: 1;
  transition: opacity 0.3s ease-in-out;
  visibility: visible;
  transition: visibility 0s, opacity 0.3s;
  background-color: white;
  border: 1px solid #ccc;
  border-radius: 4px
    ${({ isVisible }) =>
      !isVisible &&
      `
        opacity: 0;
        visibility: hidden;
    `};
`;

const StyledDialogContent = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  height: 100%;
  width: 100%;
`;

const StyledOverlay = styled.div<{ isVisible: boolean }>`
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  z-index: 99;
  opacity: 1;
  transition: opacity 0.3s ease-in-out;
  visibility: visible;
  transition: visibility 0s, opacity 0.3s;

  ${({ isVisible }) =>
    !isVisible &&
    `
        opacity: 0;
        visibility: hidden;
    `}
`;

const StyledDialogTitle = styled.div`
  font-size: 16px;
  font-weight: bold;
  text-align: center;
`;

const StyledDialogContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100%;
`;

const StyledDialogActions = styled.div`
  display: flex;
  flex-direction: row-reverse;
  justify-content: center;
  align-items: center;
  padding: 10px;

  > button {
    margin: 0 5px;
  }
`;

export const Dialog: React.FC<DialogProps> = ({
  isVisible,
  title,
  handleClose,
  children,
  onConfirm,
  onCancel,
}) => (
  <>
    <StyledOverlay isVisible={isVisible} onClick={handleClose} />
    <StyledDialogCard isVisible={isVisible}>
      <StyledDialogContent className="dialog--content">
        <StyledDialogTitle>{title}</StyledDialogTitle>
        <StyledDialogContainer>{children}</StyledDialogContainer>
      </StyledDialogContent>
      <StyledDialogActions>
        <Button
          variant="confirm"
          text="Confirm"
          onClick={() => {
            handleClose();
            onConfirm?.();
          }}
        />
        <Button
          variant="cancel"
          text="Cancel"
          onClick={() => {
            handleClose();
            onCancel?.();
          }}
        />
      </StyledDialogActions>
    </StyledDialogCard>
  </>
);
