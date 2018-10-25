import { IClients } from 'app/shared/model//clients.model';
import { ITemplates } from 'app/shared/model//templates.model';

export interface IUsers {
    id?: number;
    username?: string;
    email?: string;
    password?: string;
    clients?: IClients;
    templates?: ITemplates[];
}

export class Users implements IUsers {
    constructor(
        public id?: number,
        public username?: string,
        public email?: string,
        public password?: string,
        public clients?: IClients,
        public templates?: ITemplates[]
    ) {}
}
