export interface UserDto {
  username: string;
  realmName: string;
  clientName: string;
  roles: string[];
}

export interface UserRegisterDto {
  username: string;
  password: string;
  roles: string[];
  clientName: string;
}
