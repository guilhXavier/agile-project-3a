export interface ChipIn {
  id: string;
  name: string;
  description: string;
  goal: number;
  balance: number;
  portionPerMember: number;
  status: Status;
  createdAt: string;
  inviteLink: string;
  members: Array<Member>;
}

export interface Member {
  userId: string;
  userName: string;
  email: string;
  balance: number;
  isPaid: boolean;
  isConfirmedByOwner: boolean;
  isOwner: boolean;
}

export type Status = 'OPEN' | 'CLOSED' | 'FINISHED';

export interface User {
  id: string;
  name: string;
  email: string;
}
