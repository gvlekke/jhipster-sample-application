import { ISusteenUsers } from 'app/shared/model//susteen-users.model';

export interface IClients {
    id?: number;
    name?: string;
    users?: ISusteenUsers[];
}

export class Clients implements IClients {
    constructor(public id?: number, public name?: string, public users?: ISusteenUsers[]) {}
}
