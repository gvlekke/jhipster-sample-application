import { IUsers } from 'app/shared/model//users.model';

export interface IClients {
    id?: number;
    name?: string;
    users?: IUsers[];
}

export class Clients implements IClients {
    constructor(public id?: number, public name?: string, public users?: IUsers[]) {}
}
