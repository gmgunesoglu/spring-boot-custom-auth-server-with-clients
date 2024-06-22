export interface UserDto {
  username: string;
  realmName: string;
  clientName: string;
  blocked: boolean;
}

export interface UserDetailDto {
  username: string;
  realmName: string;
  clientName: string;
  blocked: boolean;
  roles: string[];
}

export interface UserRegisterDto {
  username: string;
  password: string;
  roles: string[];
  clientName: string;
}
