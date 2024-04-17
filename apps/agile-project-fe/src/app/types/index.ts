export interface ChipIn {
  id: string;
  amount: number;
  user: User;
  event: Event;
}

export interface User {
  id: string;
  name: string;
  email: string;
  friends: Array<User>;
  chipIns: Array<ChipIn>;
}
