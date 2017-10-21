import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Ticket } from './ticket.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class TicketService {

    private resourceUrl = SERVER_API_URL + 'api/tickets';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(ticket: Ticket): Observable<Ticket> {
        const copy = this.convert(ticket);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(ticket: Ticket): Observable<Ticket> {
        const copy = this.convert(ticket);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<Ticket> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.fecha = this.dateUtils
            .convertDateTimeFromServer(entity.fecha);
    }

    private convert(ticket: Ticket): Ticket {
        const copy: Ticket = Object.assign({}, ticket);

        copy.fecha = this.dateUtils.toDate(ticket.fecha);
        return copy;
    }
}
