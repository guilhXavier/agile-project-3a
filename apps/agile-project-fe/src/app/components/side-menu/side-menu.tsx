import React from 'react';
import { createPortal } from 'react-dom';
import styled from 'styled-components';
import MenuIcon from '@material-ui/icons/Menu';
import CloseIcon from '@material-ui/icons/Close';
import AttachMoneyIcon from '@material-ui/icons/AttachMoney';
import MenuBookIcon from '@material-ui/icons/MenuBook';
import PersonIcon from '@material-ui/icons/Person';
import AddCircleIcon from '@material-ui/icons/AddCircle';
import { Dialog } from '../dialog/dialog';
import { Field, useForm } from '../../hooks/useForm/useForm';
import Input from '../input/input';
import { useCreateChipIn } from '../../api/useCreateChipIn/useCreateChipIn';
import { useStore } from '../../store';

const schema = {
  name: {
    type: Field.Text,
    validation: {
      predicate: (value: string) => value.length > 0,
      message: 'Nome é obrigatório',
    },
  },
  description: {
    type: Field.Text,
  },
  totalValue: {
    type: Field.Number,
    validation: {
      predicate: (value: string) => Number(value) > 0,
      message: 'Valor total é obrigatório',
    },
  },
  password: {
    type: Field.Password,
    validation: {
      predicate: (value: string) => value.length > 5,
      message: 'Senha é obrigatória e deve ter mais de 5 dígitos',
    },
  },
};

const StyledSideMenu = styled.div<{ isExpanded: boolean }>`
  height: 100%;
  background-color: #1b850a;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
  position: fixed;
  top: 0;
  left: 0;
  z-index: 98;
  transition: width 0.3s ease-in-out;
  width: ${(props) => (props.isExpanded ? '128px' : '64px')};
  box-shadow: 0 0 10px 0 rgba(0, 0, 0, 0.5);

  .MuiSvgIcon-root {
    color: white;
    cursor: pointer;
    margin-top: 20px;
    font-size: 32px;
  }

  .action-icons {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    margin-top: 20px;
  }

  .profile-icon {
    margin-bottom: 20px;
  }

  .icon {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    margin-bottom: 20px;
    color: white;
    font-size: 16px;
  }
`;

export const SideMenu: React.FC = () => {
  const ownerId = useStore((state) => state.user?.id);

  const [isExpanded, setIsExpanded] = React.useState<boolean>(false);

  const [isCreateDialogOpen, setIsCreateDialogOpen] =
    React.useState<boolean>(false);

  const { validation, get, set, isValid } = useForm<
    'name' | 'description' | 'totalValue' | 'password'
  >(schema);

  const { createChipIn } = useCreateChipIn();

  const renderCreateDialog = () => (
    <Dialog
      title="Criar racha"
      isVisible={isCreateDialogOpen}
      handleClose={() => setIsCreateDialogOpen(!isCreateDialogOpen)}
      onConfirm={() => {
        if (isValid) {
          createChipIn({
            name: get('name'),
            description: get('description'),
            goal: Number(get('totalValue')),
            password: get('password'),
            ownerId: Number(ownerId),
          });
        }
      }}
    >
      <Input
        id="name"
        inputType="text"
        label="Nome"
        placeholder="Nome do racha"
        value={get('name')}
        onInput={(e) => set('name', e.currentTarget.value)}
        isValid={validation.get('name')?.isValid}
        validationMessage={validation.get('name')?.message}
      />
      <Input
        id="description"
        inputType="text"
        label="Descrição"
        placeholder="Descrição do racha"
        value={get('description')}
        onInput={(e) => set('description', e.currentTarget.value)}
      />
      <Input
        id="totalValue"
        inputType="text"
        label="Valor total"
        placeholder="Valor total do racha"
        value={get('totalValue')}
        onInput={(e) => set('totalValue', e.currentTarget.value)}
        isValid={validation.get('totalValue')?.isValid}
        validationMessage={validation.get('totalValue')?.message}
      />
      <Input
        id="password"
        inputType="password"
        label="Senha"
        placeholder="Senha do racha"
        value={get('password')}
        onInput={(e) => set('password', e.currentTarget.value)}
        isValid={validation.get('password')?.isValid}
        validationMessage={validation.get('password')?.message}
      />
    </Dialog>
  );

  return (
    <StyledSideMenu isExpanded={isExpanded}>
      {createPortal(renderCreateDialog(), document.body)}
      {!isExpanded && (
        <MenuIcon
          fontSize="medium"
          onClick={() => setIsExpanded(!isExpanded)}
        />
      )}
      {isExpanded && (
        <CloseIcon
          fontSize="medium"
          onClick={() => setIsExpanded(!isExpanded)}
        />
      )}
      <div className="action-icons">
        <div className="icon">
          <AttachMoneyIcon fontSize="medium" />
          {isExpanded && <span>Rachas</span>}
        </div>
        <div className="icon">
          <MenuBookIcon fontSize="medium" />
          {isExpanded && <span>Histórico</span>}
        </div>
      </div>
      <div className="profile-icon">
        <div className="icon" onClick={() => setIsCreateDialogOpen(true)}>
          <AddCircleIcon fontSize="medium" />
          {isExpanded && <span>Criar</span>}
        </div>
        <div className="icon">
          <PersonIcon fontSize="medium" />
          {isExpanded && <span>Perfil</span>}
        </div>
      </div>
    </StyledSideMenu>
  );
};
