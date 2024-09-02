import React from 'react';
import { useParams } from 'react-router-dom';
import { useGetChipInById } from '../../api/useGetChipInById/useGetChipInById';
import styled from 'styled-components';
import { SideMenu } from '../../components/side-menu/side-menu';
import { Card } from '../../components/card/card';

const StyledDetail = styled.div``;

export const Detail: React.FC = () => {
  const { chipInId } = useParams();

  const { data, isLoading, isSuccess } = useGetChipInById(chipInId || '');

  return (
    <StyledDetail>
      <SideMenu />
      {isLoading && <p>Loading...</p>}
      {isSuccess && (
        <Card key={data?.id || ''}>
          <h2>{data?.name}</h2>
        </Card>
      )}
    </StyledDetail>
  );
};
