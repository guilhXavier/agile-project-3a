import React from 'react';
import { useParams } from 'react-router-dom';
import { useGetChipInById } from '../../api/useGetChipInById/useGetChipInById';
import styled from 'styled-components';
import { SideMenu } from '../../components/side-menu/side-menu';
import { Card } from '../../components/card/card';
import { Member } from '../../types';

const StyledDetail = styled.div`
  margin: 0 2.5rem 0 5rem;
`;

export const Detail: React.FC = () => {
  const { chipInId } = useParams();

  const { data, isLoading, isSuccess } = useGetChipInById(chipInId || '');

  const renderParticipants = (member: Member) => (
    <Card key={member.userId}>
      <div>{member.userName}</div>
      {member.isOwner && 'Dono'}
      {member.userSaidPaid ? 'Pago' : 'Não pago'}
    </Card>
  );

  const renderDetailSection = () => (
    <>
      <Card key={data?.id || ''}>
        <h2>{data?.name}</h2>
        <h3>{data?.description}</h3>
        <p>Objetivo: {data?.goal}</p>
        <p>Saldo: {data?.balance}</p>
      </Card>
      <Card key={`participants-${data?.id}`}>
        <h3>Participantes</h3>
        {!data?.members.length && <p>Ainda não há participantes</p>}
        {data?.members.map(renderParticipants)}
      </Card>
    </>
  );

  return (
    <StyledDetail>
      <SideMenu />
      {isLoading && <p>Loading...</p>}
      {isSuccess && renderDetailSection()}
    </StyledDetail>
  );
};
