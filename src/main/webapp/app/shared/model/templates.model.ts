import { ISusteenUsers } from 'app/shared/model//susteen-users.model';

export interface ITemplates {
    id?: number;
    name?: string;
    fileContentType?: string;
    file?: any;
    susteenUsers?: ISusteenUsers;
}

export class Templates implements ITemplates {
    constructor(
        public id?: number,
        public name?: string,
        public fileContentType?: string,
        public file?: any,
        public susteenUsers?: ISusteenUsers
    ) {}
}
