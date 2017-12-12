import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Round } from './round.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class RoundService {

    private resourceUrl = SERVER_API_URL + 'api/rounds';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(round: Round): Observable<Round> {
        const copy = this.convert(round);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(round: Round): Observable<Round> {
        const copy = this.convert(round);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Round> {
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
     * Convert a returned JSON object to Round.
     */
    private convertItemFromServer(json: any): Round {
        const entity: Round = Object.assign(new Round(), json);
        entity.finishedAt = this.dateUtils
            .convertDateTimeFromServer(json.finishedAt);
        return entity;
    }

    /**
     * Convert a Round to a JSON which can be sent to the server.
     */
    private convert(round: Round): Round {
        const copy: Round = Object.assign({}, round);

        copy.finishedAt = this.dateUtils.toDate(round.finishedAt);
        return copy;
    }
}
