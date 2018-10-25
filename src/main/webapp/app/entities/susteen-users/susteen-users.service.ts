import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISusteenUsers } from 'app/shared/model/susteen-users.model';

type EntityResponseType = HttpResponse<ISusteenUsers>;
type EntityArrayResponseType = HttpResponse<ISusteenUsers[]>;

@Injectable({ providedIn: 'root' })
export class SusteenUsersService {
    public resourceUrl = SERVER_API_URL + 'api/susteen-users';

    constructor(private http: HttpClient) {}

    create(susteenUsers: ISusteenUsers): Observable<EntityResponseType> {
        return this.http.post<ISusteenUsers>(this.resourceUrl, susteenUsers, { observe: 'response' });
    }

    update(susteenUsers: ISusteenUsers): Observable<EntityResponseType> {
        return this.http.put<ISusteenUsers>(this.resourceUrl, susteenUsers, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ISusteenUsers>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISusteenUsers[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
