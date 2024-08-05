export interface ChipIn {
  id: string;
  name: string;
  description: string;
  goal: number;
  balance: number;
  status: string;
  createdAt: string;
  inviteLink: string;
  owner: {
    id: number;
    name: string;
    email: string;
  };
}

export interface User {
  id: string;
  name: string;
  email: string;
}
