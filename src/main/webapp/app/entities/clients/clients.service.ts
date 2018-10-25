import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IClients } from 'app/shared/model/clients.model';

type EntityResponseType = HttpResponse<IClients>;
type EntityArrayResponseType = HttpResponse<IClients[]>;

@Injectable({ providedIn: 'root' })
export class ClientsService {
    public resourceUrl = SERVER_API_URL + 'api/clients';

    constructor(private http: HttpClient) {}

    create(clients: IClients): Observable<EntityResponseType> {
        return this.http.post<IClients>(this.resourceUrl, clients, { observe: 'response' });
    }

    update(clients: IClients): Observable<EntityResponseType> {
        return this.http.put<IClients>(this.resourceUrl, clients, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IClients>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IClients[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
