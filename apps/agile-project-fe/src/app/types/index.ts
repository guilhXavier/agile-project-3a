export interface ChipIn {}

export interface User {
  id: string;
  name: string;
  email: string;
  friends: Array<User>;
  chipIns: Array<ChipIn>;
}
