import React from 'react';
import { useFindChipInsByOwnerId } from '../../api/useFindChipInsByOwnerId/useFindChipInsByOwnerId';
import { useStore } from '../../store';
import { ChipIn } from '../../types';
import { Card } from '../../components/card/card';
import { SideMenu } from '../../components/side-menu/side-menu';
import { StyledListing } from './listing.styled';

export const Listing: React.FC = () => {
  const userId = useStore((state) => state.user?.id);

  const { data, isLoading, isSuccess } = useFindChipInsByOwnerId(userId || '1');

  return (
    <StyledListing>
      <SideMenu />
      <h1>Listing</h1>
      {isLoading && <p>Loading...</p>}
      {isSuccess &&
        data?.data?.map((chipIn: ChipIn) => (
          <Card key={chipIn.id}>
            <h2>{chipIn.name}</h2>
            <p>{chipIn.description}</p>
            <p>{chipIn.goal}</p>
            <p>{chipIn.balance}</p>
            <p>{chipIn.inviteLink}</p>
          </Card>
        ))}
    </StyledListing>
  );
};
