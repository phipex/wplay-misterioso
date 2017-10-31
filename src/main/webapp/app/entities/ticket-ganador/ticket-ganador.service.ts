import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { TicketGanador } from './ticket-ganador.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class TicketGanadorService {

    private resourceUrl = SERVER_API_URL + 'api/ticket-ganadors';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(ticketGanador: TicketGanador): Observable<TicketGanador> {
        const copy = this.convert(ticketGanador);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(ticketGanador: TicketGanador): Observable<TicketGanador> {
        const copy = this.convert(ticketGanador);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<TicketGanador> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
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
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to TicketGanador.
     */
    private convertItemFromServer(json: any): TicketGanador {
        const entity: TicketGanador = Object.assign(new TicketGanador(), json);
        entity.fecha = this.dateUtils
            .convertDateTimeFromServer(json.fecha);
        return entity;
    }

    /**
     * Convert a TicketGanador to a JSON which can be sent to the server.
     */
    private convert(ticketGanador: TicketGanador): TicketGanador {
        const copy: TicketGanador = Object.assign({}, ticketGanador);

        copy.fecha = this.dateUtils.toDate(ticketGanador.fecha);
        return copy;
    }
}
