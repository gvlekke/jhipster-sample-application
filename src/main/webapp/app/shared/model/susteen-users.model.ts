import { IClients } from 'app/shared/model//clients.model';
import { ITemplates } from 'app/shared/model//templates.model';

export interface ISusteenUsers {
    id?: number;
    username?: string;
    email?: string;
    password?: string;
    clients?: IClients;
    templates?: ITemplates[];
}

export class SusteenUsers implements ISusteenUsers {
    constructor(
        public id?: number,
        public username?: string,
        public email?: string,
        public password?: string,
        public clients?: IClients,
        public templates?: ITemplates[]
    ) {}
}
