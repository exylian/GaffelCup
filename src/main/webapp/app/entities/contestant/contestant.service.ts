import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import {Contestant, Strength} from './contestant.model';
import { ResponseWrapper, createRequestOption } from '../../shared';
import { UUID } from 'angular2-uuid';

@Injectable()
export class ContestantService {

    private resourceUrl = SERVER_API_URL + 'api/contestants';
    private registerUrl = SERVER_API_URL + 'api/contestants/register';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(contestant: Contestant): Observable<Contestant> {
        const copy = this.convert(contestant);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }
    register(contestant: Contestant): Observable<Contestant> {
        const copy = this.convert(contestant);
        return this.http.post(this.registerUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(contestant: Contestant): Observable<Contestant> {
        const copy = this.convert(contestant);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Contestant> {
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
     * Convert a returned JSON object to Contestant.
     */
    private convertItemFromServer(json: any): Contestant {
        const entity: Contestant = Object.assign(new Contestant(), json);
        entity.confirmedAt = this.dateUtils
            .convertDateTimeFromServer(json.confirmedAt);
        entity.payedAt = this.dateUtils
            .convertDateTimeFromServer(json.payedAt);
        return entity;
    }

    /**
     * Convert a Contestant to a JSON which can be sent to the server.
     */
    private convert(contestant: Contestant): Contestant {
        const copy: Contestant = Object.assign({}, contestant);

        copy.confirmedAt = this.dateUtils.toDate(contestant.confirmedAt);

        copy.payedAt = this.dateUtils.toDate(contestant.payedAt);
        return copy;
    }
}
