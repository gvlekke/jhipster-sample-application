import { IUsers } from 'app/shared/model//users.model';

export interface ITemplates {
    id?: number;
    name?: string;
    fileContentType?: string;
    file?: any;
    users?: IUsers;
}

export class Templates implements ITemplates {
    constructor(public id?: number, public name?: string, public fileContentType?: string, public file?: any, public users?: IUsers) {}
}
